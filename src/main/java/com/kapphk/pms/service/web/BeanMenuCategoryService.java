package com.kapphk.pms.service.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.kapphk.pms.bean.BeanMenuCategory;
import com.kapphk.pms.service.BaseService;
import com.kapphk.pms.util.Result;

public interface BeanMenuCategoryService extends BaseService<BeanMenuCategory, Long>{

	/**
	 * 查询菜谱一级分类数据列表
	 * @param searchName 分类名称
	 * @param page 页数
	 * @param rows 每页显示的行数
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-20
	 */
	public Result getFirstCategoryList(String searchName, int page, int rows)throws Exception;

	/**
	 * 新增或修改菜谱一级分类
	 * @param menuCategory 对象
	 * @return result
	 * @throws Exception
	 * @author huangzh 2016-01-20
	 */
	public Result saveFirstCategory(BeanMenuCategory menuCategory,
			MultipartFile file, HttpServletRequest req)throws Exception;

	/**
	 * 查询菜谱二级分类数据列表
	 * @param searchName 分类名称
	 * @param page 页数
	 * @param rows 每页显示的行数
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-20
	 */
	public Result getSecondCategoryList(String searchName,String recommend, int page, int rows)throws Exception;

	/**
	 * 查询一级分类下拉框数据
	 * @return List
	 * @throws Exception
	 * @author huangzh 2016-01-20
	 */
	public List<Map<String, Object>> getFirstCombox()throws Exception;

	/**
	 * 新增或修改菜谱二级分类
	 * @param menuCategory 对象
	 * @return result
	 * @throws Exception
	 * @author huangzh 2016-01-20
	 */
	public Result saveTowCategory(BeanMenuCategory menuCategory,
			MultipartFile file, HttpServletRequest req)throws Exception;

	/**
	 * 是否推荐
	 * @param id 分类id
	 * @param recommend 推荐标记字段
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-20
	 */
	public Result recommend(Long id, String recommend)throws Exception;

	/**
	 * 根据一级id查询二级列表
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-20
	 */
	public List<Map<String, Object>> getSecondCombox(Long firstId)throws Exception;
	
	
	/**
	 * * 暂停或者使用菜谱一级或者二级分类
	 * @param ids id
	 * @param type 状态
	 * @param rank 级别
	 * @return
	 */
	public Result upCategoryStatus(List<Long> ids,String type,String rank);

	/**
	 * 查询一级菜谱状态
	 * @param asList
	 * @return
	 */
	public Result getCategoryStatus(List<Long> ids);


}
