package com.kapphk.pms.controller.web;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kapphk.pms.bean.BeanAppIOSUpdate;
import com.kapphk.pms.service.web.BeanAppIOSService;
import com.kapphk.pms.util.Contents;
import com.kapphk.pms.util.Result;
import com.kapphk.pms.util.Result.MSG;

/**
 * 苹果app版本控制层
 * @author huzi
 *
 */
@RestController
@RequestMapping("/appIOS/")
public class BeanAppIOSController {

	@Autowired
	private BeanAppIOSService service;
	
	@RequestMapping("getAppIOSList.htm")
	public Result getAppAndroidList(BeanAppIOSUpdate beanAppIOSUpdate, String versionName,String startTime, String endTime,
			int page,int rows)throws Exception{
	
		return service.getAppIOSList(beanAppIOSUpdate, versionName, startTime, endTime, page, rows);
		  
	}
	
	
	@RequestMapping("delAppIOS.htm")
	public Result delAppIOS(Long[] ids)throws Exception{
		
		return  service.delAppIOS(Arrays.asList(ids));
		
	}
	
	@RequestMapping("upAppIOS.htm")
	public Result upAppIOS(BeanAppIOSUpdate appIOSUpdate)throws Exception{
		 Result rs = new Result();
		 
		 int count = service.update(appIOSUpdate);
		 if (count!=0) {
			rs.setMsg("保存成功");
			rs.setStatus(Contents.OK);
		 }
		 return rs;
		 
	}
	
	
	/**
	 * 查询单条数据  huzi 2018-04-24
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("data.htm")
	public Result data(Long id)throws Exception{
		Result rs = new Result();
		
		BeanAppIOSUpdate  data = null;
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
