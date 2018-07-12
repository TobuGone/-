package com.kapphk.pms.service.web.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kapphk.pms.bean.BeanAppIOSUpdate;
import com.kapphk.pms.dao.mapper.BeanAppIOSUpdateMapper;
import com.kapphk.pms.service.BaseServiceImpl;
import com.kapphk.pms.service.web.BeanAppIOSService;
import com.kapphk.pms.util.Contents;
import com.kapphk.pms.util.Result;
import com.kapphk.pms.util.ValidateUtils;
@Service("beanAppIOSUpdateService")
public class BeanAppIOSServiceImpl extends BaseServiceImpl<BeanAppIOSUpdate,Long> implements BeanAppIOSService {

	@Autowired
	private BeanAppIOSUpdateMapper appIOSUpdateMapper;
	
	@Override
	public void init() {
		super.setMapper(appIOSUpdateMapper);
	}
	
	/**
	 * 查询更新版本列表
	 * @param appAndroidUpdate
	 * @param versionName 版本名称
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param page 
	 * @param rows
	 * @return
	 */
	@Override
	public Result getAppIOSList(BeanAppIOSUpdate appIOSUpdate,
			String versionName, String startTime, String endTime, int page,int rows) {
		
		Result rs = new Result();	
		
		List<Map<String,Object>> list = appIOSUpdateMapper.getAppIOSList(appIOSUpdate, versionName, startTime, endTime, (page-1)*rows, rows);
		int count = appIOSUpdateMapper.getAppIOSCount(appIOSUpdate, versionName, startTime, endTime, (page-1)*rows, rows);
		rs.put("total", count);
		rs.put("rows", list);
		
		return rs;
	}
	
	
	/**
	 * 批量删除
	 * @param asList
	 * @return
	 */
	@Override
	public Result delAppIOS(List<Long> asList) {
		Result rs = new Result();
		
		if(!ValidateUtils.isEmptyForCollection(asList)){
			int count = appIOSUpdateMapper.deletes(asList);
			rs.put("count", count);
		}else{
			rs.setStatus(Contents.ERROR);
			rs.setMsg("参数有误");
			return rs;
		}
		
		return rs;
	}

	
	/**
	 * 根据版本名称查询数据
	 */
	@Override
	public BeanAppIOSUpdate findByVersionName(String versionName) {
		return appIOSUpdateMapper.findByVersionName(versionName);
		
	}

	
	/**
	 * 查询最新版本应用
	 */
	@Override
	public BeanAppIOSUpdate finMaxVersion() {
		return appIOSUpdateMapper.finMaxVersion();
	}

	/**
	 * 更新上传的版本信息
	 * @param equipmentUpdate
	 * @param file
	 * @return
	 */
	@Override
	public int upAppIOS(BeanAppIOSUpdate appIOSUpdate){
	
		return appIOSUpdateMapper.update(appIOSUpdate);
		
	}

	
	
}
