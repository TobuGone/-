package com.kapphk.pms.controller.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.kapphk.pms.bean.BeanSystemLog;
import com.kapphk.pms.bean.BeanSystemUser;
import com.kapphk.pms.dao.mapper.BeanSystemLogMapper;
import com.kapphk.pms.service.web.BeanLoginService;
import com.kapphk.pms.util.DateUtils;
import com.kapphk.pms.util.ValueUtil;
import com.kapphk.pms.util.getIP;

/**
 * 登录管理控制层
 * @author huangzh 2015-11-05
 */

@RestController
@RequestMapping("/login/")
public class BeanLoginController {

	@Autowired
	private BeanLoginService service;
	
	@Autowired
	private BeanSystemLogMapper logMapper;
	
	/**
	 * 用户登录
	 * @param userName 用户账号
	 * @param password 用户密码
	 * @param request
	 * @return
	 */
	@RequestMapping("login.htm")
	@ResponseBody
    public JSONObject login(String userName,String password,HttpServletRequest request)throws Exception{
    	JSONObject object = new JSONObject();
    	if(!ValueUtil.isEmpty(userName) && !ValueUtil.isEmpty(password)){
    	
			 BeanSystemUser userVO = service.findUserByUserNameAndPassword(userName, password);
			 
     		 if(userVO != null && userVO.getId() != null){
     			 
     			 if(userVO.getStatus().equals("0")){
     				 object.put("rtCode", false) ;
      				 object.put("msg", "亲，您的账户已停用") ;
      				 return object;
     			 }
     			  request.getSession().setAttribute("user", userVO);
     			  request.getSession().setAttribute("userId", userVO.getId());
     			  object.put("rtCode", true) ;
  				  object.put("msg", "亲，登录成功") ;
  				 
  				 //记录日志
  				try {
  					BeanSystemLog systemLog = new BeanSystemLog();
  	  				systemLog.setUid(userVO.getId());
					systemLog.setIpAddress(getIP.getIpAddr(request));
					systemLog.setCreateTime(DateUtils.getLocalDate());
					systemLog.setOperationType("登录");
					systemLog.setEventName("系统登录");
					logMapper.insert(systemLog);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
  				 
    		 }else{
    			 object.put("rtCode", false) ;
 				 object.put("msg", "亲，用户名或密码错误") ;
    		 }
    			
    	}else{
    		object.put("rtCode", false);
    		object.put("msg", "亲，用户名或密码不能为空");
    		
    	}
    	return object;
      }
	
	/**
	 * 退出系统
	 * @param request
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-12-22
	 */
	@RequestMapping("loginout.htm")
	public void loginout(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		Long userId = (Long) request.getSession().getAttribute("userId");
		
		 //记录日志
		try {
			BeanSystemLog systemLog = new BeanSystemLog();
			systemLog.setUid(userId);
			systemLog.setIpAddress(getIP.getIpAddr(request));
			systemLog.setCreateTime(DateUtils.getLocalDate());
			systemLog.setOperationType("退出");
			systemLog.setEventName("退出系统，更改权限");
			logMapper.insert(systemLog);
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.getSession().invalidate();
		
		String jsp = request.getScheme()+"://"+request.getServerName() + ":" + request.getServerPort() + request.getContextPath()+"/login/login.jsp";
		
		response.sendRedirect(jsp); 
		
	}
	
}
