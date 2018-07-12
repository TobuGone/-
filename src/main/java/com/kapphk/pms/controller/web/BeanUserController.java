package com.kapphk.pms.controller.web;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kapphk.pms.bean.BeanUserMenuCategory;
import com.kapphk.pms.dao.mapper.BeanUserMenuCategoryMapper;
import com.kapphk.pms.service.web.BeanUserService;
import com.kapphk.pms.util.Contents;
import com.kapphk.pms.util.FileConstant;
import com.kapphk.pms.util.Result;
import com.kapphk.pms.util.Result.MSG;
import com.kapphk.pms.util.UserExcelImportAndExportUtils;
import com.kapphk.pms.util.ValidateUtils;

import jxl.read.biff.BiffException;

/**
 * 手机端用户控制层
 * @author huangzh 2016-01-14
 */

@RestController
@RequestMapping("/userinfo/")
public class BeanUserController {

	@Autowired
	private BeanUserService service;
	
	@Autowired
	private BeanUserMenuCategoryMapper userMenuCategoryMapper;
	
	
	/**
	 * 查询手机用户数据列表
	 * @param page 页数
	 * @param rows 每页显示的行数
	 * @return
	 * @throws Exception
	 * @author huzi 2018-06-21
	 */
	@RequestMapping("getUserInfoList.htm")
	public Result getUserInfoList(String searchUserName,String searchNickName,String searchNumber,int page,int rows)throws Exception{
		Result rs = new Result();
		rs = service.getUserInfoList(searchUserName,searchNickName,searchNumber,page,rows);
		
		return rs;
	}
	
	
	/**
	 * 查询单条数据
	 * @param id
	 * @return
	 * huangzh 2015-11-12
	 * @throws Exception
	 */
	@RequestMapping("data.htm")
	public Result data(Long id)throws Exception{
		Result rs = new Result();
		
		Map<String, Object> data = null;
		try {
			data = service.findMapById(id);
		} catch (Exception e) { 
			rs.setError(MSG.ERROR);
			rs.setMsg("请求失败");
			e.printStackTrace();
		}
		
		if(null == data) rs.setError(MSG.NOT_FOUND, "查询失败", "信息");
		else rs.put("data", data);
		return rs;
	}
	
	
	
	 /**
	  * 批量导入excel表单数据
	  * 
	  * @param request
	  * @param myfile
	  * @return
	  * @author huzi 2018-04-23
	  */
	 @RequestMapping("importExcel.htm")
	 public Result userImportExcel(@RequestParam("myfile") MultipartFile myFile) {
		   Result rs = new Result();
			try {
				// 获得文件名
				Workbook workbook = null;
				String fileName = myFile.getOriginalFilename();
				if (fileName.endsWith(UserExcelImportAndExportUtils.XLS)) {
					// 2003
					workbook = new HSSFWorkbook(myFile.getInputStream());
				} else if (fileName.endsWith(FileConstant.XLSX)) {
					// 2007
					workbook = new XSSFWorkbook(myFile.getInputStream());
				} 
				rs = UserExcelImportAndExportUtils.importExcel(service, userMenuCategoryMapper, workbook, rs);
				
			} catch (Exception e) {
//				e.printStackTrace();
				rs.setMsg(e.getMessage());
				rs.setStatus(Contents.CUS_ERROR);
			}
			  return rs;
	 }
	 
	 
	 /**
	  * 批量导出数据库数据
	  * 
	  * @param request
	  * @param myfile
	  * @return
	  * @throws IOException 
	  * @throws BiffException 
	  * @author huzi 2018-04-23
	  */
	 @RequestMapping("exportExcel.htm")
	 public void userExportExcel(HttpServletResponse response,String ids) throws BiffException, IOException { 
		  try {
			  
			  String[] s1 = ids.split(",");
			  
			  List<Map> userList = new ArrayList<Map>();
			  BeanUserMenuCategory userMenuCategory = null;
			  List<BeanUserMenuCategory> userMenuCategoryList = null ;
			  List<List<BeanUserMenuCategory>> userMenuCategoryListData = new ArrayList<>();
			  
			  for (String s : s1) {
				  
				  long id = Long.parseLong(s);
				  
				  //查询 用户表
				  Map<String, Object> user = service.findMapById(id);
				  userList.add(user);
				  
				  //查询 用户-菜谱分类 中间表
				  userMenuCategory = new BeanUserMenuCategory();
				  userMenuCategory.setUid(id);
				  //这里的 0 100 只是为了规范  并无实际意义 不参与sql查询
				  userMenuCategoryList = userMenuCategoryMapper.findByPage(userMenuCategory, 0, 100);
				  userMenuCategoryListData.add(userMenuCategoryList);
				  
			  }
			  
			  HSSFWorkbook wb = UserExcelImportAndExportUtils.export(userList,userMenuCategoryListData);
			  
			  //输出Excel文件
			  OutputStream output=response.getOutputStream();
			  response.reset();
			  SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
			  String fileName = df.format(new Date());// new Date()为获取当前系统时间
			  response.setHeader("Content-disposition", "attachment; filename="+fileName+".xls");
			  response.setContentType("application/msexcel");  
			  wb.write(output);
			  output.flush();
			  output.close();
			  
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
	 }
	 
	 
		/**
		 * 批量删除
		 * @param  ids 	id集合
		 * @return
		 * @throws Exception
		 * @author huzi 2018-04-23
		 */
		@RequestMapping("delUsers.htm")
		public Result delUsers(Long[] ids)throws Exception{
			Result rs = new Result();
			
			rs = service.delUsers(Arrays.asList(ids));
			return rs;
		}
	 
		
		/**
		 * 禁用或使用用户
		 * @param  ids 	id集合
		 * @param  type 状态参数
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("upUserStatus.htm")
		public Result upUserStatus(Long[] ids,String type) throws Exception{
			Result rs = new Result();
			if(!ValidateUtils.isEmptyForCollection(Arrays.asList(ids))){
				int count = service.upUserStatus(Arrays.asList(ids), type);
				rs.put("count", count);
				return rs;
			}else{
				rs.setStatus(Contents.PARAMS_ERROR);
				rs.setMsg("参数错误!!");
				return rs;
			}
		}
}
	