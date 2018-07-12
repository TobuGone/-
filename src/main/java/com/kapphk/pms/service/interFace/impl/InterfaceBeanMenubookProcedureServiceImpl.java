package com.kapphk.pms.service.interFace.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kapphk.pms.bean.BeanMenubookProcedure;
import com.kapphk.pms.dao.mapper.BeanMenubookProcedureMapper;
import com.kapphk.pms.service.interFace.InterfaceBeanMenubookProcedureService;

/**   
 * @ClassName: InterfaceBeanMenubookProcedureServiceImpl    
 * @Description: 菜制作步骤管理   
 * @author XS  
 * @date 2016-1-26 下午4:31:25    
 *         
 */
@Service
public class InterfaceBeanMenubookProcedureServiceImpl implements
		InterfaceBeanMenubookProcedureService {
	
	@Autowired
	private BeanMenubookProcedureMapper menubookProcedureMapper;

	/**   
	* @Title: findByCondition    
	* @Description: TODO(这里用一句话描述这个方法的作用)    
	* @param @param conditon
	* @param @return    设定文件    
	* @throws    
	*/
	@Override
	public List<BeanMenubookProcedure> findByCondition(
			Map<String, Object> condition) {
		return menubookProcedureMapper.findByCondition(condition);
	}

}
