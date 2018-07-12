package com.kapphk.pms.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kapphk.pms.bean.BeanFoodCategory;
import com.kapphk.pms.bean.FoodStoreFirstCategory;

/**
 * 分类的数据操作接口
 * @author zoneyu 2016-01-23
*/
public interface BeanFoodCategoryMapper extends BaseMapper<BeanFoodCategory, Long> {
	
	public List<Map<String, Object>> findSecondList(@Param(value="searchName")String searchName,
			@Param(value="page")Integer page, 
			@Param(value="rows")Integer rows);

	
	public int findSecondListCount(@Param(value="searchName")String searchName);

	/**
	 * 查询一级分类下拉框数据
	 * @return List
	 * @throws Exception
	 * @author huangzh 2016-01-20
	 */
	public List<Map<String, Object>> getFirstCombox();


	/**
	 * 查询二级级分类下拉框数据
	 * @return List
	 * @throws Exception
	 * @author huangzh 2016-01-20
	 */
	public List<Map<String, Object>> getSecondCombox(Long firstId);
	
	
	
	public List<BeanFoodCategory> findByCondition(@Param("param") Map<String, Object> condition);
	
	
	/**   
	* @Title: findTowFoodStoreCategory    
	* @Description: 查询食材的两级分类关系 
	* @param     设定文件    
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-26 下午3:18:26
	*/
	public List<FoodStoreFirstCategory> findTowFoodStoreCategory(@Param("param")Map<String, Object> param);

	/**
	 * * 暂停或者使用食材一级或者二级分类
	 * @param ids id
	 * @param type 状态
	 * @param rank 级别
	 * @return
	 */
	public int upCategoryStatus(@Param("ids")List<Long> ids,@Param("type")String type,@Param("rank")String rank);


	/**
	 * 查询一级食材状态
	 * @param asList
	 * @return
	 */
	public List<String> getCategoryStatus(@Param("ids")List<Long> ids);

	/**
	 * 根据实体真实
	 * @param param
	 * @return
	 */
	public List<BeanFoodCategory> findByCategory(@Param(value = "param")BeanFoodCategory param);

}