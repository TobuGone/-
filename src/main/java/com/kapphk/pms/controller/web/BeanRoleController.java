package com.kapphk.pms.controller.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.kapphk.pms.bean.BeanResource;
import com.kapphk.pms.bean.BeanRole;
import com.kapphk.pms.bean.BeanStandardResource;
import com.kapphk.pms.controller.BaseController;
import com.kapphk.pms.dao.mapper.BeanRoleMapper;
import com.kapphk.pms.service.web.BeanResourceService;
import com.kapphk.pms.service.web.BeanRoleService;
import com.kapphk.pms.util.ResourceFormat;
import com.kapphk.pms.util.Result;
import com.kapphk.pms.util.Result.MSG;

/**
 * 角色管理控制层
 * @author huangzh 2015-11-12
 */
@RestController
@RequestMapping("/role/")
public class BeanRoleController extends BaseController{
	@Autowired
	private BeanRoleService service;
	
	@Autowired
	private BeanRoleMapper roleMapper;
	
	@Autowired
	private BeanResourceService menuService;
	
	
	/**
	 * 查询角色下拉框数据列表
	 * @return
	 * @author huangzh 2015-11-12
	 */
	@RequestMapping("getComboxList.htm")
	public List<Map<String, Object>> getComboxList()throws Exception{
		
		List<Map<String, Object>> mapList = service.getComboxList();
		
		return mapList;
	}
	
	
	/**
	* @Title: loadResourceByUserId    
	* @Description: 查询角色对应得资源  
	* @param @param request
	* @param @param id
	* @param @return    设定文件    
	* @return JSONObject    返回类型    
	* @throws    
	*/
	@RequestMapping("/loadResourceByUserId.htm")
	@ResponseBody
	public JSONObject loadResourceByUserId(HttpServletRequest request,Long id) throws Exception{
		   JSONObject json = new JSONObject();
		   Long userId = null;
		    boolean flag = isSuperAdmin(request);
		    if(!flag){
		    	userId = (Long) request.getSession().getAttribute("userId");
		    }
	       //查询登录用户拥有的权限
			List<BeanResource> list = menuService.findUserResourcesByUserId(userId);
			ResourceFormat rf = new ResourceFormat();
			List<BeanStandardResource> resourceList = rf.format(list,"open");
		    //查询角色拥有的权限ids
		    List<Long> resourceIds =   service.findUserResourceIdsByRoleId(id);
			json.put("resourceIds",resourceIds);
			json.put("resources", resourceList);
		    return json;
	}
	
	
	/**   
	* @Title: insertRoleResource    
	* @Description: 添加角色资源关系
	* @param @param id
	* @param @param resourceIds
	* @param @return    设定文件    
	* @return JSONObject    返回类型    
	* @throws    
	*/
	@RequestMapping("/insertRoleResource.htm")
	@ResponseBody
	public JSONObject insertRoleResource(Long id ,Long [] resourceIds){
		JSONObject json = new JSONObject();
		if(id == null ){
			json.put("message", "角色不能为空");
			json.put("status", "error");
			return json;
		}
		try	{
			service.insertRoleResource(id, resourceIds);
			json.put("message", "保存成功");
			json.put("status", "success");

		} catch (Exception e) {
			json.put("message", "保存失败");
			json.put("status", "error");
			e.printStackTrace();
		}
	
	return json;
	}
	
	/**
	 * 查询系统角色列表
	 * @param roleName 角色名称
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-12-24
	 */
	@RequestMapping("getSystemRoleList.htm")
	public Result getSystemRoleList(String roleName,int page,int rows)throws Exception{
		Result rs = new Result();
		
		rs = service.getSystemRoleList(roleName,page,rows);
		
		return rs;
	}
	
	/**
	 * 新增或修改角色
	 * @param beanRole 对象
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-12-24
	 */
	@RequestMapping("saveRole.htm")
	public Result saveRole(BeanRole beanRole)throws Exception{
		Result rs = new Result();
		
		rs = service.saveRole(beanRole);
		
		return rs;
	}
	
	/**
	 * 查询单条数据
	 * @param id
	 * @return
	 * huangzh 2015-08-14
	 * @throws Exception
	 */
	@RequestMapping("data.htm")
	public Result data(Long id)throws Exception{
		Result rs = new Result();
		
		BeanRole data = null;
		try {
			data = roleMapper.findById(id);
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
	 * 改变角色状态
	 * @param ids
	 * @param status 状态
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-11-12
	 */
	@RequestMapping("changeStemRoleStatus.htm")
	public Result changeStemRoleStatus(String ids,String status)throws Exception{
		Result rs = new Result();
		
		rs = service.changeStemRoleStatus( ids, status);
		
		return rs;
	}
}
