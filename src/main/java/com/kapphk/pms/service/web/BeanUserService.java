	package com.kapphk.pms.service.web;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kapphk.pms.bean.BeanUser;
import com.kapphk.pms.service.BaseService;
import com.kapphk.pms.util.Result;

public interface BeanUserService extends BaseService<BeanUser, Long>{

	/**
	 * 查询手机用户数据列表
	 * @param page 页数
	 * @param rows 每页显示的行数
	 * @return
	 * @throws Exception
	 * @author huzi 2018-06-21
	 */
	public Result getUserInfoList(String searchUserName,String searchNickName,String searchNumber, int page, int rows)throws Exception;

	/**
	 * 查询单条数据
	 * @param id
	 * @return
	 * huangzh 2015-11-12
	 * @throws Exception
	 */
	public Map<String, Object> findMapById(Long id)throws Exception;
	
	/**
	 * 批量删除
	 * @param ids id集合
	 * @return
	 * @throws Exception
	 * @author huzi 2018-04-23
	 */
	public Result delUsers(List<Long> ids)throws Exception;

	
	/**
	 * 禁用或者使用用户
	 * @param ids id
	 * @param type 状态
	 * @param rank 级别
	 * @return
	 * @author huzi 2018-04-23
	 */
	public int upUserStatus(@Param("ids")List<Long> ids,String type);

}
