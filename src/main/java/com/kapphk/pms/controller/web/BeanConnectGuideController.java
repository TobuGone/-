package com.kapphk.pms.controller.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kapphk.pms.bean.BeanConnectGuide;
import com.kapphk.pms.service.web.BeanConnectGuideService;
import com.kapphk.pms.util.Result;
import com.kapphk.pms.util.Result.MSG;

/**
 * 连接指南控制层
 * @author huangzh 2016-01-13
 */
@RestController
@RequestMapping("/connect/")
public class BeanConnectGuideController {
	@Autowired
	private BeanConnectGuideService service;
	
	/**
	 * 查询连接指南列表
	 * @param page 页数
	 * @param rows 每页显示的行数
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-13
	 */
	@RequestMapping("getConnectList.htm")
	public Result getConnectList(int page,int rows)throws Exception{
		Result rs = new Result();
		
		rs = service.getConnectList( page, rows);
		
		return rs;
	}
	
	/**
	 * 新增或修改连接指南
	 * @param beanConnectGuide 对象
	 * @param file 文件流
	 * @param req 请求参数
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-13
	 */
	@RequestMapping("saveConnect.htm")
	public Result saveConnect(BeanConnectGuide beanConnectGuide,MultipartFile file,HttpServletRequest req)throws Exception{
		Result rs= new Result();
		
		rs = service.saveConnect( beanConnectGuide, file, req);
		
		return rs;
	}
	
	
	/**
	 * 查询单条数据
	 * @param id
	 * @return
	 * huangzh 2015-08-14
	 * @throws Exception
	 */
	@RequestMapping("data.htm")
	public Result data(Long id)throws Exception{
		Result rs = new Result();
		
		BeanConnectGuide data = null;
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
	
	
	/**
	 * 批量删除
	 * @param ids id集合
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-12-22
	 */
	@RequestMapping("delConnect.htm")
	public Result delConnect(String ids)throws Exception{
		Result rs = new Result();
		
		rs = service.delConnect(ids);
		
		return rs;
	}
}
