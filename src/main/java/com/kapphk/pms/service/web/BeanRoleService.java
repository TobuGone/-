package com.kapphk.pms.service.web;

import java.util.List;
import java.util.Map;

import com.kapphk.pms.bean.BeanRole;
import com.kapphk.pms.util.Result;

public interface BeanRoleService {

	public List<BeanRole> findRoleListByUserId(Map<String, Object> map);


	public List<Long> findUserResourceIdsByRoleId(Long id)throws Exception;

	public void insertRoleResource(Long id, Long[] resourceIds);

	public Result getSystemRoleList(String roleName, int page, int rows);

	public Result saveRole(BeanRole beanRole);

	public Result changeStemRoleStatus(String ids, String status);

	/**
	 * 查询角色下拉框数据列表
	 * @return
	 * @author huangzh 2015-11-12
	 */
	public List<Map<String, Object>> getComboxList();

}
