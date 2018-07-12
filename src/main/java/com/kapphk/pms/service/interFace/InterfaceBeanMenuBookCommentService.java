package com.kapphk.pms.service.interFace;

import java.util.List;
import java.util.Map;

import com.kapphk.pms.bean.BeanMenuBookComment;

/**   
 * @ClassName: InterfaceBeanMenuBookCommentService    
 * @Description: 菜谱评论管理
 * @author XS  
 * @date 2016-1-26 下午4:58:45    
 *         
 */
public interface InterfaceBeanMenuBookCommentService {
	
	
	/**   
	* @Title: findByCondition    
	* @Description: 查询菜谱评论   
	* @param     设定文件    
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-26 下午5:04:49
	*/
	public List<Map<String, Object>> findByCondition(Map<String, Object> condition);
	
	public void insert(BeanMenuBookComment menuBookComment);
	
	
	
//	/**
//	 * 分页查询菜谱对应评论
//	 * huzi 2018-06-07
//	 * 
//	 */
//	public Result findCommentList(BeanMenuBookComment beanMenuBookComment, String menuId, int page, int rows);
	
	

}
