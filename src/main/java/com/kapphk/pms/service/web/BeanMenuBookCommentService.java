package com.kapphk.pms.service.web;

import com.kapphk.pms.bean.BeanMenuBookComment;
import com.kapphk.pms.util.Result;

/**
 * 评论service
 * @author 胡子
 * 2018/03/12
 */
public interface BeanMenuBookCommentService {
	
	public Result getList(BeanMenuBookComment bean, String name, int page, int rows, Result rs) throws Exception;
			  
	public Result delete(String ids, Result rs) throws Exception;
			  
	public Result operater(BeanMenuBookComment bean, Result rs) throws Exception;
	
}
