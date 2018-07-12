package com.kapphk.pms.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kapphk.pms.bean.BeanCity;

/**
 * 的数据操作接口
 * @author zoneyu 2016-01-28
*/
public interface BeanCityMapper extends BaseMapper<BeanCity, Long> {
	
	public List<BeanCity> findCityByCondition(@Param("param")Map<String,Object> condition);
}