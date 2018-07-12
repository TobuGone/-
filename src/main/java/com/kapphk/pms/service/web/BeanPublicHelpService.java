package com.kapphk.pms.service.web;

import javax.servlet.http.HttpServletRequest;

import com.kapphk.pms.bean.BeanPublicHelp;
import com.kapphk.pms.service.BaseService;
import com.kapphk.pms.util.Result;

public interface BeanPublicHelpService extends BaseService<BeanPublicHelp, Long>{

	/**
	 * 查询文案数据列表
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-11-12
	 */
	public Result getPublicHelpList(int page,int rows)throws Exception;
	
	/**
	 * 修改保存数据
	 * @param beanPublicHelp
	 * huangzh 2015-11-12
	 * @return
	 * @throws Exception
	 */
	public Result saveServiceAgreeMent(BeanPublicHelp beanPublicHelp)throws Exception;
	
	/**
	 * 根据标题查询
	 * @param title 标题
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-12-10
	 */
	public Result searchByTitle(String title,HttpServletRequest request)throws Exception;

}
