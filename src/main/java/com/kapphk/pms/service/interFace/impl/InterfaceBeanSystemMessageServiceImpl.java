package com.kapphk.pms.service.interFace.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kapphk.pms.bean.BeanMessagePush;
import com.kapphk.pms.bean.PaginationCondition;
import com.kapphk.pms.dao.mapper.BeanAppAndroidUpdateMapper;
import com.kapphk.pms.dao.mapper.BeanAppIOSUpdateMapper;
import com.kapphk.pms.dao.mapper.BeanEquipmentUpdateMapper;
import com.kapphk.pms.dao.mapper.BeanEquipmentUpdateOldMapper;
import com.kapphk.pms.dao.mapper.BeanMessagePushMapper;
import com.kapphk.pms.service.interFace.InterfaceBeanSystemMessageService;
import com.kapphk.pms.util.Result;

/**   
 * @ClassName: InterfaceBeanSystemMessageServiceImpl    
 * @Description: TODO(这里用一句话描述这个类的作用)    
 * @author XS  
 * @date 2016-1-28 下午5:13:44    
 *         
 */
@Service
public class InterfaceBeanSystemMessageServiceImpl implements InterfaceBeanSystemMessageService{

	@Autowired
	private BeanMessagePushMapper messagePushMapper;
	
	@Autowired
	private BeanEquipmentUpdateOldMapper equipmentUpdateOldMapper;
	
	@Autowired
	private BeanEquipmentUpdateMapper equipmentUpdateMapper;
	
	@Autowired
	private BeanAppAndroidUpdateMapper appAndroidUpdateMapper;
	
	@Autowired BeanAppIOSUpdateMapper appIOSUpdateMapper;
	
	public List<Map<String, Object>> findPagination(Map<String,Object> condition,PaginationCondition pc){
		return messagePushMapper.findPagination(condition, pc);
	}


	/**   
	* @Title: findById    
	* @Description: TODO(这里用一句话描述这个方法的作用)    
	* @param @param id
	* @param @return    设定文件    
	* @throws    
	*/
	@Override
	public BeanMessagePush findById(Long id) {
		 return messagePushMapper.findById(id);
	}
	
	/**
	 * 更新PMS电磁炉固件(旧方式)
	 * huzi 2018-06-05
	 */
	@Override
	public void getEquipmentUpdateOld(Result rs) throws Exception {
		List<Map<String, Object>> list = equipmentUpdateOldMapper.getEquipmentUpdate();
		rs.put("fileUpdate", list);
		
	}


	/**
	 * 更新PMS电磁炉固件
	 * huzi 2018-01-29
	 */
	@Override
	public void getEquipmentUpdate(Result rs) throws Exception {
		List<Map<String, Object>> list = equipmentUpdateMapper.getEquipmentUpdate();
		rs.put("fileUpdate", list);
		
	}

	
	/**
	 * 更新Android端app包
	 * huzi 2018-01-29
	 */
	@Override
	public void getAppAndroidUpdate(Result rs) throws Exception {
		List<Map<String, Object>> list = appAndroidUpdateMapper.getAppAndroidUpdate();
		rs.put("fileUpdate", list);
	}


	/**
	 * 更新ios端app包
	 * huzi 2018-04-24
	 */
	@Override
	public void getAppIOSUpdate(Result rs) throws Exception {
		List<Map<String, Object>> list = appIOSUpdateMapper.getAppIOSUpdate();
		rs.put("fileUpdate", list);
		
	}


	
}
