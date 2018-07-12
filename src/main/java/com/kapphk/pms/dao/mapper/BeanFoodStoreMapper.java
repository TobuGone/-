package com.kapphk.pms.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kapphk.pms.bean.BeanFoodStore;
import com.kapphk.pms.bean.FoodStoreFirstCategory;
import com.kapphk.pms.util.Result;
 

/**
 * 主键的数据操作接口
 * @author zoneyu 2016-01-27
*/
public interface BeanFoodStoreMapper extends BaseMapper<BeanFoodStore, Long> {
	/**
	 * 查询食材库数据列表
	 * @param searchName 食材学名
	 * @param page 页数
	 * @param rows 每页显示的行数
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-25
	 */
	public List<Map<String, Object>> findMapByPage(@Param(value="searchName")String searchName, @Param(value="page")Integer page, @Param(value="rows")Integer rows);

	public int findMapByPageCount(@Param(value="searchName")String searchName);

	/**
	 * 查询单条数据
	 * @param id
	 * @return
	 * huangzh 2015-08-14
	 * @throws Exception
	 */
	public Map<String, Object> findMapById(Long id);
	
	/**   
	* @Title: foodStoreCategory    
	* @Description: 查询食材分类和食材关系(包括一级分类和食材关系、二级分类和食材关系)  
	* @param     设定文件    
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-26 下午2:58:12
	*/
	public List<FoodStoreFirstCategory> foodStoreCategory(@Param("param")Map<String, Object> param);
	
	
	public List<Map<String,Object>> findByCondition(@Param("param")Map<String, Object> condition);
	
	/**   
	* @Description: 查询我的食材(包括一级分类)  
	* @param     设定文件    
	* @return     返回类型    
	* @author Lotus  
	* @date 2016-04-08
	*/
	public List<Map<String,Object>> findMyFoodByCondition(@Param("param")Map<String, Object> condition);

	
	public List<Map<String, Object>> getFoodStoreByCategoryId(@Param(value="categoryId")Long categoryId,
			@Param(value="standardStatus")Integer standardStatus);

	/**
	 * 食材下拉框数据
	 * @return List
	 * @throws Exception
	 * @author huangzh 2016-01-20
	 */
	public List<Map<String, Object>> getSecondCombox(@Param(value="foodId")Long foodId);

	/**
	 * 查询全部数据 返回id,name 
	 * @return
	 */
	public List<Map<String, Object>> find();

	/**
	 * 根据食材id查询他的父类
	 * @param id
	 * @return
	 */
	public List<Long> findByStoreId(@Param(value="id")Long id);
	
	/**
	 * 根据食材id查询菜谱
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> findMenuBookByFoodStore(@Param(value="storeId")Long storeId);

	/**
	 * 据食材名称判断是否存在
	 */
	public Long findByFoodName(@Param(value="name")String name);


}