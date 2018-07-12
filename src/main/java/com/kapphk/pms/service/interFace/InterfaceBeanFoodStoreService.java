package com.kapphk.pms.service.interFace;

import java.util.List;
import java.util.Map;

import com.kapphk.pms.bean.BeanFoodStore;
import com.kapphk.pms.bean.FoodStoreFirstCategory;
import com.kapphk.pms.util.Result;

/**   
 * @ClassName: InterfaceBeanFoodStoreService    
 * @Description:食材分类管理   
 * @author XS  
 * @date 2016-1-25 下午4:18:54    
 *         
 */
public interface InterfaceBeanFoodStoreService {
	/**   
	* @Title: foodStoreCategory    
	* @Description: 查询食材分类和食材关系(包括一级分类和食材关系、二级分类和食材关系)  
	* @param     设定文件    
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-26 下午2:58:12
	*/
	public  List<FoodStoreFirstCategory> foodStoreCategory(Map<String, Object> param);

	/**   
	* @Title: findTowFoodStoreCategory    
	* @Description: 查询食材的两级分类关系 
	* @param     设定文件    
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-26 下午3:18:26
	*/
	public List<FoodStoreFirstCategory> findTowFoodStoreCategory(Map<String, Object> param);
	
	
	public List<Map<String,Object>> findByCondition(Map<String, Object> condition);
	
	public List<Map<String,Object>> findMyFoodByCondition(Map<String, Object> condition);
	
	/**   
	* @Title: saveFoodStore    
	* @Description: 添加食材 ；食材分类关系  
	* @param     设定文件    
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-25 下午6:25:30
	*/
	public void saveFoodStore(BeanFoodStore foodStore,Long firstCategoryId,Long secondCategoryId,Long userId);

	public Result getFoodStoreByCategoryId(Long categoryId,
			Integer standardStatus)throws Exception;

	/**
	 * 从外部文件中导入数据
	 *Lotus 2016-04-22
	 */
	public void inputDataFromFile(Result rs)throws Exception;

	public void findMenuBookByFoodStore(Long foodStoreId, Result rs)throws Exception;

}
