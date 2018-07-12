package com.kapphk.pms.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kapphk.pms.bean.BeanMessagePush;
import com.kapphk.pms.service.web.BeanMessagePushService;
import com.kapphk.pms.util.Result;
import com.kapphk.pms.util.Result.MSG;

/**
 * 信息推送控制层
 * @author huangzh 2016-01-13
 */
@RestController
@RequestMapping("/push/")
public class BeanMessagePushController {
	
	@Autowired
	private BeanMessagePushService service;
	
	/**
	 * 查询推送信息列表
	 * @param page 页数
	 * @param rows 每页显示的行数
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-13
	 */
	@RequestMapping("getPushMessageList.htm")
	public Result getPushMessageList(int page,int rows)throws Exception{
		Result rs = new Result();
		
		rs = service.getPushMessageList(page,rows);
		
		return rs;
	}
	
	/**
	 * 新增推送信息
	 * @param beanMessagePush 对象
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-13
	 */
	@RequestMapping("savePushMessage.htm")
	public Result savePushMessage(BeanMessagePush beanMessagePush)throws Exception{
		Result rs = new Result();
		
		rs = service.savePushMessage(beanMessagePush);
		
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
		
		BeanMessagePush data = null;
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
	@RequestMapping("delPushMessage.htm")
	public Result delPushMessage(String ids)throws Exception{
		Result rs = new Result();
		
		rs = service.delPushMessage(ids);
		
		return rs;
	}
}
