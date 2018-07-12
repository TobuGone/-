package com.kapphk.pms.controller.web;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kapphk.pms.bean.BeanEquipmentUpdate;
import com.kapphk.pms.service.web.BeanEquipmentService;
import com.kapphk.pms.util.Result;
import com.kapphk.pms.util.Result.MSG;

/**
 * 版本控制层
 * @author LENOVO
 *
 */
@RestController
@RequestMapping("/equipment/")
public class BeanEquipmentController {

	@Autowired
	private BeanEquipmentService service;
	
	@RequestMapping("getEquipmentList.htm")
	public Result getEquipmentList(BeanEquipmentUpdate equipmentUpdate, String versionCode,String startTime, String endTime,
			int page,int rows)throws Exception{
		
		return  service.getEquipmentList(equipmentUpdate, versionCode, startTime, endTime, page, rows);
		
	}
	
	@RequestMapping("saveEquipment.htm")
	public Result saveEquipment(MultipartFile file,BeanEquipmentUpdate equipmentUpdate)throws Exception{
		
		return  service.saveEquipment(equipmentUpdate, file);
		
	}
	
	@RequestMapping("delEquipment.htm")
	public Result delEquipment(Long[] ids)throws Exception{
		
		return  service.delEquipment(Arrays.asList(ids));
		
	}
	
	
	
	/**
	 * 查询单条数据  huzi 2018-04-17
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("data.htm")
	public Result data(Long id)throws Exception{
		Result rs = new Result();
		
		BeanEquipmentUpdate  data = null;
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
