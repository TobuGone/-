package com.kapphk.pms.controller.web;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kapphk.pms.bean.BeanFoodCategory;
import com.kapphk.pms.service.web.BeanFoodCategoryService;
import com.kapphk.pms.util.Result;
import com.kapphk.pms.util.Result.MSG;

/**
 * 食材分类控制层
 * @author huangzh 2016-01-23
 */
@RestController
@RequestMapping("/foodcategory/")
public class BeanFoodCategoryController {
	
	@Autowired
	private BeanFoodCategoryService service;
	
	/**
	 * 查询食材一级分类数据列表
	 * @param searchName 分类名称
	 * @param page 页数
	 * @param rows 每页显示的行数
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-20
	 */
	@RequestMapping("getFirstCategoryList.htm")
	public Result getFirstCategoryList(String searchName,int page,int rows)throws Exception{
		Result rs = new Result();
		
		rs = service.getFirstCategoryList(searchName,page,rows);
		
		return rs;
	}
	
	
	/**
	 * 新增或修改食材一级分类
	 * @param menuCategory 对象
	 * @return result
	 * @throws Exception
	 * @author huangzh 2016-01-20
	 */
	@RequestMapping("saveFirstCategory.htm")
	public Result saveFirstCategory(BeanFoodCategory foodCategory)throws Exception{
		Result rs = new Result();
		
		rs = service.saveFirstCategory(foodCategory);
		
		return rs;
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
		
		BeanFoodCategory data = null;
		try {
			data = service.findById(id);
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
	 * 查询食材二级分类数据列表
	 * @param searchName 分类名称
	 * @param page 页数
	 * @param rows 每页显示的行数
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-20
	 */
	@RequestMapping("getSecondCategoryList.htm")
	public Result getSecondCategoryList(String searchName,int page,int rows)throws Exception{
		Result rs = new Result();
		
		rs  = service.getSecondCategoryList(searchName,page,rows);
		
		return rs;
	}
	
	
	/**
	 * 查询一级分类下拉框数据
	 * @return List
	 * @throws Exception
	 * @author huangzh 2016-01-20
	 */
	@RequestMapping("getFirstCombox.htm")
	public List<Map<String, Object>> getFirstCombox()throws Exception{
		List<Map<String, Object>> maps = service.getFirstCombox();
		return maps;
	}
	
	
	/**
	 * 新增或修改食材二级分类
	 * @param menuCategory 对象
	 * @return result
	 * @throws Exception
	 * @author huangzh 2016-01-20
	 */
	@RequestMapping("saveTowCategory.htm")
	public Result saveTowCategory(BeanFoodCategory foodCategory,MultipartFile file,HttpServletRequest req)throws Exception{
		Result rs = new Result();
		
		rs = service.saveTowCategory(foodCategory,file,req);
		
		return rs;
	}
	
	
	/**
	 * 查询二级级分类下拉框数据
	 * @return List
	 * @throws Exception
	 * @author huangzh 2016-01-20
	 */
	@RequestMapping("getSecondCombox.htm")
	public List<Map<String, Object>> getSecondCombox(Long firstId)throws Exception{
		List<Map<String, Object>> maps = service.getSecondCombox(firstId);
		return maps;
	}
	
	
	/**
	 * * 暂停或者使用食材一级或者二级分类
	 * @param ids id
	 * @param type 状态
	 * @param rank 级别 不为空时一级 为空时二级
	 * @return
	 */
	@RequestMapping("upCategoryStatus.htm")
	public Result upCategoryStatus(Long[] ids,String type,String rank){
		
		return service.upCategoryStatus(Arrays.asList(ids),type,rank);
	
	}
	
	@RequestMapping("getCategoryStatus.htm")
	public Result getCategoryStatus(Long[] pids){
		
		return service.getCategoryStatus(Arrays.asList(pids));
		
	}
	
}
