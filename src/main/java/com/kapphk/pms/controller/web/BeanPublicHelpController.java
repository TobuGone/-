package com.kapphk.pms.controller.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kapphk.pms.bean.BeanPublicHelp;
import com.kapphk.pms.service.web.BeanPublicHelpService;
import com.kapphk.pms.util.Result;
import com.kapphk.pms.util.Result.MSG;

/**
 * 文案管理控制层
 * @author huangzh 2015-11-12
 */
@RestController
@RequestMapping("/publichelp/")
public class BeanPublicHelpController {
	
	@Autowired
	private BeanPublicHelpService service;
	
	/**
	 * 查询文案数据列表
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-11-12
	 */
	@RequestMapping("getPublicHelpList.htm")
	public Result getPublicHelpList(int page,int rows)throws Exception{
		Result rs = new Result();
		
		rs = service.getPublicHelpList( page, rows);
		
		return rs;
	}
	
	
	/**
	 * 查询单条数据
	 * @param id
	 * @return
	 * huangzh 2015-11-12
	 * @throws Exception
	 */
	@RequestMapping("data.htm")
	public Result data(Long id)throws Exception{
		Result rs = new Result();
		
		BeanPublicHelp data = null;
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
	 * 修改保存数据
	 * @param beanPublicHelp
	 * huangzh 2015-11-12
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("saveServiceAgreeMent.htm")
	public Result saveServiceAgreeMent(BeanPublicHelp beanPublicHelp) throws Exception{
		Result rs = new Result();
		
		rs = service.saveServiceAgreeMent(beanPublicHelp);
		
		return rs;
	}
	
	/**
	 * 根据标题查询
	 * @param title 标题
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-12-10
	 */
	@RequestMapping("searchByTitle.htm")
	public Result searchByTitle(String title,HttpServletRequest request)throws Exception{
		Result rs = new Result();
		
		rs = service.searchByTitle(title,request);
		
		return rs;
	}
}
