package com.kapphk.pms.service.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kapphk.pms.bean.BeanSystemUser;
import com.kapphk.pms.service.BaseService;
import com.kapphk.pms.util.Result;

public interface BeanSystemUserService extends BaseService<BeanSystemUser, Long>{
	
	/**
	 * 修改密码 
	 * @param opwd
	 * @param npwd
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-11-19
	 */
	public Result changePwd(String opwd, String npwd, HttpServletRequest request)throws Exception;
	
	/**
	 * 查询后台系统用户列表
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-08
	 */
	public Result getSystemUserList(int page, int rows)throws Exception;

	/**
	 * 新增或修改系统用户
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-08
	 */
	public Result saveSystemUser(BeanSystemUser systemUser,Long roleId)throws Exception;

	/**
	 * 查询单条数据
	 * @param id
	 * @return
	 * huangzh 2015-11-12
	 * @throws Exception
	 */
	public Map<String, Object> findByUId(Long id)throws Exception;

	/**
	 * 删除系统用户
	 * @param ids id集合
	 * @return
	 * @author huangzh 2015-11-11
	 */
	public Result delSystemUser(String ids)throws Exception;

	/**
	 * 重置密码
	 * @param oldPwd 原密码
	 * @param newPwd 新密码
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-11-12
	 */
	public Result updatePwd(Long id, String oldPwd, String newPwd)throws Exception;

}
