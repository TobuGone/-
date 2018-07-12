package com.kapphk.pms.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kapphk.pms.bean.BeanEquipmentUpdate;

/**
 * 设备更新ID的数据操作接口
 * @author huzi 2018-5-15
*/
public interface BeanEquipmentUpdateMapper extends BaseMapper<BeanEquipmentUpdate, Long> {
	
	/**
	 * 分页查找数据
	 * 
	 * @Param("param")Map<String,Object> condition 
	 * 只有用到需要指定名称的字段的时候   才用到@Param 这个注解
	 */
	public List<Map<String,Object>> getEquipmentList(@Param(value = "param") BeanEquipmentUpdate equipmentUpdate,
			@Param(value = "versionCode")String versionCode, 
			@Param(value = "startTime")String startTime, 
			@Param(value = "endTime")String endTime,
			@Param(value = "offset") int offset,
			@Param(value = "pageSize") int pageSize);
	
	/**
	 * 分页查找总条数
	 * 
	 */
	public int getEquipmentCount(@Param(value = "param") BeanEquipmentUpdate equipmentUpdate,
			@Param(value = "versionCode")String versionCode, 
			@Param(value = "startTime")String startTime, 
			@Param(value = "endTime")String endTime,
			@Param(value = "offset") int offset,
			@Param(value = "pageSize") int pageSize);

	public List<Map<String, Object>> getEquipmentUpdate();
	
	
//	/**
//	 * 根据硬件版本查询数据
//	 */
//	public List<BeanEquipmentUpdate> findByHardwareVersion(String hardwareVersion);
//	
//	
//	/**
//	 * 根据软件版本查询数据
//	 */
//	public BeanEquipmentUpdate findBySoftwareVersion(String softwareVersion);
	
	/**
	 * 根据硬件型号、软件型号查询数据
	 * @author huzi 2018年05月15日
	 */
	public BeanEquipmentUpdate findByHardwareAndSoftwar(Map<String,Object> condition);
	
	
	/**
	 * 查询最新版本应用
	 * @author huzi 2018年05月15日
	 */
	public BeanEquipmentUpdate finMaxVersionCode();
	
	
	/**
	 * 根据硬件型号、软件型号、版本号 查询数据
	 * @author huzi 2018年05月15日
	 */
	public BeanEquipmentUpdate findByHsv(Map<String,Object> condition);
	
	
	
}