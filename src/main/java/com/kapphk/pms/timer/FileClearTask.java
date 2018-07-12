package com.kapphk.pms.timer;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kapphk.pms.bean.BeanMenuBook;
import com.kapphk.pms.service.web.BeanMenuBookService;

/**
 * 定时清理脏数据文件（此功能本地项目运行正常，部署至云服务器后运行将会造成所有菜谱文件被清除，目前不知道原因，所以此功能未开放。。。）
 * 
 * 执行时间点：0 0 2 * * ? 
 * 二小时执行一次
 * 
 * @author 胡子 since 2018年5月17日
 */
@Component
public class FileClearTask implements Serializable {

	private static final long serialVersionUID = 1L;

	// 系统Logger
	Logger logger = LoggerFactory.getLogger(FileClearTask.class);

	@Value("${filePath}")
	private String filePath;

	@Value("${doTask}")
	private String doTask;

	@Autowired
	private BeanMenuBookService service;

	@PostConstruct // 此注解在系统启动时 立即生效并执行该方法
	public void doTask() {
		if (Boolean.parseBoolean(doTask)) {
			getFileClear();
		}
	}

	/**
	 * 脏数据清理
	 * 
	 * @author 			huzi
	 * @date 			2018年5月16日
	 * @param filePath	文件路径
	 *          
	 * @return
	 */
	@Scheduled(cron = "${fileClear}")
	public void getFileClear() {//定时执行的方法不能带参数
		// Result rs = new Result();

		// case 1 : 数据库有XX日文件 路径，那么服务器上必然有文件对应 : 检查文件是否存在，不存在就抛出异常（算是监控功能）。
		// case 2 : 数据库没有XX日文件 路径 服务器上有XX日的文件 所以必然是脏数据 ： 直接把该文件夹 及
		// 子文件全部删除，不做任何比对。
		// case 3 : 数据库没有XX日文件 路径 服务器上也没有XX日的文件 继续下一次循环 ： continue；
		if (Boolean.parseBoolean(doTask)) {
			try {

				Calendar instance = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

				int dayCount = 100; // 今天以前向前减100天
				File dirFile = null; // 用于存放文件
				String currentPath = ""; // 用于存放目录路径
				String datePath = ""; // 用于存放以日期命名的目录名

				for (int i = 0; i < dayCount; i++) { // 遍历100天

					instance.add(Calendar.DATE, -1);
					datePath = sdf.format(instance.getTime());
					currentPath = filePath + datePath;

					// 根据文件路径中的目录名查询目录下的所有文件(根据当前路径 或者说 这个日期 去做查询)
					// List<BeanMenuBook> filesNameStr =
					// service.findByFilePath("'%/" + datePath + "/%'");
					List<BeanMenuBook> filesNameStr = service.findByFilePath(datePath);

					// 判断目录是否为空或者是否存在
					if (null == filesNameStr || filesNameStr.isEmpty()) {
						continue;
					}

					dirFile = new File(currentPath);
					// 当前文件存在 并且是目录
					if (dirFile.exists() && dirFile.isDirectory()) {
						File[] listFiles = dirFile.listFiles();

						// 遍历目录下的所有文件
						for (int j = 0; j < listFiles.length; j++) {
							File file = listFiles[j];
							if (filesNameStr.indexOf(file.getName()) == -1) {
								file.delete();
								System.out.println("脏数据清除成功~");
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				// rs.setStatus(Contents.PARAMS_ERROR);
			}
		}

		// return rs;
	}

}
