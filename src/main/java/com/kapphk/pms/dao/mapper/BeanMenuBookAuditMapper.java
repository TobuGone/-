package com.kapphk.pms.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.kapphk.pms.bean.BeanMenuBookAudit;


/**
 * 审核记录的数据操作接口
 * @author zoneyu 2016-04-26
*/
public interface BeanMenuBookAuditMapper extends BaseMapper<BeanMenuBookAudit, Long> {

	List<Map<String, Object>> getMenuBookAudit(@Param("uid")Long uid);
}