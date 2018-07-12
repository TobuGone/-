package com.kapphk.pms.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kapphk.pms.bean.BeanResource;

/**
 * 的数据操作接口
 * @author zoneyu 2016-01-06
*/
public interface BeanResourceMapper extends BaseMapper<BeanResource, Integer> {

	public List<Map<String, Object>> findUserResourceIdsByUserId(@Param(value="userId")Long userId);

	public List<BeanResource> findUserResourcesByUserId(@Param(value="userId")Long userId);
}