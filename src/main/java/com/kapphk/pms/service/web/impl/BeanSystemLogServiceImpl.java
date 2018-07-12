package com.kapphk.pms.service.web.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kapphk.pms.bean.BeanSystemLog;
import com.kapphk.pms.dao.mapper.BeanSystemLogMapper;
import com.kapphk.pms.service.BaseServiceImpl;
import com.kapphk.pms.service.web.BeanSystemLogService;
import com.kapphk.pms.util.Result;
import com.kapphk.pms.util.ValidateUtils;
@Service("beanSystemLogService")
public class BeanSystemLogServiceImpl extends BaseServiceImpl<BeanSystemLog, Long>implements BeanSystemLogService {

	@Autowired
	private BeanSystemLogMapper logMapper;
	
	
	@Override
	public void init() {
		super.setMapper(logMapper);
	}

	/**
	 * 查询系统日志列表
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-12-22
	 */
	@Override
	public Result getloglist(int page,int rows) throws Exception {
		Result rs = new Result();
		
		int count = logMapper.findMapAllCount();
		
		List<Map<String, Object>> list = logMapper.findMapAll((page-1)*rows,rows);
		rs.put("total", count);
		rs.put("rows",list);
		
		return rs;
	}

	/**
	 * 批量删除日志
	 * @param ids id集合
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-12-22
	 */
	@Override
	public Result delSystemLog(String ids) throws Exception {
		Result rs = new Result();
		
		if(!ValidateUtils.isBlank(ids)){
			List<Long> idList = new ArrayList<Long>();
			
			String [] strids = ids.split(",");
			
			for(String id:strids){
				idList.add(Long.parseLong(id));
			}
			
			logMapper.deletes(idList);
		}
		
		return rs;
	}
	
	

}
