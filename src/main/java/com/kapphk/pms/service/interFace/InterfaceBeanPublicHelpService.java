package com.kapphk.pms.service.interFace;

import java.util.List;
import java.util.Map;

import com.kapphk.pms.bean.BeanPublicHelp;

/**   
 * @ClassName: InterfaceBeanPublicHelpService    
 * @Description: 用户协议管理  
 * @author XS  
 * @date 2016-1-27 下午12:07:29    
 *         
 */
public interface InterfaceBeanPublicHelpService {
	
	  List<BeanPublicHelp> findByCondition(  Map<String,Object> condition);

	public String findPublicDetail(BeanPublicHelp help);

}
