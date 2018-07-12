package com.kapphk.pms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.kapphk.pms.bean.BeanRole;
import com.kapphk.pms.bean.BeanSystemUser;
import com.kapphk.pms.service.web.BeanRoleService;



/**
 * 抽象BaseController，用于继承
 * @author Folo 2014-10-13
 */
public class BaseController{
	@Autowired
	private BeanRoleService roleService;
	
	/**
	 * 判断当前用户是否为管理员
	 * @param request
	 */
	public boolean isSuperAdmin(HttpServletRequest request){
		
		BeanSystemUser beanUser = (BeanSystemUser) request.getSession().getAttribute("user");
		Long userId = beanUser.getId();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
	    List<BeanRole> roleList = 	roleService.findRoleListByUserId(map);
	      if(!org.springframework.util.CollectionUtils.isEmpty(roleList)){
	    	  for(BeanRole role:roleList){
	    		  if(role!= null && "ROLE_SUPER_ADMIN".equals(role.getRoleValue())){
	    			  return true;
	    		  }
	    	  }
	      }
	      return false;
	}
}
