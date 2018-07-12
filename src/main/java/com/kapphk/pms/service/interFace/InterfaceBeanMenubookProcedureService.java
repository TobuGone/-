package com.kapphk.pms.service.interFace;

import java.util.List;
import java.util.Map;

import com.kapphk.pms.bean.BeanMenubookProcedure;

/**   
 * @ClassName: InterfaceBeanMenubookProcedureService    
 * @Description:  菜制作步骤管理       
 * @author XS  
 * @date 2016-1-26 下午4:30:50    
 *         
 */
public interface InterfaceBeanMenubookProcedureService {
	
	public List<BeanMenubookProcedure> findByCondition(Map<String, Object> condition);

}
