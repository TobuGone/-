package com.kapphk.pms.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kapphk.pms.bean.BeanPhoneCode;

/**
 * 的数据操作接口
 * @author zoneyu 2016-01-27
*/
public interface BeanPhoneCodeMapper extends BaseMapper<BeanPhoneCode, Long> {
	
	public List<BeanPhoneCode> findByCondition(@Param("param") Map<String, Object> condition);
}