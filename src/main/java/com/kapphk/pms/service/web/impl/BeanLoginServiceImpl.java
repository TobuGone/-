package com.kapphk.pms.service.web.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kapphk.pms.bean.BeanSystemUser;
import com.kapphk.pms.dao.mapper.BeanSystemUserMapper;
import com.kapphk.pms.service.web.BeanLoginService;
@Service("beanLoginService")
public class BeanLoginServiceImpl implements BeanLoginService {

	@Autowired
	private BeanSystemUserMapper userMapper;
	
	/**
	 * 根据用户名密码查询用户对象
	 * @param userName 用户名
	 * @param password 密码
	 * @return
	 * @author huangzh 2016-01-06
	 */
	@Override
	public BeanSystemUser findUserByUserNameAndPassword(String userName,
			String password) throws Exception {
		return userMapper.findUserByUserNameAndPassword(userName,password);
	}

}
