
package com.kapphk.pms.service.interFace.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kapphk.pms.bean.BeanEquipment;
import com.kapphk.pms.bean.BeanUserEquipment;
import com.kapphk.pms.dao.mapper.BeanEquipmentMapper;
import com.kapphk.pms.dao.mapper.BeanUserEquipmentMapper;
import com.kapphk.pms.service.interFace.InterfaceBeanEquipmentService;
import com.kapphk.pms.util.DateUtils;

/**   
 * @ClassName: InterfaceBeanEquipmentServiceImpl    
 * @Description:  设备管理   
 * @author XS  
 * @date 2016-1-25 下午1:32:42    
 *         
 */
@Service
public class InterfaceBeanEquipmentServiceImpl implements InterfaceBeanEquipmentService{

	@Autowired
	private BeanUserEquipmentMapper userEquipmentMapper;
	
	@Autowired
	private BeanEquipmentMapper equipmentMapper;
	
	@Override
	public List<Map<String, Object>> findMyEquipmentByCondition(
			Map<String, Object> condition) {
		 return equipmentMapper.findByCondition(condition);
	}

	/**   
	* @Title: delete    
	* @Description: 删除设备、设备和用户关系 
	* @param @param id    设定文件    
	* @throws    
	*/
	@Override
	public void delete(Long id) {
		//删除设备
		equipmentMapper.delete(id);
		//删除设备用户关系
		BeanUserEquipment ue  = new BeanUserEquipment();
		ue.setEquipmentId(id);
		userEquipmentMapper.deleteByEntity(ue);
		
	}

   /**  
	* 新增用户设备连接记录   huzi 2018-06-26
	* @Title: save    
	* @Description: TODO(这里用一句话描述这个方法的作用)    
	* @param @param userId
	* @param @param number    设定文件    
	* @throws    
	*/
	@Override
	public void save(Long userId, String number) {
		
		String createTime =  DateUtils.getLocalDate();
		BeanEquipment e = new BeanEquipment();
		e.setUid(userId);
		e.setNumber(number);
		e.setCreateTime(createTime);
		
		BeanUserEquipment ue = new BeanUserEquipment();
		ue.setUserId(userId);
		ue.setCreateTime(createTime);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("number", number);
		BeanEquipment equipment = equipmentMapper.findByUidAndNumber(map);
		
		if (equipment != null) {
			equipmentMapper.delete(equipment.getId());
			
			BeanUserEquipment u = userEquipmentMapper.findByEid(equipment.getId());
			userEquipmentMapper.delete(u.getId());
			
		    equipmentMapper.insert(e);
		    
		    ue.setEquipmentId(e.getId());
		    userEquipmentMapper.insert(ue);
		}
		else {
			equipmentMapper.insert(e);
			
			ue.setEquipmentId(e.getId());
	        userEquipmentMapper.insert(ue);
		}
		
	}	

}
