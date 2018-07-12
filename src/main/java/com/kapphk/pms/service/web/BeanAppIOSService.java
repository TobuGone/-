package com.kapphk.pms.service.web;

import java.util.List;

import com.kapphk.pms.bean.BeanAppIOSUpdate;
import com.kapphk.pms.service.BaseService;
import com.kapphk.pms.util.Result;
/**
 * @author 胡子
 * since 2018年4月24日
 */
public interface BeanAppIOSService extends BaseService<BeanAppIOSUpdate, Long> {
	
	/**
	 * 查询更新版本列表
	 * @param equipmentUpdate
	 * @param versionName 版本名称
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param page 
	 * @param rows
	 * @return
	 */
	public Result getAppIOSList(BeanAppIOSUpdate appIOSUpdate,
			String versionName, String startTime, String endTime, int page,
			int rows);

	
	
	/**
	 * 批量删除
	 * @param asList
	 * @return
	 */
	public Result delAppIOS(List<Long> asList);
	
	
	/**
	 * 根据版本名称查询数据
	 */
	public BeanAppIOSUpdate findByVersionName(String versionName);

	
	/**
	 * 查询最新版本应用
	 * 
	 */
	public BeanAppIOSUpdate finMaxVersion();

}
