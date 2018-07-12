package com.kapphk.pms.service.web;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kapphk.pms.bean.BeanAppAndroidUpdate;
import com.kapphk.pms.service.BaseService;
import com.kapphk.pms.util.Result;

public interface BeanAppAndroidService extends BaseService<BeanAppAndroidUpdate, Long> {
	
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
	public Result getAppAndroidList(BeanAppAndroidUpdate appAndroidUpdate,
			String versionName, String startTime, String endTime, int page,
			int rows);

	/**
	 * 保存上传的版本文件
	 * @param equipmentUpdate
	 * @param file
	 * @return
	 */
	public Result saveAppAndroid(BeanAppAndroidUpdate appAndroidUpdate,
			MultipartFile file);
	
	
	/**
	 * 批量删除
	 * @param asList
	 * @return
	 */
	public Result delAppAndroid(List<Long> asList);
	
	
	/**
	 * 根据版本名称查询数据
	 */
	public BeanAppAndroidUpdate findByVersionName(String versionName);

	
	/**
	 * 查询最新版本应用
	 * @author huzi 2018年01月31日
	 */
	public BeanAppAndroidUpdate finMaxVersion();

}
