package com.kapphk.pms.service.interFace;

import java.util.List;
import java.util.Map;



/**   
 * @ClassName: InterfaceBeanEquipmentService    
 * @Description: 设备管理   
 * @author XS  
 * @date 2016-1-25 下午1:32:08    
 *         
 */
public interface InterfaceBeanEquipmentService {
	
	public List<Map<String, Object>> findMyEquipmentByCondition(Map<String,Object> condition);
	
	
	public void save(Long userId,String number);
	
	/**   
	* @Title: deleteEquipment    
	* @Description: 参数设备、设备用户关系 
	* @param     设定文件    
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-27 下午3:49:35
	*/
	public void delete(Long id);

}
