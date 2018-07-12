package com.kapphk.pms.service.interFace.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 
 
import com.kapphk.pms.bean.BeanPhoneCode;
import com.kapphk.pms.dao.mapper.BeanPhoneCodeMapper;
import com.kapphk.pms.service.interFace.InterfaceBeanPhoneCodeService;
import com.kapphk.pms.util.MessageUtils;
  

/**   
 * @ClassName: InterfaceBeanPhoneCodeServiceImpl    
 * @Description: 手机验证码管理  
 * @author XS  
 * @date 2016-1-27 上午9:42:23    
 *         
 */
@Service
public class InterfaceBeanPhoneCodeServiceImpl implements
		InterfaceBeanPhoneCodeService {

	
	 @Autowired
	 private BeanPhoneCodeMapper phoneCodeMapper;
	
	/**   
	* @Title: insert    
	* @Description: 保存手机验证码 保存前先删除该手机的所有验证码   
	* @param @param phoneCode    设定文件    
	* @throws    
	*/
	@Override
	public void insert(BeanPhoneCode phoneCode) {
	    //删除全部该手机验证码
		BeanPhoneCode pc = new BeanPhoneCode();
		pc.setPhone(phoneCode.getPhone());
	    phoneCodeMapper.deleteByEntity(pc);
	   //插入验证码
	    phoneCodeMapper.insert(phoneCode);
	   //发送短信验证码
		try {
			MessageUtils.sendMessage(phoneCode.getPhone(), phoneCode.getCode()) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**   
	* @Title: findByCondition    
	* @Description: TODO(这里用一句话描述这个方法的作用)    
	* @param @param condition
	* @param @return    设定文件    
	* @throws    
	*/
	@Override
	public List<BeanPhoneCode> findByCondition(Map<String, Object> condition) {
		 return phoneCodeMapper.findByCondition(condition);
	}

}
