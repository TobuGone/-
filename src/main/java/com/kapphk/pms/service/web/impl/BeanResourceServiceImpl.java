package com.kapphk.pms.service.web.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kapphk.pms.bean.BeanResource;
import com.kapphk.pms.dao.mapper.BeanResourceMapper;
import com.kapphk.pms.service.web.BeanResourceService;
import com.kapphk.pms.util.ValueUtil;
@Service("beanResourceService")
public class BeanResourceServiceImpl implements BeanResourceService {

	@Autowired
	private BeanResourceMapper mapper;

	/**   
	* @Title: findUserResourcesByUserId    
	* @Description:  查询用户资源   
	* @param @param userId 用户id 为空时  默认查询所有
	* @param @param status   资源是否展开   status  open:展开  ；closed:关闭
	* @param @return    设定文件    
	* @throws    
	*/
	@Override
	public List<BeanResource> findUserResourcesByUserId(Long userId) {
		 return mapper.findUserResourcesByUserId(userId) ;
    }

	/**   
	* @Title: findUserResourceIdsByUserId    
	* @Description:  查询用户拥有的资源Id   
	* @param @param userId  用户id
	* @param @return    设定文件    
	* @throws    
	*/
	public List<Integer> findUserResourceIdsByUserId(Long userId) {
		 List<Map<String,Object>> list = mapper.findUserResourceIdsByUserId(userId);
		 List<Integer> lList = new ArrayList<Integer>();
		if(list != null && list.size() >=1 ){
			for(Map<String, Object> map :list){
				Integer menuId = ValueUtil.toInteger(ValueUtil.toString(map.get("id")));
				if(menuId >= 1){
					lList.add(menuId);
				}
			}
		}
		return lList;
	}


}
