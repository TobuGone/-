package com.kapphk.pms.service.web;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.kapphk.pms.bean.BeanEquipmentUpdate;
import com.kapphk.pms.service.BaseService;
import com.kapphk.pms.util.Result;

public interface BeanEquipmentService extends BaseService<BeanEquipmentUpdate, Long> {
	
	/**
	 * 查询更新版本列表
	 * @param equipmentUpdate
	 * @param versionCode 版本号
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param page 
	 * @param rows
	 * @return
	 */
	public Result getEquipmentList(BeanEquipmentUpdate equipmentUpdate,
			String versionCode, String startTime, String endTime, int page,
			int rows);

	/**
	 * 保存上传的版本文件
	 * @param equipmentUpdate
	 * @param file
	 * @return
	 */
	public Result saveEquipment(BeanEquipmentUpdate equipmentUpdate,
			MultipartFile file);

	/**
	 * 批量删除
	 * @param asList
	 * @return
	 */
	public Result delEquipment(List<Long> asList);
	
	
	/**
	 * 根据硬件型号、软件型号、版本号 查询数据
	 * @author huzi 2018年05月15日
	 */
	public BeanEquipmentUpdate findByHardwareAndSoftwar(Map<String,Object> condition);
	
	
	/**
	 * 查询最新版本应用
	 * @author huzi 2018年05月15日
	 */
	public BeanEquipmentUpdate finMaxVersionCode();
	
	

}
