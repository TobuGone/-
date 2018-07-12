package com.kapphk.pms.controller.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kapphk.pms.bean.BeanResource;
import com.kapphk.pms.bean.BeanStandardResource;
import com.kapphk.pms.controller.BaseController;
import com.kapphk.pms.service.web.BeanResourceService;
import com.kapphk.pms.util.ResourceFormat;
import com.kapphk.pms.util.ValueUtil;
 
 


/**   
* @ClassName: BeanResourceController    
* @Description: 系统资源  
* @author XS  
* @date 2015-7-14 下午2:19:51    
*         
*/
@Controller
@RequestMapping("resource")
public class BeanResourceController  extends BaseController{
	
	@Autowired
	private BeanResourceService resourceService;
	
	
	

	/**   
	* @Title: findResourceTree    
	* @Description: 查询用户拥有的所有菜单 
	* @param @param status  子节点是否展开  'open'：展开  ；'closed'：关闭
	* @param @param request
	* @param @return    设定文件    
	* @return List<JSONObject>    返回类型    
	* @throws    
	*/
	@RequestMapping("/findResourceTree.htm")
	@ResponseBody
	public  List<BeanStandardResource> findResourceTree(String status ,HttpServletRequest request){
		boolean flag = isSuperAdmin(request);
		Long userId = null;
		if(!flag){
			userId = (Long) request.getSession().getAttribute("userId");
		}
		List<BeanResource> list = resourceService.findUserResourcesByUserId(userId);
		
		
		ResourceFormat rf = new ResourceFormat();
		List<BeanStandardResource> bsrList = rf.format(list, ValueUtil.isNotEmpty(status)?status:"closed");
		return bsrList;
	}
	
	

}
