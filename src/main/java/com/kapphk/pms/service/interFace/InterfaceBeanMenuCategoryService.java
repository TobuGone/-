package com.kapphk.pms.service.interFace;

import java.util.List;
import java.util.Map;

import com.kapphk.pms.bean.MenuFirstCategory;
import com.kapphk.pms.bean.PaginationCondition;

/**   
 * @ClassName: BeanMenuCategoryService    
 * @Description: 菜谱分类管理   
 * @author XS  
 * @date 2016-1-26 上午10:45:15    
 *         
 */
public interface InterfaceBeanMenuCategoryService {
	
	public List<Map<String, Object>> findByCondition(Map<String,Object> condition);
	
	public List<Map<String,Object>> findPagination( Map<String,Object> condition,  PaginationCondition pc);
	
	
	/**   
	* @Title: findTowMenuCategory    
	* @Description:  查询一级，二级分类关系   
	* @param     设定文件    
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-26 下午4:03:37
	*/
	public List<MenuFirstCategory> findTowMenuCategory( Long condition, PaginationCondition pc);

	

}
