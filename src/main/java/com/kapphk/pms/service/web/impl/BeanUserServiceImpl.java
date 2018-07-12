package com.kapphk.pms.service.web.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kapphk.pms.bean.BeanUser;
import com.kapphk.pms.dao.mapper.BeanUserMapper;
import com.kapphk.pms.service.BaseServiceImpl;
import com.kapphk.pms.service.web.BeanUserService;
import com.kapphk.pms.util.Contents;
import com.kapphk.pms.util.Result;
import com.kapphk.pms.util.ValidateUtils;
@Service("beanUserService")
public class BeanUserServiceImpl extends BaseServiceImpl<BeanUser, Long>implements BeanUserService {
	
	@Autowired
	private BeanUserMapper beanUserMapper;
	
	@Override
	public void init() {
		super.setMapper(beanUserMapper);
	}

	/**
	 * 查询手机用户数据列表
	 * @param page 页数
	 * @param rows 每页显示的行数
	 * @return
	 * @throws Exception
	 * @author huzi 2018-06-21
	 */
	@Override
	public Result getUserInfoList(String searchUserName,String searchNickName,String searchNumber,int page, int rows) throws Exception {
		Result rs = new Result();
		
		List<Map<String, Object>> list = beanUserMapper.findAllPage(searchUserName,searchNickName,searchNumber,(page-1)*rows,rows);
		
		int count = beanUserMapper.findAllPageCount(searchUserName,searchNickName,searchNumber);
		
		rs.put("total", count);
		rs.put("rows", list);
		
		return rs;
	}

	/**
	 * 查询单条数据
	 * @param id
	 * @return
	 * huangzh 2015-11-12
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> findMapById(Long id) throws Exception {
		
		return beanUserMapper.findMapById(id);
	}

	/**
	 * 批量删除
	 * @param ids id集合
	 * @return
	 * @throws Exception
	 * @author huzi 2018-04-23
	 */
	@Override
	public Result delUsers(List<Long> ids) throws Exception {
		Result rs = new Result();
		
		if(!ValidateUtils.isBlank(ids)){
			
			int count = beanUserMapper.deletes(ids);
			rs.put("count", count);
			return rs;
		}
		
		rs.setStatus(Contents.PARAMS_ERROR);
		rs.setMsg("参数错误!");
		return rs;
	}



	/**
	 * 禁用或使用用户
	 * status 数据状态 1:正常 2:已失效
	 * @param ids id集合
	 * @return	
	 * @author huzi 2018-04-23
	 */
	@Override
	public int upUserStatus(List<Long> ids, String type) {
		return beanUserMapper.upUserStatus(ids, type);
	}
}

