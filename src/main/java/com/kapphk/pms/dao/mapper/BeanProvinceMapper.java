package com.kapphk.pms.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kapphk.pms.bean.BeanProvince;

/**
 * 主键ID的数据操作接口
 * @author zoneyu 2016-01-28
*/
public interface BeanProvinceMapper extends BaseMapper<BeanProvince, Long> {
	
	
	public List<BeanProvince> findProvinceByCondition(@Param("pram")Map<String,Object> condition);
}