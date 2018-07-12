package com.kapphk.pms.controller.web;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kapphk.pms.bean.BeanFoodStore;
import com.kapphk.pms.bean.BeanFoodstoreCategory;
import com.kapphk.pms.dao.mapper.BeanFoodstoreCategoryMapper;
import com.kapphk.pms.service.web.BeanFoodStoreService;
import com.kapphk.pms.util.Contents;
import com.kapphk.pms.util.FileConstant;
import com.kapphk.pms.util.FoodStoreExcelImportAndExportUtils;
import com.kapphk.pms.util.Result;
import com.kapphk.pms.util.Result.MSG;

import jxl.read.biff.BiffException;

/**
 * 食材库控制层
 * @author huangzh 2016-01-25
 */
@RestController
@RequestMapping("/foodstore/")
public class BeanFoodStoreController {

	@Autowired
	private BeanFoodStoreService foodStoreservice;
	
//	@Autowired
//	private BeanFoodCategoryService foodCategoryservice;

	@Autowired
	private BeanFoodstoreCategoryMapper foodstoreCategoryMapper;
	
	
	/**
	 * 查询食材库数据列表
	 * @param searchName 食材学名
	 * @param page 页数
	 * @param rows 每页显示的行数
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-25
	 */
	@RequestMapping("searchFoodStoreList.htm")
	public Result searchFoodStoreList(String searchName,int page,int rows)throws Exception{
		Result rs = new Result();
		
		rs = foodStoreservice.searchFoodStoreList(searchName,page,rows);
		
		return rs;
	}
	
	/**
	 * 新增或修改食材库
	 * @param foodStore 食材库对象
	 * @param firstId 一级分类id
	 * @param secondId 二级分类id
	 * @param file 文件流
	 * @param req
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-25
	 */
	@RequestMapping("saveFoodStore.htm")
	public Result saveFoodStore(BeanFoodStore foodStore,Long firstId,Long secondId,Long [] menuId,MultipartFile file,HttpServletRequest req){
		Result rs = new Result();
		try {
			rs = foodStoreservice.saveFoodStore(foodStore,firstId,secondId,menuId,file,req);
			
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
			rs.setError(MSG.ERROR);
			rs.setMsg("请求失败");
			return rs;
		}		
	}
	
	
	/**
	 * 查询单条数据
	 * @param id
	 * @return
	 * huangzh 2015-08-14
	 * @throws Exception
	 */
	@RequestMapping("data.htm")
	public Result data(Long id)throws Exception{
		Result rs = new Result();
		
		Map<String, Object> data = null;
		
		try {
			data = foodStoreservice.findMapById(id);
			
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
	 * 删除食材库
	 * @param ids
	 * @return
	 * @throws Exception
	 * huangzh 2015-08-14
	 */
	@RequestMapping("delFoodStore.htm")
	public Result delFoodStore(Long[] ids)throws Exception{
		Result rs = new Result();
		
		rs = foodStoreservice.delFoodStore(ids);
		
		return rs;
	}
	
	/**
	 * 食材下拉框数据
	 * @return List
	 * @throws Exception
	 * @author huangzh 2016-01-20
	 */
	@RequestMapping("getSecondCombox.htm")
	public List<Map<String, Object>> getSecondCombox(Long foodId)throws Exception{
		List<Map<String, Object>> maps = foodStoreservice.getSecondCombox(foodId);
		return maps;
	}
	
	
	
	 /**
	  * 批量导入excel表单数据
	  * 
	  * @param request
	  * @param myfile
	  * @return
	  * huzi
	  */
	 @RequestMapping("importExcel.htm")
	 public Result foodStoreImportExcel(@RequestParam("myfile") MultipartFile myFile) {
		   Result rs = new Result();
			try {

				// 获得文件名
				Workbook workbook = null;
				String fileName = myFile.getOriginalFilename();
				if (fileName.endsWith(FileConstant.XLS)) {
					// 2003
					workbook = new HSSFWorkbook(myFile.getInputStream());
				} else if (fileName.endsWith(FileConstant.XLSX)) {
					// 2007
					workbook = new XSSFWorkbook(myFile.getInputStream());
				} 
				FoodStoreExcelImportAndExportUtils.importExcel(foodStoreservice, foodstoreCategoryMapper, workbook,rs);
			} catch (Exception e) {
				e.printStackTrace();
				rs.setMsg(e.getMessage());
				rs.setStatus(Contents.CUS_ERROR);
			}
			  return rs;
	 }
	 
	
	 /**
	  * 批量导出数据库数据       huzi 2018-04-18 
	  * 
	  * @param request
	  * @param myfile
	  * @return
	  * @throws IOException 
	  * @throws BiffException 
	  * huzi
	  */
	 @RequestMapping("exportExcel.htm")
	 public void foodStoreExportExcel(HttpServletResponse response,String ids) throws BiffException, IOException { 
		  try {
			  
			  String[] s1 = ids.split(",");
			  
			  List<Map> foodStoreList = new ArrayList<Map>();
			  BeanFoodstoreCategory foodstoreCategory = null;
			  List<BeanFoodstoreCategory> foodstoreCategoryList = null ;
			  List<List<BeanFoodstoreCategory>> foodstoreCategoryListData = new ArrayList<>();
			  
			  for (String s : s1) {
				  
				  long id = Long.parseLong(s);
				  
				  //查询 食材表
				  Map<String, Object> foodStore = foodStoreservice.findMapById(id);
				  foodStoreList.add(foodStore);
				  
				  //查询 食材-食材分类 中间表
				  foodstoreCategory = new BeanFoodstoreCategory();
				  foodstoreCategory.setStoreId(id);
				  //这里的 0 100 只是为了规范  并无实际意义 不参与sql查询
				  foodstoreCategoryList = foodstoreCategoryMapper.findByPage(foodstoreCategory, 0, 100);
				  foodstoreCategoryListData.add(foodstoreCategoryList);
				  
			  }
			  
			  HSSFWorkbook wb = FoodStoreExcelImportAndExportUtils.export(foodStoreList,foodstoreCategoryListData);
			  
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
	

	
	
}
