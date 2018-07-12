package com.kapphk.pms.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kapphk.pms.bean.BeanMenuBookComment;

/**
 * 评论用的数据操作接口
 * @author zoneyu 2016-01-26
 * 
 * 更新于2018/03/12  huzi
*/
public interface BeanMenuBookCommentMapper extends BaseMapper<BeanMenuBookComment, Long> {
	
	  public List<Map<String, Object>> findByCondition(@Param("param") Map<String, Object> condition);
	
	  public List<Map<String, Object>> getList(
			  @Param("param") BeanMenuBookComment paramBeanMenuBookComment, 
			  @Param("name") String paramString, 
			  @Param("page") int paramInt1, 
			  @Param("rows") int paramInt2);
	  
	  public int getListCount(@Param("param") BeanMenuBookComment paramBeanMenuBookComment, @Param("name") String paramString);
	
	  
//	  
//	  	/**
//		 * 分页查找数据
//		 * huzi 2018-06-07
//		 * 
//		 */
//		public List<Map<String,Object>> findCommentList(
//				@Param(value = "param") BeanMenuBookComment beanMenuBookComment, 
//				@Param(value = "menuId")String menuId, 
//				@Param(value = "offset") int offset,
//				@Param(value = "pageSize") int pageSize);
//		
//		
//		/**
//		 * 分页查找总条数
//		 * huzi 2018-06-07
//		 */
//		public int findCommentListCount(
//				@Param(value = "param") BeanMenuBookComment beanMenuBookComment,
//				@Param(value = "menuId")String menuId,
//				@Param(value = "offset") int offset,
//				@Param(value = "pageSize") int pageSize);
		
}