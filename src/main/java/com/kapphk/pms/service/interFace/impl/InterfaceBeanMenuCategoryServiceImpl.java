package com.kapphk.pms.service.interFace.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kapphk.pms.bean.MenuFirstCategory;
import com.kapphk.pms.bean.PaginationCondition;
import com.kapphk.pms.dao.mapper.BeanMenuCategoryMapper;
import com.kapphk.pms.service.interFace.InterfaceBeanMenuCategoryService;

/**   
 * @ClassName: InterfaceBeanMenuCategoryServiceImpl    
 * @Description: TODO(这里用一句话描述这个类的作用)    
 * @author XS  
 * @date 2016-1-26 上午10:46:05    
 *         
 */
@Service
public class InterfaceBeanMenuCategoryServiceImpl implements InterfaceBeanMenuCategoryService{

	 @Autowired
	 private BeanMenuCategoryMapper menuCategoryMapper;
	
	/**   
	* @Title: findByCondition    
	* @Description: TODO(这里用一句话描述这个方法的作用)    
	* @param @return    设定文件    
	* @throws    
	*/
	@Override
	public List<Map<String, Object>> findByCondition(Map<String,Object>condition) {
		 return menuCategoryMapper.findByCondition(condition);
	}

	/**   
	* @Title: findPagination    
	* @Description: TODO(这里用一句话描述这个方法的作用)    
	* @param @param condition
	* @param @param pc
	* @param @return    设定文件    
	* @throws    
	*/
	@Override
	public List<Map<String, Object>> findPagination(
			Map<String, Object> condition, PaginationCondition pc) {
		return menuCategoryMapper.findPagination(condition, pc);
	}
	
	

	/**   
	* @Title: findTowMenuCategory    
	* @Description:  查询一级，二级分类关系   
	* @param     设定文件    
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-26 下午4:03:37
	*/
	public List<MenuFirstCategory> findTowMenuCategory( Long conditionId, PaginationCondition pc){
		return menuCategoryMapper.findTowMenuCategory(conditionId, pc);
	};

}
