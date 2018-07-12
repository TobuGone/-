package com.kapphk.pms.service.interFace.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kapphk.pms.bean.BeanMenubookShareProcedure;
import com.kapphk.pms.dao.mapper.BeanMenubookShareProcedureMapper;
import com.kapphk.pms.service.interFace.InterfaceBeanMenubookShareProcedureService;

/**   
 * @ClassName: InterfaceBeanMenubookShareProcedureServiceImpl    
 * @Description: 分享菜谱的制作步骤管理       
 * @author huzi 
 * @date 2018-05-22  
 *         
 */
@Service
public class InterfaceBeanMenubookShareProcedureServiceImpl implements InterfaceBeanMenubookShareProcedureService {
	
	@Autowired
	private BeanMenubookShareProcedureMapper menubookShareProcedureMapper;

	/**   
	* @Title: findByCondition    
	* @Description: TODO(这里用一句话描述这个方法的作用)    
	* @param @param conditon
	* @param @return    设定文件    
	* @throws    
	*/
	@Override
	public List<BeanMenubookShareProcedure> findByCondition(Map<String, Object> condition) {
		return menubookShareProcedureMapper.findByCondition(condition);
	}

}
