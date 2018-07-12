package com.kapphk.pms.service.web;

import java.util.List;
import java.util.Map;

import com.kapphk.pms.bean.BeanMenuBookShare;
import com.kapphk.pms.service.BaseService;
import com.kapphk.pms.util.Result;

public interface BeanMenuBookShareService extends BaseService<BeanMenuBookShare, Long>{
	
	/**
	 * 分页查询用户分享-菜谱列表
	 * @param searchName 菜谱名称
	 * @param page 页数
	 * @param rows 每页显示的行数
	 * @return result
	 * @throws Exception
	 * @author huzi 2018-05-22
	 */
	public Result getMenuBookShareList(String userName,String shareName,String orderby,int page, int rows);

	/**
	 * 根据所分享菜谱名查询菜谱分享记录	huzi 2018-05-22
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public BeanMenuBookShare findMenuBookShareByName(String name);
	
	/**	
	 * 根据用户id查询分享菜谱分享记录		huzi 2018-05-22
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
	
	/**
	 * 批量删除
	 * @param asList
	 * @return
	 */
	public Result delMenuBookShare(List<Long> ids);
}
