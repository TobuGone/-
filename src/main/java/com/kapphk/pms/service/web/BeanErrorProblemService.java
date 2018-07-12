package com.kapphk.pms.service.web;

import com.kapphk.pms.bean.BeanErrorProblem;
import com.kapphk.pms.service.BaseService;
import com.kapphk.pms.util.Result;

public interface BeanErrorProblemService extends BaseService<BeanErrorProblem, Long>{

	/**
	 * 查询错误问题数据列表
	 * @param page 页数
	 * @param rows 每页显示的行数
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-13
	 */
	public Result getErrorList(int page, int rows)throws Exception;

	/**
	 * 新增或修改错误问题
	 * @param beanErrorProblem 对象
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-13
	 */
	public Result saveError(BeanErrorProblem beanErrorProblem)throws Exception;

	/**
	 * 批量删除
	 * @param ids 集合id
	 * huangzh 2015-08-14
	 * @return
	 */
	public Result delError(String ids)throws Exception;

}
