package com.kapphk.pms.controller.web;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kapphk.pms.bean.BeanEquipmentUpdateOld;
import com.kapphk.pms.service.web.BeanEquipmentOldService;
import com.kapphk.pms.util.Result;
import com.kapphk.pms.util.Result.MSG;

/**
 * 版本控制层
 * @author huzi 2018-06-05
 *
 */
@RestController
@RequestMapping("/equipmentOld/")
public class BeanEquipmentOldController {

	@Autowired
	private BeanEquipmentOldService service;
	
	@RequestMapping("getEquipmentList.htm")
	public Result getEquipmentList(BeanEquipmentUpdateOld equipmentUpdateOld, String versionsName,String startTime, String endTime,
			int page,int rows)throws Exception{
		
		return  service.getEquipmentList(equipmentUpdateOld, versionsName, startTime, endTime, page, rows);
		
	}
	
	@RequestMapping("saveEquipment.htm")
	public Result saveEquipment(MultipartFile file,BeanEquipmentUpdateOld equipmentUpdateOld)throws Exception{
		
		return  service.saveEquipment(equipmentUpdateOld, file);
		
	}
	
	@RequestMapping("delEquipment.htm")
	public Result delEquipment(Long[] ids)throws Exception{
		
		return  service.delEquipment(Arrays.asList(ids));
		
	}
	
	/**
	 * 查询单条数据  huzi 2018-06-05
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("data.htm")
	public Result data(Long id)throws Exception{
		Result rs = new Result();
		
		BeanEquipmentUpdateOld  data = null;
		try {
			data = service.findById(id);
		} catch (Exception e) { 
			rs.setError(MSG.ERROR);
			rs.setMsg("请求失败");
			e.printStackTrace();
		}	
		
		if(null == data) rs.setError(MSG.NOT_FOUND, "查询失败", "信息");
		else rs.put("data", data);
		return rs;
	}
	
}
