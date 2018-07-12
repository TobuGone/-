package com.kapphk.pms.service.web;

import com.kapphk.pms.bean.BeanSystemLog;
import com.kapphk.pms.service.BaseService;
import com.kapphk.pms.util.Result;

public interface BeanSystemLogService extends BaseService<BeanSystemLog, Long>{

	/**
	 * 查询系统日志列表
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-12-22
	 */
	public Result getloglist(int page,int rows)throws Exception;

	/**
	 * 批量删除日志
	 * @param ids id集合
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-12-22
	 */
	public Result delSystemLog(String ids)throws Exception;

}
