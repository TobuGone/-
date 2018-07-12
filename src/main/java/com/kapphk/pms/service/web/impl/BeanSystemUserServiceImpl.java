package com.kapphk.pms.service.web.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kapphk.pms.bean.BeanRole;
import com.kapphk.pms.bean.BeanSystemUser;
import com.kapphk.pms.bean.BeanUserRole;
import com.kapphk.pms.dao.mapper.BeanRoleMapper;
import com.kapphk.pms.dao.mapper.BeanSystemUserMapper;
import com.kapphk.pms.dao.mapper.BeanUserRoleMapper;
import com.kapphk.pms.service.BaseServiceImpl;
import com.kapphk.pms.service.web.BeanSystemUserService;
import com.kapphk.pms.util.Contents;
import com.kapphk.pms.util.DateUtils;
import com.kapphk.pms.util.MD5;
import com.kapphk.pms.util.Result;
import com.kapphk.pms.util.ValidateUtils;
@Service("beanSystemUserService")
public class BeanSystemUserServiceImpl extends BaseServiceImpl<BeanSystemUser, Long>implements BeanSystemUserService {

	@Autowired
	private BeanSystemUserMapper userMapper;
	
	@Autowired
	private BeanRoleMapper roleMapper;
	
	@Autowired
	private BeanUserRoleMapper userRoleMapper;
	
	@Override
	public void init() {
		super.setMapper(userMapper);
	}

	

	/**
	 * 修改密码 
	 * @param opwd
	 * @param npwd
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-11-19
	 */
	@Override
	public Result changePwd(String opwd, String npwd, HttpServletRequest request)
			throws Exception {
		Result rs = new Result();
		
		Long userId = (Long) request.getSession().getAttribute("userId");
		
		BeanSystemUser beanUser = userMapper.findById(userId);
		
		if(!MD5.getMD5(opwd).equals(beanUser.getPwd())){
			rs.setStatus(Contents.ERROR);
			rs.setMsg("原密码错误，请重新输入");
			return rs;
		}
		
		beanUser.setPwd(MD5.getMD5(npwd));
		
		userMapper.update(beanUser);
		
		return rs;
	}
	
	
	/**
	 * 查询后台系统用户列表
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-08
	 */
	@Override
	public Result getSystemUserList(int page, int rows) throws Exception {
		Result rs = new Result();
		
		BeanSystemUser systemUser = new BeanSystemUser();
		
		List<BeanSystemUser> list = userMapper.findByPage(systemUser, (page-1)*rows, rows);
		int count = userMapper.findByPageCount(systemUser);
		
		rs.put("total", count);
		rs.put("rows",list);
		
		return rs;
	}

	/**
	 * 新增或修改系统用户
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-08
	 */
	@Override
	public Result saveSystemUser(BeanSystemUser systemUser,Long roleId) throws Exception {
		Result rs = new Result();
		
		BeanRole beanRole = roleMapper.findById(roleId);
		
		//新增
		if(ValidateUtils.isBlank(systemUser.getId())){
			BeanSystemUser user = new BeanSystemUser();
			user.setUserName(systemUser.getUserName());
			
			int count = userMapper.findByPageCount(user);
			
			if(count != 0){
				rs.setStatus(Contents.EXIST);
				rs.setMsg("用户账号已重复，请重新输入");
				return rs;
			}
			
			systemUser.setName(beanRole.getRoleName());
			systemUser.setPwd(MD5.getMD5(systemUser.getPwd()));
			systemUser.setCreateTime(DateUtils.getLocalDate());
			
			userMapper.insert(systemUser);
			
			
			BeanUserRole beanUserRole = new BeanUserRole();
			
			beanUserRole.setRoleId(roleId);
			beanUserRole.setUserId(systemUser.getId());
			
			userRoleMapper.insert(beanUserRole);
		}
		//修改
		else{
			BeanSystemUser user = new BeanSystemUser();
			user.setUserName(systemUser.getUserName());
			
			List<BeanSystemUser> list = userMapper.findByPage(user, 0, 1);
			
			if(!ValidateUtils.isEmptyForCollection(list)){
				BeanSystemUser beanSystemUser = list.get(0);
				
				if(systemUser.getId().equals(beanSystemUser.getId())){
					
					systemUser.setName(beanRole.getRoleName());
					systemUser.setCreateTime(DateUtils.getLocalDate());
					userMapper.update(systemUser);
					
					userRoleMapper.updateByUId(systemUser.getId(),roleId);
					
				}else{
					rs.setStatus(Contents.EXIST);
					rs.setMsg("用户账号已重复，请重新输入");
					return rs;
				}
				
			}else{
				systemUser.setName(beanRole.getRoleName());
				systemUser.setCreateTime(DateUtils.getLocalDate());
				userMapper.update(systemUser);
				
				userRoleMapper.updateByUId(systemUser.getId(),roleId);
			}
		}
		
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
	public Map<String, Object> findByUId(Long id) throws Exception {
		
		Map<String, Object> map = userMapper.findByUId(id);
		
		return map;
	}

	/**
	 * 删除系统用户
	 * @param ids id集合
	 * @return
	 * @author huangzh 2015-11-11
	 */
	@Override
	public Result delSystemUser(String ids) throws Exception {
		Result rs = new Result();
		
		if(!ValidateUtils.isBlank(ids)){
			List<Long> idList = new ArrayList<Long>();
			
			String [] strids = ids.split(",");
			
			for(String id:strids){
				idList.add(Long.parseLong(id));
				
				userRoleMapper.deltetByUserId(Long.parseLong(id));
			}
			
			userMapper.deletes(idList);
		}
		
		return rs;
	}
	
	/**
	 * 重置密码
	 * @param oldPwd 原密码
	 * @param newPwd 新密码
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-11-12
	 */
	@Override
	public Result updatePwd(Long id,String oldPwd, String newPwd) throws Exception {
		Result rs = new Result();
		
		BeanSystemUser beanUser = userMapper.findById(id);
		
		if(!MD5.getMD5(oldPwd).equals(beanUser.getPwd())){
			rs.setStatus(Contents.TOKEN_FAILURE);
			rs.setMsg("原密码错误，请重新输入");
			return rs;
		}
		
		beanUser.setPwd(MD5.getMD5(newPwd));
		
		userMapper.update(beanUser);
		
		return rs;
	}
}
