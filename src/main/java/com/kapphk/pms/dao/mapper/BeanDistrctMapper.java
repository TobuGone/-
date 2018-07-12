package com.kapphk.pms.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kapphk.pms.bean.BeanDistrct;

/**
 * 主键ID的数据操作接口
 * @author zoneyu 2016-01-28
*/
public interface BeanDistrctMapper extends BaseMapper<BeanDistrct, Long> {
	
	
	public List<BeanDistrct> findDistrctByCondition(@Param("param")Map<String,Object> condition);
}