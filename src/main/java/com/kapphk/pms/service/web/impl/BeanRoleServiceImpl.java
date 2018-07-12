package com.kapphk.pms.service.web.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kapphk.pms.bean.BeanRole;
import com.kapphk.pms.bean.BeanRoleResource;
import com.kapphk.pms.dao.mapper.BeanRoleMapper;
import com.kapphk.pms.service.web.BeanRoleService;
import com.kapphk.pms.util.Contents;
import com.kapphk.pms.util.DateUtils;
import com.kapphk.pms.util.Result;
import com.kapphk.pms.util.ValidateUtils;
import com.kapphk.pms.util.ValueUtil;
@Service("beanRoleService")
public class BeanRoleServiceImpl implements BeanRoleService{

	@Autowired
	private  BeanRoleMapper roleMapper;
	
	/**
	 * 查询角色下拉框数据列表
	 * @return
	 * @author huangzh 2015-11-12
	 */
	public List<Map<String, Object>> getComboxList(){
		List<Map<String, Object>> mapList = roleMapper.findComboxList();
		
		return mapList;	
	}
	
	
	/**
	 * 根据角色ID查询  角色拥有的菜单资源ID
	 * @param roleId
	 * @return
	 */
	@Override
	public List<Long> findUserResourceIdsByRoleId(@Param(value="roleId") Long roleId){
		List<Map<String, Object>> list = roleMapper.findUserResourceIdsByRoleId(roleId);
		List<Long> lList = new ArrayList<Long>();
		if(list != null && list.size() >=1 ){
			for(Map<String, Object> map :list){
				Long menuId = ValueUtil.toLong(ValueUtil.toString(map.get("id")));
				if(menuId >= 1){
					lList.add(menuId);
				}
			}
		}
		return lList;
	};
	
	/**
	 * 查询用户拥有的角色
	 */
	@Override
	public List<BeanRole> findRoleListByUserId(Map<String, Object> map) {
		 return roleMapper.findRoleListByUserId(map);
	}
	
	/**
	 * 根据用户ID查询  用户拥有的所有角色ids
	 */
	public List<Long> findRoleIdsByUserId(Long userId) {
	   List<Map<String,Object>> list = 	roleMapper.findRoleIdsByUserId(userId);
	   List<Long> lList = new ArrayList<Long>();
		if(list != null && list.size() >=1 ){
			for(Map<String, Object> map :list){
				Long roleId = ValueUtil.toLong(ValueUtil.toString(map.get("id")));
				if(roleId >= 1){
					lList.add(roleId);
				}
			}
		}
		return lList;
	}

  
	/**   
	* @Title: insertRoleResource    
	* @Description: 添加角色资源关系  
	*/
	@Override
	public void insertRoleResource(Long id,Long [] resourceIds){
		Long [] ids = new Long[]{id};
		//删除角色资源关系
		 roleMapper.deleteRoleResourceByRoleIds(ids);
		 //添加角色资源关系
		 addRoleResource(id,resourceIds);
	}

	
	
	/**
	 * 添加角色资源关系
	 */
	public void addRoleResource(Long roleId ,Long [] resourceIds){
        if(resourceIds != null && resourceIds.length >= 1){
       	 List<BeanRoleResource> list = new ArrayList<BeanRoleResource>();
       	 for(Long resourceId:resourceIds){
       		 if(resourceId != null){
       			 BeanRoleResource roleResource = new BeanRoleResource();
       			 roleResource.setResourceId(resourceId.intValue());
       			 roleResource.setRoleId(roleId.intValue());
       			 list.add(roleResource);
       		 }
       	 } 
       	 if(list.size() >= 1){
       		 roleMapper.insertRoleResources(list);
       	 } 
        }
	}

	/**
	 * 查询系统角色列表
	 * @param roleName 角色名称
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-12-24
	 */
	@Override
	public Result getSystemRoleList(String roleName,int page,int rows){
		Result rs = new Result();
		
		List<Map<String, Object>> list = roleMapper.findSystemRoleList(roleName,(page-1)*rows,rows);
		
		int count = roleMapper.findSystemRoleCount(roleName);
		
		rs.put("total", count);
		rs.put("rows", list);
		
		return rs;
	}


	/**
	 * 新增或修改角色
	 * @param beanRole 对象
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-12-24
	 */
	@Override
	public Result saveRole(BeanRole beanRole){
		Result rs = new Result();
		
		//新增
		if(ValidateUtils.isBlank(beanRole.getId())){
			BeanRole role = new BeanRole(); 
			role.setRoleName(beanRole.getRoleName());
			
			int count = roleMapper.findByPageCount(role);
			
			if(count != 0){
				rs.setStatus(Contents.EXIST);
				rs.setMsg("角色已重复，请重新输入");
				return rs;
			}
			
			beanRole.setCreateTime(DateUtils.getLocalDate());
			
			roleMapper.insert(beanRole);
		}
		//修改
		else{
			BeanRole role = new BeanRole(); 
			role.setRoleName(beanRole.getRoleName());
			
			List<BeanRole> list = roleMapper.findByPage(role, 0, 1);
			
			if(!ValidateUtils.isEmptyForCollection(list)){
				BeanRole br = list.get(0);
				if(br.getId().equals(beanRole.getId())){
					beanRole.setCreateTime(DateUtils.getLocalDate());
					roleMapper.update(beanRole);
				}else{
					rs.setStatus(Contents.EXIST);
					rs.setMsg("角色已重复，请重新输入");
					return rs;
				}
			}else{
				beanRole.setCreateTime(DateUtils.getLocalDate());
				roleMapper.update(beanRole);
			}
		}
		
		return rs;
	}

	/**
	 * 改变角色状态
	 * @param ids
	 * @param status 状态
	 * @return
	 * @author huangzh 2015-11-12
	 */
	@Override
	public Result changeStemRoleStatus(String ids, String status) {
		Result rs = new Result();
		
		String [] userIds = ids.split(",");
		
		for(String uid:userIds){
			BeanRole beanRole = new BeanRole();
			beanRole.setId(Long.parseLong(uid));
			beanRole.setStatus(Integer.parseInt(status));
			roleMapper.update(beanRole);
		}
		
		return rs;
	}
}
