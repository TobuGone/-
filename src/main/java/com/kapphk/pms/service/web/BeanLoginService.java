package com.kapphk.pms.service.web;

import com.kapphk.pms.bean.BeanSystemUser;

public interface BeanLoginService {

	/**
	 * 根据用户名密码查询用户对象
	 * @param userName 用户名
	 * @param password 密码
	 * @return
	 * @author huangzh 2016-01-06
	 */
	public BeanSystemUser findUserByUserNameAndPassword(String userName,
			String password)throws Exception;

}
