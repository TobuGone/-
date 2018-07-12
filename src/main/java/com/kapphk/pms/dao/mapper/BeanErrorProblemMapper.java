package com.kapphk.pms.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kapphk.pms.bean.BeanErrorProblem;

/**
 * 主键的数据操作接口
 * @author zoneyu 2016-01-13
*/
public interface BeanErrorProblemMapper extends BaseMapper<BeanErrorProblem, Long> {
	
	public List<Map<String, Object>> findCondition(@Param("param")Map<String, Object> condition);

	public List<BeanErrorProblem> findByCondition(@Param("param")Map<String, Object> condition);
}