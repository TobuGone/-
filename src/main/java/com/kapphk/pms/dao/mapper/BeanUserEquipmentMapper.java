package com.kapphk.pms.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kapphk.pms.bean.BeanUserEquipment;

/**
 * 的数据操作接口
 * @author zoneyu 2016-01-25
*/
public interface BeanUserEquipmentMapper extends BaseMapper<BeanUserEquipment, Long> {
	
	public List<Map<String, Object>> findByCondition(@Param("param") Map<String,Object> condition);
	
	/**
	 * 根据设备id查询
	 * @author  huzi
	 * @date    2018年6月26日	
	 * @param 	eid
	 * @return
	 */
	public BeanUserEquipment findByEid(Long eid);
}