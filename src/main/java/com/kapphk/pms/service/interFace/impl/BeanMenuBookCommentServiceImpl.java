package com.kapphk.pms.service.interFace.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kapphk.pms.bean.BeanMenuBookComment;
import com.kapphk.pms.dao.mapper.BeanMenuBookCommentMapper;
import com.kapphk.pms.service.interFace.InterfaceBeanMenuBookCommentService;

/**   
 * @ClassName: BeanMenuBookCommentServiceImpl    
 * @Description: 菜谱评论管理  
 * @author XS  
 * @date 2016-1-26 下午4:59:15    
 *         
 */
@Service
public class BeanMenuBookCommentServiceImpl implements
		InterfaceBeanMenuBookCommentService {
	
	@Autowired
	private BeanMenuBookCommentMapper menuBookCommentMapper;

	/**   
	* @Title: findByCondition    
	* @Description: TODO(这里用一句话描述这个方法的作用)    
	* @param @param condition
	* @param @return    设定文件    
	* @throws    
	*/
	@Override
	public List<Map<String, Object>> findByCondition(
			Map<String, Object> condition) {
		 return menuBookCommentMapper.findByCondition(condition);
	}

	/**   
	* @Title: insert    
	* @Description: TODO(这里用一句话描述这个方法的作用)    
	* @param @param menuBookComment    设定文件    
	* @throws    
	*/
	@Override
	public void insert(BeanMenuBookComment menuBookComment) {
		 menuBookCommentMapper.insert(menuBookComment);
		
	}

	
//	@Override
//	public Result findCommentList(BeanMenuBookComment beanMenuBookComment, String menuId, int page, int rows) {
//		Result rs = new Result();	
//		
//		List<Map<String,Object>> list = menuBookCommentMapper.findCommentList(beanMenuBookComment, menuId, offset, pageSize)(appAndroidUpdate, versionName, startTime, endTime, (page-1)*rows, rows);
//		int count = appAndroidUpdateMapper.getAppAndroidCount(appAndroidUpdate, versionName, startTime, endTime, (page-1)*rows, rows);
//		rs.put("total", count);
//		rs.put("rows", list);
//		
//		return rs;
//	}



}
