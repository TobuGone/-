package com.kapphk.pms.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kapphk.pms.bean.BeanMenubookShareProcedure;

/**
 * 分享菜谱的步骤的数据操作接口
 * @author huzi 2018-05-22
*/
public interface BeanMenubookShareProcedureMapper extends BaseMapper<BeanMenubookShareProcedure, Long> {

	public void deleteByMenuId(Long menuid);
	
	
	public List<BeanMenubookShareProcedure> findByCondition(@Param("param")Map<String, Object> condition);
}