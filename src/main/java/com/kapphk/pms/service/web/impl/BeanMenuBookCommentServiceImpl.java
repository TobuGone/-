package com.kapphk.pms.service.web.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kapphk.pms.bean.BeanMenuBookComment;
import com.kapphk.pms.dao.mapper.BeanMenuBookCommentMapper;
import com.kapphk.pms.service.BaseServiceImpl;
import com.kapphk.pms.service.web.BeanMenuBookCommentService;
import com.kapphk.pms.util.DataUtils;
import com.kapphk.pms.util.Result;

/**
 * 评论serviceImpl
 * @author 胡子
 * 2018/03/12
 * 
 */
@Service("beanMenuBookCommentService")
public class BeanMenuBookCommentServiceImpl extends BaseServiceImpl<BeanMenuBookComment, Long> implements BeanMenuBookCommentService {

	 @Autowired
	  private BeanMenuBookCommentMapper mapper;
	 
	 @Override
	 public void init() {
		super.setMapper(mapper);
			
	 }
	
	@Override
	public Result getList(BeanMenuBookComment bean, String name, int page, int rows, Result rs) throws Exception {
		    List<Map<String, Object>> list = mapper.getList(bean, name, (page - 1) * rows, rows);
		    int count = mapper.getListCount(bean, name);
		    rs = DataUtils.mergeData(count, list);
		    return rs;
	}

	@Override
	public Result delete(String ids, Result rs) throws Exception {
		List<Long> idss = DataUtils.string2List(ids);
	    mapper.deletes(idss);
	    return rs;
	}

	@Override
	public Result operater(BeanMenuBookComment bean, Result rs) throws Exception {
		mapper.update(bean);
	    return rs;
	}
	
	
	

}
