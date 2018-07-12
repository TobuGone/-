package com.kapphk.pms.dao.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kapphk.pms.bean.BeanSystemUser;

/**
 * 主键的数据操作接口
 * @author zoneyu 2016-01-06
*/
public interface BeanSystemUserMapper extends BaseMapper<BeanSystemUser, Long> {

	/**
	 * 根据用户名密码查询用户对象
	 * @param userName 用户名
	 * @param password 密码
	 * @return
	 * @author huangzh 2016-01-06
	 */
	public BeanSystemUser findUserByUserNameAndPassword(@Param(value="userName")String userName,
			@Param(value="password")String password);

	public Map<String, Object> findByUId(Long userId);
}