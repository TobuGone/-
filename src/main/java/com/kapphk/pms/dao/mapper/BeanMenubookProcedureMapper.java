package com.kapphk.pms.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kapphk.pms.bean.BeanMenubookProcedure;

/**
 * 步骤的数据操作接口
 * @author zoneyu 2016-01-22
*/
public interface BeanMenubookProcedureMapper extends BaseMapper<BeanMenubookProcedure, Long> {

	public void deleteByMenuId(Long menuid);
	
	
	public List<BeanMenubookProcedure> findByCondition(@Param("param")Map<String, Object> condition);
}