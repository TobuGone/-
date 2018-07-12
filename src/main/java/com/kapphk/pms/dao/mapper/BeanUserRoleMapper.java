package com.kapphk.pms.dao.mapper;

import org.apache.ibatis.annotations.Param;

import com.kapphk.pms.bean.BeanUserRole;

/**
 * 的数据操作接口
 * @author zoneyu 2016-01-06
*/
public interface BeanUserRoleMapper extends BaseMapper<BeanUserRole, Long> {

	public void updateByUId(@Param(value="userId")Long userId, @Param(value="roleId")Long roleId);

	public void deltetByUserId(Long userId);
}