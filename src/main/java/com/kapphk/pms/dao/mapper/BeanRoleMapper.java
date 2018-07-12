package com.kapphk.pms.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kapphk.pms.bean.BeanRole;
import com.kapphk.pms.bean.BeanRoleResource;

/**
 * 的数据操作接口
 * @author zoneyu 2016-01-06
*/
public interface BeanRoleMapper extends BaseMapper<BeanRole, Long> {

	List<Map<String, Object>> findUserResourceIdsByRoleId(@Param(value="roleId") Long roleId);

	List<BeanRole> findRoleListByUserId(@Param(value="param") Map<String, Object> condition);

	List<Map<String, Object>> findRoleIdsByUserId(@Param(value="userId") Long userId);


	void deleteRoleResourceByRoleIds(@Param(value="roleIds")Long[] ids);

	void insertRoleResources(List<BeanRoleResource> list);

	List<Map<String, Object>> findSystemRoleList(@Param(value="roleName")String roleName, @Param(value="page")Integer page,
			@Param(value="rows")Integer rows);

	int findSystemRoleCount(@Param(value="roleName")String roleName);

	List<Map<String, Object>> findComboxList();
}