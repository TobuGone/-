package com.kapphk.pms.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kapphk.pms.bean.BeanUserMenuBook;
import com.kapphk.pms.bean.PaginationCondition;

/**
 * 的数据操作接口
 * @author zoneyu 2016-01-26
*/
public interface BeanUserMenuBookMapper extends BaseMapper<BeanUserMenuBook, Long> {
	
    public List<Map<String,Object>> findByCondition(@Param("param")Map<String,Object> condition,@Param("pc")PaginationCondition pc);

	public void cancelTopMenuBookByUserId(Long userId);





}