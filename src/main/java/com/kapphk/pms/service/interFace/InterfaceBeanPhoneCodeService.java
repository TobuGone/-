package com.kapphk.pms.service.interFace;

import java.util.List;
import java.util.Map;

import com.kapphk.pms.bean.BeanPhoneCode;

/**   
 * @ClassName: InterfaceBeanPhoneCodeService    
 * @Description: 手机验证码管理 
 * @author XS  
 * @date 2016-1-27 上午9:42:01    
 *         
 */
public interface InterfaceBeanPhoneCodeService {
	
	public void insert(BeanPhoneCode phoneCode);
	
	public List<BeanPhoneCode> findByCondition(Map<String, Object> condition);

}
