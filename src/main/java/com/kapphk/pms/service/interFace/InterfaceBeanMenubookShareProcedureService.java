package com.kapphk.pms.service.interFace;

import java.util.List;
import java.util.Map;

import com.kapphk.pms.bean.BeanMenubookShareProcedure;

/**   
 * @ClassName: InterfaceBeanMenubookProcedureService    
 * @Description:  分享菜谱的制作步骤管理       
 * @author huzi  
 * @date 2018-05-22  
 *         
 */
public interface InterfaceBeanMenubookShareProcedureService {
	
	public List<BeanMenubookShareProcedure> findByCondition(Map<String, Object> condition);

}