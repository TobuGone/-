package com.kapphk.pms.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kapphk.pms.service.web.BeanSystemLogService;
import com.kapphk.pms.util.Result;

/**
 * 系统日志管理控制层
 * @author huangzh 2015-12-22
 */
@RestController
@RequestMapping("/log/")
public class BeanSystemLogController {
	
	@Autowired
	private BeanSystemLogService service;
	
	/**
	 * 查询系统日志列表
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-12-22
	 */
	@RequestMapping("getloglist.htm")
	public Result getloglist(int page,int rows)throws Exception{
		Result rs = new Result();
		
		rs = service.getloglist(page,rows);
		
		return rs;
	}
	
	/**
	 * 批量删除日志
	 * @param ids id集合
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-12-22
	 */
	@RequestMapping("delSystemLog.htm")
	public Result delSystemLog(String ids)throws Exception{
		Result rs = new Result();
		
		rs = service.delSystemLog(ids);
		
		return rs;
	}

}
