package com.kapphk.pms.service.web;

import com.kapphk.pms.bean.BeanMessagePush;
import com.kapphk.pms.service.BaseService;
import com.kapphk.pms.util.Result;

public interface BeanMessagePushService extends BaseService<BeanMessagePush, Long>{

	/**
	 * 查询推送信息列表
	 * @param page 页数
	 * @param rows 每页显示的行数
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-13
	 */
	public Result getPushMessageList(int page, int rows)throws Exception;
	
	/**
	 * 新增推送信息
	 * @param beanMessagePush 对象
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-13
	 */
	public Result savePushMessage(BeanMessagePush beanMessagePush)throws Exception;
	
	/**
	 * 批量删除
	 * @param ids 集合id
	 * huangzh 2015-08-14
	 * @return
	 */
	public Result delPushMessage(String ids)throws Exception;

}
