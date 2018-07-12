package com.kapphk.pms.controller.web;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kapphk.pms.bean.BeanAppAndroidUpdate;
import com.kapphk.pms.service.web.BeanAppAndroidService;
import com.kapphk.pms.util.Result;
import com.kapphk.pms.util.Result.MSG;

/**
 * 安卓端app版本控制层
 * @author huzi
 *
 */
@RestController
@RequestMapping("/appAndroid/")
public class BeanAppAndroidController {

	@Autowired
	private BeanAppAndroidService service;
	
	@RequestMapping("getAppAndroidList.htm")
	public Result getAppAndroidList(BeanAppAndroidUpdate appAndroidUpdate, String versionName,String startTime, String endTime,
			int page,int rows)throws Exception{
	
		return service.getAppAndroidList(appAndroidUpdate, versionName, startTime, endTime, page, rows);
		  
	}
	
	@RequestMapping("saveAppAndroid.htm")
	public Result saveAppAndroid(MultipartFile file,BeanAppAndroidUpdate appAndroidUpdate)throws Exception{
		
		return  service.saveAppAndroid(appAndroidUpdate, file);
		
	}	
	
	@RequestMapping("delAppAndroid.htm")
	public Result delAppAndroid(Long[] ids)throws Exception{
		
		return  service.delAppAndroid(Arrays.asList(ids));
		
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
		
		BeanAppAndroidUpdate  data = null;
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
