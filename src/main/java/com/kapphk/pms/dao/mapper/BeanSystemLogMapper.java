package com.kapphk.pms.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kapphk.pms.bean.BeanSystemLog;

/**
 * 的数据操作接口
 * @author zoneyu 2016-01-06
*/
public interface BeanSystemLogMapper extends BaseMapper<BeanSystemLog, Long> {
	public int findMapAllCount();

	public List<Map<String, Object>> findMapAll(@Param(value="page")Integer page,@Param(value="rows")Integer rows);
}