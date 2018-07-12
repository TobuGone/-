package com.kapphk.pms.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kapphk.pms.bean.BeanEquipment;

/**
 * 主键的数据操作接口
 * @author zoneyu 2016-01-19
*/
public interface BeanEquipmentMapper extends BaseMapper<BeanEquipment, Long> {
	
	public List<Map<String, Object>> findByCondition(@Param("param") Map<String,Object> condition);
	
	/**
	 * 根据硬用户id、设备名 查询数据
	 * @author huzi 2018年06月26日
	 */
	public BeanEquipment findByUidAndNumber(Map<String,Object> condition);
}