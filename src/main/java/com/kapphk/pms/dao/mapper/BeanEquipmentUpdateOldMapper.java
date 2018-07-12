package com.kapphk.pms.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kapphk.pms.bean.BeanEquipmentUpdateOld;

/**
 * 设备更新ID的数据操作接口
 * @author huzi 2018-06-05
*/
public interface BeanEquipmentUpdateOldMapper extends BaseMapper<BeanEquipmentUpdateOld, Long> {
	
	/**
	 * 分页查找数据
	 * 
	 */
	public List<Map<String,Object>> getEquipmentList(@Param(value = "param") BeanEquipmentUpdateOld equipmentUpdateOld,
			@Param(value = "versionsName")String versionsName, 
			@Param(value = "startTime")String startTime, 
			@Param(value = "endTime")String endTime,
			@Param(value = "offset") int offset,
			@Param(value = "pageSize") int pageSize);
	
	/**
	 * 分页查找总条数
	 * 
	 */
	public int getEquipmentCount(@Param(value = "param") BeanEquipmentUpdateOld equipmentUpdateOld,
			@Param(value = "versionsName")String versionsName, 
			@Param(value = "startTime")String startTime, 
			@Param(value = "endTime")String endTime,
			@Param(value = "offset") int offset,
			@Param(value = "pageSize") int pageSize);

	public List<Map<String, Object>> getEquipmentUpdate();
}
