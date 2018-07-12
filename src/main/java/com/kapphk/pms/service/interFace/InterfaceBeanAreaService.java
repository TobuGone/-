package com.kapphk.pms.service.interFace;

import java.util.List;
import java.util.Map;

import com.kapphk.pms.bean.BeanCity;
import com.kapphk.pms.bean.BeanDistrct;
import com.kapphk.pms.bean.BeanProvince;

/**   
 * @ClassName: InterfaceBeanAreaService    
 * @Description: 省市区管理  
 * @author XS  
 * @date 2016-1-28 下午1:38:57    
 *         
 */
public interface InterfaceBeanAreaService {
	/**   
	* @Title: findProvinceByCondition    
	* @Description: 查询省份  
	* @param     设定文件    
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-28 下午1:54:19
	*/
	public List<BeanProvince> findProvinceByCondition(Map<String,Object> condition);
	
	
	/**   
	* @Title: findCityByCondition    
	* @Description:  查询城市   
	* @param     设定文件    
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-28 下午1:54:29
	*/
	public List<BeanCity> findCityByCondition(Map<String,Object> condition);
	
	/**   
	* @Title: findDistrctByCondition    
	* @Description: 查询地区  
	* @param     设定文件    
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-28 下午1:54:40
	*/
	public List<BeanDistrct> findDistrctByCondition(Map<String,Object> condition);
}
