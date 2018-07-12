package com.kapphk.pms.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kapphk.pms.bean.BeanErrorProblem;
import com.kapphk.pms.service.web.BeanErrorProblemService;
import com.kapphk.pms.util.Result;
import com.kapphk.pms.util.Result.MSG;
/**
 * 错误问题控制层
 * @author huangzh 2016-01-13
 */
@RestController
@RequestMapping("/error/")
public class BeanErrorProblemController {

	@Autowired
	private BeanErrorProblemService service;
	
	/**
	 * 查询错误问题数据列表
	 * @param page 页数
	 * @param rows 每页显示的行数
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-13
	 */
	@RequestMapping("getErrorList.htm")
	public Result getErrorList(int page,int rows)throws Exception{
		Result rs = new Result();
		
		rs = service.getErrorList( page, rows);
		
		return rs;
	}
	
	/**
	 * 新增或修改错误问题
	 * @param beanErrorProblem 对象
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-13
	 */
	@RequestMapping("saveError.htm")
	public Result saveError(BeanErrorProblem beanErrorProblem)throws Exception{
		Result rs = new Result();
		
		rs = service.saveError(beanErrorProblem);
		
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
		
		BeanErrorProblem data = null;
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
	 * @param ids 集合id
	 * huangzh 2015-08-14
	 * @return
	 */
	@RequestMapping("delError.htm")
	public Result delAdvert(String ids)throws Exception{
		Result rs = new Result();
		
		rs = service.delError(ids);
		
		return rs;
	}
}
