package com.kapphk.pms.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kapphk.pms.bean.BeanMessagePush;
import com.kapphk.pms.bean.PaginationCondition;

/**
 * 主键的数据操作接口
 * @author zoneyu 2016-01-13
*/
public interface BeanMessagePushMapper extends BaseMapper<BeanMessagePush, Long> {
	
	public List<Map<String, Object>> findPagination(@Param("param") Map<String,Object> condition,@Param("pc") PaginationCondition pc);
}