package com.kapphk.pms.service.web.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kapphk.pms.bean.BeanMenuBookShare;
import com.kapphk.pms.dao.mapper.BeanMenuBookShareMapper;
import com.kapphk.pms.dao.mapper.BeanMenubookShareProcedureMapper;
import com.kapphk.pms.service.BaseServiceImpl;
import com.kapphk.pms.service.web.BeanMenuBookShareService;
import com.kapphk.pms.util.Contents;
import com.kapphk.pms.util.Result;
import com.kapphk.pms.util.ValidateUtils;
@Service("beanMenuBookShareService")
public class BeanMenuBookShareServiceImpl extends BaseServiceImpl<BeanMenuBookShare, Long>implements BeanMenuBookShareService {

	@Autowired
	private BeanMenuBookShareMapper menuBookShareMapper;
	
	@Autowired
	private BeanMenubookShareProcedureMapper menuBookShareProcedureMapper;
	
	@Override
	public void init() {
		super.setMapper(menuBookShareMapper);
	}
	
	
	/**
	 * 分页查询用户分享-菜谱列表
	 * 2018-05-22  huzi
	 */
	@Override
	public Result getMenuBookShareList(String userName, String shareName, String orderby, int page, int rows) {
		Result rs = new Result();
		
		List<Map<String,Object>> list = menuBookShareMapper.getMenuBookShareList(userName, shareName, orderby, (page-1)*rows, rows);
		int count = menuBookShareMapper.getMenuBookShareCount(userName, shareName, orderby, (page-1)*rows, rows);
		rs.put("total", count);
		rs.put("rows", list);
		
		return rs;
	}


	/**
	 * 根据所分享菜谱名查询菜谱分享记录
	 * 2018-05-22  huzi
	 */
	@Override
	public BeanMenuBookShare findMenuBookShareByName(String name) {
		return menuBookShareMapper.findMenuBookShareByName(name);
	}

	/**
	 * 根据用户id查询菜谱分享记录
	 * 2018-05-22  huzi
	 */
	@Override
	public BeanMenuBookShare findMenuBookShareByUserId(Long id) {
		return menuBookShareMapper.findMenuBookShareByUserId(id);
	}

	/**
	 * 根据用户id和菜谱名查询菜谱分享记录
	 * 2018-05-22  huzi
	 */
	@Override
	public BeanMenuBookShare findByUserIdAndFileName(Map<String, Object> condition) {
		return menuBookShareMapper.findByUserIdAndFileName(condition);
	}


	/**
	 * 批量删除
	 * 2018-05-22  huzi
	 */
	@Override
	public Result delMenuBookShare(List<Long> ids) {
		Result rs = new Result();
		if(!ValidateUtils.isEmptyForCollection(ids)){
			int count = menuBookShareMapper.deletes(ids);
			for (Long id : ids) {
				menuBookShareProcedureMapper.deleteByMenuId(id);
			}
			rs.put("count", count);
		}else{
			rs.setStatus(Contents.ERROR);
			rs.setMsg("参数有误");
			return rs;
		}
		
		return rs;
	}


	
	
}
