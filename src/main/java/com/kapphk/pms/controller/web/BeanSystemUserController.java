package com.kapphk.pms.controller.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kapphk.pms.util.Result.MSG;
import com.kapphk.pms.bean.BeanSystemUser;
import com.kapphk.pms.service.web.BeanSystemUserService;
import com.kapphk.pms.util.Result;

/**
 * 后台系统用户控制层
 * @author huangzh 2016-01-08
 */
@RestController
@RequestMapping("/systemuser/")
public class BeanSystemUserController {

	@Autowired
	private BeanSystemUserService service;
	

	/**
	 * 重置密码
	 * @param oldPwd 原密码
	 * @param newPwd 新密码
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-11-12
	 */
	@RequestMapping("updatePwd.htm")
	public Result updatePwd(Long id,String oldPwd,String newPwd)throws Exception{
		Result rs = new Result();
		
		rs = service.updatePwd(id,oldPwd,newPwd);
		
		return rs;
	}
	
	
	/**
	 * 修改密码 
	 * @param opwd
	 * @param npwd
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-11-19
	 */
	@RequestMapping("changePwd.htm")
	public Result changePwd(String opwd,String npwd,HttpServletRequest request)throws Exception{
		Result rs = new Result();
		
		rs =  service.changePwd( opwd, npwd, request);
		
		return rs;
	}
	
	
	/**
	 * 查询后台系统用户列表
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-08
	 */
	@RequestMapping("getSystemUserList.htm")
	public Result getSystemUserList(int page,int rows)throws Exception{
		Result rs = new Result();
		
		rs = service.getSystemUserList(page,rows);
		
		return rs;
	}
	
	/**
	 * 新增或修改系统用户
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-08
	 */
	@RequestMapping("saveSystemUser.htm")
	public Result saveSystemUser(BeanSystemUser systemUser,Long roleId)throws Exception{
		Result rs = new Result();
		
		rs = service.saveSystemUser(systemUser,roleId);
		
		return rs;
	}
	
	
	/**
	 * 查询单条数据
	 * @param id
	 * @return
	 * huangzh 2015-11-12
	 * @throws Exception
	 */
	@RequestMapping("data.htm")
	public Result data(Long id)throws Exception{
		Result rs = new Result();
		
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			
			data = service.findByUId(id);
			
		} catch (Exception e) { 
			rs.setError(MSG.ERROR);
			rs.setMsg("请求失败");
			e.printStackTrace();
		}
		
		if(null == data) rs.setError(MSG.NOT_FOUND, "查询失败", "信息");
		else rs.put("data", data);
		return rs;
	}
	
	
	/**
	 * 删除系统用户
	 * @param ids id集合
	 * @return
	 * @author huangzh 2015-11-11
	 */
	@RequestMapping("delSystemUser.htm")
	public Result delSystemUser(String ids)throws Exception{
		Result rs = new Result();
		
		rs = service.delSystemUser(ids);
		
		return rs;
	}
	
}
