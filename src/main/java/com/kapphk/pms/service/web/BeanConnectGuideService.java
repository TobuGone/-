package com.kapphk.pms.service.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.kapphk.pms.bean.BeanConnectGuide;
import com.kapphk.pms.service.BaseService;
import com.kapphk.pms.util.Result;

public interface BeanConnectGuideService extends BaseService<BeanConnectGuide, Long>{

	/**
	 * 查询连接指南列表
	 * @param page 页数
	 * @param rows 每页显示的行数
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-13
	 */
	public Result getConnectList(int page, int rows)throws Exception;

	/**
	 * 新增或修改连接指南
	 * @param beanConnectGuide 对象
	 * @param file 文件流
	 * @param req 请求参数
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-13
	 */
	public Result saveConnect(BeanConnectGuide beanConnectGuide,
			MultipartFile file, HttpServletRequest req)throws Exception;

	/**
	 * 批量删除
	 * @param ids id集合
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-12-22
	 */
	public Result delConnect(String ids)throws Exception;

}
