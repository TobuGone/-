package com.kapphk.pms.service.web.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.jpush.api.utils.Jgpush;

import com.kapphk.pms.bean.BeanMessagePush;
import com.kapphk.pms.dao.mapper.BeanMessagePushMapper;
import com.kapphk.pms.service.BaseServiceImpl;
import com.kapphk.pms.service.web.BeanMessagePushService;
import com.kapphk.pms.util.DateUtils;
import com.kapphk.pms.util.Result;
import com.kapphk.pms.util.ValidateUtils;
@Service("beanMessagePushService")
public class BeanMessagePushServiceImpl extends BaseServiceImpl<BeanMessagePush, Long> implements BeanMessagePushService{
	
	@Autowired
	private BeanMessagePushMapper messagePushMapper;

	@Override
	public void init() {
		super.setMapper(messagePushMapper);
	}

	/**
	 * 查询推送信息列表
	 * @param page 页数
	 * @param rows 每页显示的行数
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-13
	 */
	@Override
	public Result getPushMessageList(int page, int rows) throws Exception {
		Result rs = new Result();
		
		BeanMessagePush messagePush = new BeanMessagePush();
		
		List<BeanMessagePush> list = messagePushMapper.findByPage(messagePush, (page-1)*rows, rows);
		
		int count = messagePushMapper.findByPageCount(messagePush);
		
		rs.put("total", count);
		rs.put("rows", list);
		
		return rs;
	}

	/**
	 * 新增推送信息
	 * @param beanMessagePush 对象
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-13
	 */
	@Override
	public Result savePushMessage(BeanMessagePush beanMessagePush)
			throws Exception {
		Result rs = new Result();
		
		beanMessagePush.setCreateTime(DateUtils.getLocalDate());
		
		messagePushMapper.insert(beanMessagePush);
		
		Map<String,Object> map = new HashMap<String, Object>() ;
    	map.put("id", beanMessagePush.getId()) ;
		
		Jgpush.sendMessage2(beanMessagePush.getTitle(),map);
		
		return rs;
	}
	
	
	/**
	 * 批量删除
	 * @param ids 集合id
	 * huangzh 2015-08-14
	 * @return
	 */
	@Override
	public Result delPushMessage(String ids) throws Exception {
		
		Result rs = new Result();
		
		if(!ValidateUtils.isBlank(ids)){
			List<Long> idList = new ArrayList<Long>();
			
			String [] strids = ids.split(",");
			
			for(String id:strids){
				idList.add(Long.parseLong(id));
			}
			
			messagePushMapper.deletes(idList);
		}
		
		return rs;
	}
	
}
