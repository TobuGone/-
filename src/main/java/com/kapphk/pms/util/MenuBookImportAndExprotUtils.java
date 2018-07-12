package com.kapphk.pms.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.kapphk.pms.bean.BeanMenuBook;
import com.kapphk.pms.dao.mapper.BeanMenuBookMapper;

/**
 * 菜谱批量导入导出
 * 
 * @author 胡子 since 2018年4月16日
 */
public class MenuBookImportAndExprotUtils {

	/**
	 * 批量导出 2018-04-04 huzi
	 * 
	 * @param list
	 * @return
	 * @throws IOException
	 */
	public static void export(List<BeanMenuBook> list, BeanMenuBookMapper menuBookMapper) throws IOException {

		Result rs = new Result();

		for (int i = 0; i < list.size(); i++) {

			BeanMenuBook menuBook = list.get(i);

			if (ValidateUtils.isBlank(menuBook.getId()) || ValidateUtils.isBlank(menuBook.getUid())) {
				rs.setStatus(Contents.PARAMS_ERROR);
				rs.setMsg("参数错误");
			}

			BeanMenuBook m = menuBookMapper.findById(menuBook.getId());// 读取源文件url
																		// 需要菜单id
			if (!ValidateUtils.isBlank(m)) {
				rs.put("fileNmae", m.getName());
				String resPath = FileUploadUtils.class.getClassLoader().getResource("../../").getPath();

				File file = new File(resPath + m.getFilePath());// 源文件
				byte[] bytes = new byte[] {};// 初始化源文件字节数组
				if (file.exists()) {
					FileInputStream fi = new FileInputStream(file);// 源文件路径
					bytes = new byte[(int) file.length()];
					fi.read(bytes);
					fi.close();
				}

				String path = "/upload/menupms/" + DateUtils.getLocalDate("yyyyMMdd");
				String fullPath = resPath + path;
				File file1 = new File(fullPath);
				if (!file1.exists()) {
					file1.mkdirs();
				}
				String fileName = menuBook.getName() + ".pms";
				File downFile = new File("E:" + File.separator + fileName);
				FileOutputStream fo = new FileOutputStream(downFile);// downFile：新文件路径
				fo.write(bytes);// 输出
				fo.close();

				path = fileName;
				rs.put("path", path);
			}
		}

	}
}
