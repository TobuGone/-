package com.kapphk.pms.controller.interFace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kapphk.pms.bean.BeanCity;
import com.kapphk.pms.bean.BeanDistrct;
import com.kapphk.pms.bean.BeanProvince;
import com.kapphk.pms.service.interFace.InterfaceBeanAreaService;
import com.kapphk.pms.util.Contents;
import com.kapphk.pms.util.Result;

/**   
 * @ClassName: InterfaceBeanAreaController    
 * @Description:  省市区管理  
 * @author XS  
 * @date 2016-1-28 下午1:37:38    
 *         
 */
@RestController
@RequestMapping("/area/")
public class InterfaceBeanAreaController {
	
	@Autowired
	private InterfaceBeanAreaService areaService;
	
	
	
	/**   
	* @Title: searchProvince    
	* @Description:  查询省份  
	* @param     设定文件    
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-28 下午2:08:34
	*/
	@RequestMapping("searchProvince.aspf")
    public Result searchProvince(Result rs){
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			
			List<BeanProvince> pList =areaService.findProvinceByCondition(condition);
			rs.put("provinces", pList != null ? pList : new ArrayList<Object>());
		} catch (Exception e) {
			 e.printStackTrace();
			 rs.setStatus(Contents.ERROR);
		}
		return rs;
	}
	
	
	
	/**   
	* @Title: searchCity    
	* @Description:  查询城市    
	* @param     设定文件    
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-28 下午2:08:45
	*/
	@RequestMapping("searchCity.aspf")
    public Result searchCity(Long pid,Result rs){
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("pid", pid);
			List<BeanCity> cList =  areaService.findCityByCondition(condition) ;
			rs.put("citys", cList != null ? cList : new ArrayList<Object>());
		} catch (Exception e) {
			 e.printStackTrace();
			 rs.setStatus(Contents.ERROR);
		}
		return rs;
	}
	
	/**   
	* @Title: searchDistrct    
	* @Description: 查询地区 
	* @param     设定文件    
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-28 下午2:08:56
	*/
	@RequestMapping("searchDistrct.aspf")
    public Result searchDistrct(Long cid,Result rs){
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("cid", cid);
			List<BeanDistrct> dList =  areaService.findDistrctByCondition(condition) ;
			rs.put("distrcts", dList != null ? dList : new ArrayList<Object>());
		} catch (Exception e) {
			 e.printStackTrace();
			 rs.setStatus(Contents.ERROR);
		}
		return rs;
	}
	
}
