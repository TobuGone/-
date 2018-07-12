package com.kapphk.pms.controller.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kapphk.pms.bean.BeanMenuBook;
import com.kapphk.pms.bean.BeanMenubookProcedure;
import com.kapphk.pms.dao.mapper.BeanFoodStoreMapper;
import com.kapphk.pms.dao.mapper.BeanMenuBookMapper;
import com.kapphk.pms.service.web.BeanMenuBookService;
import com.kapphk.pms.util.ChineseJudgeUtils;
import com.kapphk.pms.util.Contents;
import com.kapphk.pms.util.FileUploadUtils;
import com.kapphk.pms.util.Result;
import com.kapphk.pms.util.Result.MSG;
import com.kapphk.pms.util.ValidateUtils;

import jxl.read.biff.BiffException;

/**
 * 菜谱控制层
 * implements ServletContextListener
 * @author huangzh 2016-01-21
 */
@RestController
@RequestMapping("/menuBook/")
public class BeanMenuBookController{

	@Autowired
	private BeanFoodStoreMapper storeMapper;

	@Autowired
	private BeanMenuBookService service;

	@Autowired
	private BeanMenuBookMapper menuBookMapper;
	
//	@Value("${filePath}")
//	private static String filePath;
	/**
	 * 查询盖头图片列表
	 * 
	 * @param page
	 *            页数
	 * @param rows
	 *            每页显示的行数
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-21
	 */
	@RequestMapping("getAdvertList.htm")
	public Result getAdvertList(int page, int rows) throws Exception {
		Result rs = new Result();

		rs = service.getAdvertList(page, rows);

		return rs;
	}

	/**
	 * 设置或修改盖头图片
	 * 
	 * @param id
	 *            菜谱id
	 * @param menuId
	 *            要修改的菜谱id
	 * @param status
	 *            状态
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-21
	 */
	@RequestMapping("saveAdvert.htm")
	public Result saveAdvert(Long id, Long menuId, String status) throws Exception {
		Result rs = new Result();

		rs = service.saveAdvert(id, menuId, status);

		return rs;
	}

	/**
	 * 删除盖头广告
	 * 
	 * @param id
	 *            菜谱id
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-21
	 */
	@RequestMapping("delAdvert.htm")
	public Result delAdvert(String ids) throws Exception {
		Result rs = new Result();

		rs = service.delAdvert(ids);

		return rs;
	}

	/**
	 * 查询菜谱列表
	 * 
	 * @param searchName
	 *            菜谱名称
	 * @param page
	 *            页数
	 * @param rows
	 *            每页显示的行数
	 * @return result
	 * @throws Exception
	 * @author huangzh 2016-01-21
	 */
	@RequestMapping("searchMenuBookList.htm")
	public Result searchMenuBookList(String searchName, String recommend, int page, int rows) throws Exception {
		Result rs = new Result();

		rs = service.searchMenuBookList(searchName, recommend, page, rows);

		return rs;
	}

	/**
	 * 新增或修改菜谱
	 * 
	 * @param beanMenuBook
	 *            对象
	 * @param firstId
	 *            一级分类id
	 * @param secondId
	 *            二级分类id
	 * @param addNumber
	 *            步骤数量
	 * @param req
	 * @param file
	 *            文件流
	 * @return Result
	 * @throws Exception
	 * @author huangzh 2016-01-21
	 * 
	 *         修改于2018-03-09 huzi 修改内容：新增食材预处理部分
	 * 
	 */
	@RequestMapping("saveMenuBook.htm")
	public Result saveMenuBook(BeanMenuBook beanMenuBook, int addNumber, int categoryNumber, HttpServletRequest req,
			MultipartFile file) throws Exception {
		Result rs = new Result();
		BeanMenuBook bmb = new BeanMenuBook();
		bmb.setUid(beanMenuBook.getUid());
		bmb.setName(beanMenuBook.getName());
		List<BeanMenuBook> findAll = menuBookMapper.findAll(bmb);
		if (ValidateUtils.isBlank(beanMenuBook.getId())) {
			if (findAll.size() > 0) {
				rs.setStatus(Contents.ERROR);
				rs.setMsg("菜谱名已存在");
				return rs;
			}
		} else if (!ValidateUtils.isEmptyForCollection(findAll)) {
			if (findAll.get(0).getId() - beanMenuBook.getId() != 0) {
				rs.setStatus(Contents.ERROR);
				rs.setMsg("菜谱名已存在");
				return rs;
			}
		}

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> storeList = storeMapper.find();
		Map<String, Object> map1 = new HashMap<String, Object>();
		for (Map<String, Object> m : storeList) {
			map1.put(m.get("id").toString(), m.get("name"));
		}

		List<List<String>> foodName = (List<List<String>>) req.getSession().getAttribute("foodName");

		for (Map<String, Object> m : storeList) {
			map1.put(m.get("id").toString(), m.get("name"));
		}

		String[] stores = req.getParameter("foodc").split(";"); // 食材UID
		String[] values = req.getParameter("foodv").split(";"); // 食材重量
		String[] yvs = req.getParameter("foody").split(";"); 	// 食材预处理 huzi

		for (int i = 1; i <= addNumber; i++) {// 数组长度
			Map<String, Object> map = new HashMap<String, Object>();
			String minute = req.getParameter("minute" + i);// 分
			String second = req.getParameter("second" + i);// 秒
			if (ValidateUtils.isBlank(minute) && ValidateUtils.isBlank(second)) {
				continue;
			}
			map.put("minute", minute);

			map.put("second", second);

			map.put("describe", req.getParameter("describe" + i));

			String foods = "";
			String[] store = stores[i - 1].split(",");// [3020001]
			String[] value = values[i - 1].split(",");// [32]
			String[] yv = yvs[i - 1].split(","); // 无 huzi
			for (int j = 0; j < yv.length; j++) {
				if (!yv[j].equals(" ")) { // 如果食材数据为空字符，则直接赋为空字符
					for (int z = 0; z < store.length; z++) { // 当一个步骤里出现多个食材数据时，遍历并拼接每个食材的数据
						foods += store[z] + "|";

						if (map1.containsKey(store[z])) {
							foods += map1.get(store[z]);// 8020015|
						} else {
							foods += foodName.get(i - 1).get(z).trim();
						}
						
						if (!ChineseJudgeUtils.isMessyCode(yv[z])) {
							yv[z] = new String(yv[z].getBytes("ISO8859-1"), "utf-8");// 如果是增量更新到云服务器之前的项目中，需将此条代码注释掉
						}
						foods += "|" + value[z].trim() + "|" + yv[z].trim() + ";";// 8020015|花生油|30|无;
					}
					if (yv.length > 1) { // 避免食材是一个以上的步骤数据整条重复循环
						break;
					}
				} else {
					foods += "";
				}
			}
			map.put("foods", foods);
			list.add(map);
		}

		List<Map<String, Object>> clist = new ArrayList<Map<String, Object>>();

		for (int i = 1; i <= categoryNumber; i++) {
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("firstId", req.getParameter("firstId" + i));

			map.put("secondId", req.getParameter("secondId" + i));

			clist.add(map);
		}

		rs = service.saveMenuBook(beanMenuBook, req, file, list, clist);

		return rs;
	}

	/**
	 * 查询单条数据
	 * 
	 * @param id
	 * @return huangzh 2015-08-14
	 * @throws Exception
	 */
	@RequestMapping("data.htm")
	public Result data(Long id, HttpServletRequest request) throws Exception {
		Result rs = new Result();

		BeanMenuBook data = null;

		try {
			data = service.findMenuBookById(id);

			if (data != null) {
				List<Map<String, Object>> list = service.findCategoryIds(id);

				data.setList(list);
			}
			if (!ValidateUtils.isEmptyForCollection(data.getProcedures())) {
				List<BeanMenubookProcedure> pr = data.getProcedures();
				List<List<String>> foodName = new ArrayList<List<String>>();
				for (BeanMenubookProcedure mp : pr) {
					List<String> li = new ArrayList<String>();
					if (!ValidateUtils.isBlank(mp.getFoods())) {
						String[] split = mp.getFoods().split(";");
						for (String s : split) {
							String[] split2 = s.split("\\|");
							li.add(split2[1]);
						}
					}
					foodName.add(li);
				}
				request.getSession().setAttribute("foodName", foodName);
			}

		} catch (Exception e) {
			rs.setError(MSG.ERROR);
			rs.setMsg("请求失败");
			e.printStackTrace();
		}

		if (null == data)
			rs.setError(MSG.NOT_FOUND, "查询失败", "信息");
		else
			rs.put("data", data);
		return rs;
	}

	/**
	 * 批量删除
	 * 
	 * @param ids
	 *            id集合
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-08-14
	 */
	@RequestMapping("delMenuBook.htm")
	public Result delMenuBook(Long[] ids) throws Exception {
		Result rs = new Result();
		
		
		rs = service.delMenuBook(Arrays.asList(ids));

		return rs;
	}

	/**
	 * 导入菜谱
	 * 
	 * @param importCategoryNumber
	 * @param req
	 * @param file
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-08-14
	 */
	@RequestMapping("saveImport.htm")
	public Result saveImport(int importCategoryNumber, HttpServletRequest req, MultipartFile file) throws Exception {
		Result rs = new Result();

		List<Map<String, Object>> clist = new ArrayList<Map<String, Object>>();

		for (int i = 1; i <= importCategoryNumber; i++) {
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("firstId", req.getParameter("import_firstId" + i));

			map.put("secondId", req.getParameter("import_secondId" + i));

			clist.add(map);
		}
		Long userId = (long) -1;
		rs = service.saveImport(clist, req, file, userId);

		return rs;
	}

	/**
	 * 根据食材学名查询菜谱（下拉框）
	 * 
	 * @param name
	 *            食材学名
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-08-14
	 */
	@RequestMapping("getMenuBookComboxByName.htm")
	public List<Map<String, Object>> getMenuBookComboxByName(String name, String isAdvert, String recommend)
			throws Exception {
		List<Map<String, Object>> maps = service.getMenuBookComboxByName(name, isAdvert, recommend);
		return maps;
	}

	/**
	 * 根据审核状态查询菜谱列表
	 * 
	 * @param searchNickName
	 *            用户昵称
	 * @param AuditStatus
	 *            审核状态（1、通过，0、不通过，2、待审核）
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-08-14
	 */
	@RequestMapping("getMenuBookListByAuditStatus.htm")
	public Result getMenuBookListByAuditStatus(String searchNickName, String searchMenuBookName, String AuditStatus,
			int page, int rows) throws Exception {
		Result rs = new Result();

		rs = service.getMenuBookListByAuditStatus(searchNickName, searchMenuBookName, AuditStatus, page, rows);

		return rs;
	}

	/**
	 * 查询用户收藏的菜谱列表
	 * 
	 * @param searchNickName
	 *            用户昵称
	 * @param page
	 *            页数
	 * @param rows
	 *            每页显示的行数
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-08-14
	 */
	@RequestMapping("getCollectList.htm")
	public Result getCollectList(String searchNickName, int page, int rows) throws Exception {
		Result rs = new Result();

		rs = service.getCollectList(searchNickName, page, rows);

		return rs;
	}

	/**
	 * 菜谱审核通过
	 * 
	 * @param id
	 *            菜谱id
	 * @param categoryNumber
	 *            分类次数
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-08-14
	 */
	@RequestMapping("saveAdopt.htm")
	public Result saveAdopt(Long id, int categoryNumber, HttpServletRequest req) throws Exception {
		Result rs = new Result();

		List<Map<String, Object>> clist = new ArrayList<Map<String, Object>>();

		for (int i = 1; i <= categoryNumber; i++) {
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("firstId", req.getParameter("firstId" + i));

			map.put("secondId", req.getParameter("secondId" + i));

			clist.add(map);
		}

		rs = service.saveAdopt(id, categoryNumber, clist);

		return rs;
	}

	/**
	 * 菜谱审核不通过
	 * 
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-08-14
	 */
	@RequestMapping("saveNoAdopt.htm")
	public Result saveNoAdopt(BeanMenuBook beanMenuBook) throws Exception {
		Result rs = new Result();

		rs = service.saveNoAdopt(beanMenuBook);

		return rs;
	}

	/**
	 * 保存推荐的菜谱
	 * 
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-08-14
	 */
	@RequestMapping("saveRecommend.htm")
	public Result saveRecommend(Long menuId) throws Exception {
		Result rs = new Result();

		rs = service.saveRecommend(menuId);

		return rs;
	}

	/**
	 * 推荐的菜谱
	 * 
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-08-14
	 */
	@RequestMapping("recommendMenuBook.htm")
	public Result recommendMenuBook(Long id, String recommend) throws Exception {
		Result rs = new Result();

		rs = service.recommendMenuBook(id, recommend);

		return rs;
	}

	/**
	 * 设为盖头广告
	 * 
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-08-14
	 */
	@RequestMapping("setAdvert.htm")
	public Result setAdvert(Long id, String isAdvert) throws Exception {
		Result rs = new Result();

		rs = service.setAdvert(id, isAdvert);

		return rs;
	}

	/**
	 * 设为最新
	 * 
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-08-14
	 */
	@RequestMapping("setNewTime.htm")
	public Result setNewTime(Long id, String isNew) throws Exception {
		Result rs = new Result();

		rs = service.setNewTime(id, isNew);

		return rs;
	}

	/**
	 * 更新上传的文件路径
	 * 
	 * @param beanMenuBook
	 * @return
	 */
	@RequestMapping("upFilePath.htm")
	public Result upFilePath(BeanMenuBook beanMenuBook, int importCategoryNumber, HttpServletRequest req)
			throws Exception {

		List<Map<String, Object>> clist = new ArrayList<Map<String, Object>>();

		for (int i = 1; i <= importCategoryNumber; i++) {
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("firstId", req.getParameter("import_firstId" + i));

			map.put("secondId", req.getParameter("import_secondId" + i));

			clist.add(map);
		}
		return service.upFilePath(beanMenuBook, clist);

	}

	/**
	 * 根据食材id查询他的父类
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("getFoodStorCombox.htm")
	public Map<String, Long> getFoodStorCombox(Long id) {
		List<Long> list = storeMapper.findByStoreId(id);
		HashMap<String, Long> map = new HashMap<String, Long>();
		if (list.get(0) > list.get(1)) {
			map.put("two", list.get(0));
			map.put("one", list.get(1));
		} else {
			map.put("one", list.get(0));
			map.put("two", list.get(1));
		}

		return map;
	}

	/**
	 * 批量下载服务器上菜谱文件至本地 2018-04-18 huzi
	 * 
	 * @param request
	 * @param myfile
	 * @return
	 * @throws IOException
	 * @throws BiffException
	 */
	@RequestMapping("exportMenuBook.htm")
	public Result exportMenuBook(HttpServletRequest request,HttpServletResponse response, String ids) throws BiffException, IOException {
		Result rs = new Result();

		List<BeanMenuBook> menuBookList = new ArrayList<BeanMenuBook>();
		String[] s1 = ids.split(",");

		int len;
		
		try {
			//遍历id
			for (String s : s1) {
				long id = Long.parseLong(s);
				BeanMenuBook mb = service.findById(id);
				menuBookList.add(mb);
			}
			
			Properties props=System.getProperties();	  //获得系统属性集  
			String osName = props.getProperty("os.name"); //操作系统名称
			
			File zipFile;
			if ("Linux".equals(osName)) {
				zipFile  = new File("/usr/local/java"+ File.separator + "menuBook.zip");//定义zip文件   Linux
			}
			else {
				zipFile  = new File("E:"+ File.separator + "批量菜谱文件.zip");//定义zip文件    Windows
			}
			
			if(!zipFile.exists()){
				zipFile.createNewFile();
			}
			
			@SuppressWarnings("resource")
			ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipFile));//声明zip文件输出流
			FileInputStream currentFileInputStream = null;
			File currentFile = null;
			String currentFileName = "";
			
			for (int i = 0; i < menuBookList.size(); i++) {
				BeanMenuBook menuBook = menuBookList.get(i);
				if (ValidateUtils.isBlank(menuBook.getId()) || ValidateUtils.isBlank(menuBook.getUid())) {
					throw new Exception("菜谱id不能为空！");
				}
				
				//根据id查询菜谱用于读取源文件url
				BeanMenuBook m = menuBookMapper.findById(menuBook.getId());
				currentFileName = m.getName()+".pms";
				
				String resPath = FileUploadUtils.class.getClassLoader().getResource("../../").getPath();//相对路径
				currentFile = new File(resPath + m.getFilePath());// 源文件路径
				
//				currentFile = new File(request.getContextPath() + "/" + m.getFilePath());//源文件对象（根据路径得到）
				byte[] bytes = new byte[1024];
				currentFileInputStream = new FileInputStream(currentFile);
	            zout.putNextEntry(new ZipEntry(currentFileName));    
	            while ((len = currentFileInputStream.read(bytes)) > 0) {  
	                zout.write(bytes, 0, len);  
	            }  
	            zout.closeEntry();  
	            currentFileInputStream.close();  
			}
	        zout.close();
	        
			 //下载文件
	        FileInputStream zipInput =new FileInputStream(zipFile);
	        OutputStream out = response.getOutputStream();
	        response.setContentType("application/octet-stream");
	        response.setHeader("Content-Disposition", "attachment; filename=menuBook.zip");
	        byte[] buf = new byte[1024];
	        while ((len= zipInput.read(buf))!= -1){  
	            out.write(buf,0,len);  
	        }
	        zipInput.close();
	        out.flush();
	        out.close();
	        //删除压缩包
	        zipFile.delete();
	        
		} catch (Exception e) {
			e.printStackTrace();
			rs.setStatus(Contents.PARAMS_ERROR);
		}	
		return rs;
	}
	
	
}
