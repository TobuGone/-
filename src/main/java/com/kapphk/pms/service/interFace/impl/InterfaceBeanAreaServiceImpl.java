package com.kapphk.pms.service.interFace.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kapphk.pms.bean.BeanCity;
import com.kapphk.pms.bean.BeanDistrct;
import com.kapphk.pms.bean.BeanProvince;
import com.kapphk.pms.dao.mapper.BeanCityMapper;
import com.kapphk.pms.dao.mapper.BeanDistrctMapper;
import com.kapphk.pms.dao.mapper.BeanProvinceMapper;
import com.kapphk.pms.service.interFace.InterfaceBeanAreaService;

/**   
 * @ClassName: InterfaceBeanAreaServiceImpl    
 * @Description: TODO(这里用一句话描述这个类的作用)    
 * @author XS  
 * @date 2016-1-28 下午1:39:13    
 *         
 */
@Service
public class InterfaceBeanAreaServiceImpl implements InterfaceBeanAreaService{
 
	@Autowired
	private BeanProvinceMapper provinceMapper;
	
	@Autowired
	private BeanCityMapper cityMapper;
	
	@Autowired
	private BeanDistrctMapper distrctMapper;
	
	
	
	public List<BeanProvince> findProvinceByCondition(Map<String,Object> condition){
		return provinceMapper.findProvinceByCondition(condition);
		
	};
	
	
	public List<BeanCity> findCityByCondition(Map<String,Object> condition){
		return cityMapper.findCityByCondition(condition);
	};
	
	public List<BeanDistrct> findDistrctByCondition(Map<String,Object> condition){
		return distrctMapper.findDistrctByCondition(condition);
	};
}
