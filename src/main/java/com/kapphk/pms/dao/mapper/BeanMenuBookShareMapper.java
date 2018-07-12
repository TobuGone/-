package com.kapphk.pms.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kapphk.pms.bean.BeanMenuBookShare;

/**
 * 菜谱分享的数据操作接口
 * @author huzi 2018-05-18
*/
public interface BeanMenuBookShareMapper extends BaseMapper<BeanMenuBookShare, Long> {
	
	/**
	 * 分页查询用户分享-菜谱列表
	 * @param searchName 菜谱名称
	 * @param userName 用户名
	 * @param shareName 菜谱
	 * @param page 页数
	 * @param rows 每页显示的行数
	 * @return result
	 * @throws Exception
	 * @author huzi 2018-05-22
	 */
	public List<Map<String, Object>> getMenuBookShareList(
			@Param("userName")String userName,
			@Param("shareName")String shareName,
			@Param("orderby")String orderby,
			@Param("offset") int offset,
			@Param("pageSize") int pageSize);

	/**
	 * 每页显示条数
	 * @return
	 */
	public int getMenuBookShareCount(
			@Param("userName")String userName,
			@Param("shareName")String shareName,
			@Param("orderby")String orderby,
			@Param("offset") int offset,
			@Param("pageSize") int pageSize);
	

	
	/**
	 * 根据所分享菜谱名查询菜谱分享记录	huzi 2018-05-22
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public BeanMenuBookShare findMenuBookShareByName(String name);
	
	/**	
	 * 根据用户id查询菜谱分享记录		huzi 2018-05-22
	 * @param id	
	 * @return
	 */
	public BeanMenuBookShare findMenuBookShareByUserId(Long id);
	
	/**
	 * 根据用户id和菜谱名查询菜谱分享记录	huzi 2018-05-22
	 * @param condition
	 * @return
	 */
	public BeanMenuBookShare findByUserIdAndFileName(Map<String,Object> condition);

}