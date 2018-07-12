package com.kapphk.pms.service.web.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kapphk.pms.bean.BeanPublicHelp;
import com.kapphk.pms.dao.mapper.BeanPublicHelpMapper;
import com.kapphk.pms.service.BaseServiceImpl;
import com.kapphk.pms.service.web.BeanPublicHelpService;
import com.kapphk.pms.util.DateUtils;
import com.kapphk.pms.util.Result;
import com.kapphk.pms.util.Result.MSG;
@Service("beanPublicHelpService")
public class BeanPublicHelpServiceImpl extends BaseServiceImpl<BeanPublicHelp, Long>implements BeanPublicHelpService {
	
	@Autowired
	private BeanPublicHelpMapper publicHelpMapper;

	@Override
	public void init() {
		super.setMapper(publicHelpMapper);
	}

	/**
	 * 查询文案数据列表
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-11-12
	 */
	@Override
	public Result getPublicHelpList(int page,int rows) throws Exception {
		Result rs = new Result();
		
		BeanPublicHelp beanPublicHelp = new BeanPublicHelp();
		
		List<BeanPublicHelp> list = publicHelpMapper.findByPage(beanPublicHelp,(page-1)*rows, rows);
		int count = publicHelpMapper.findByPageCount(beanPublicHelp);
		
		rs.put("total", count);
		rs.put("rows", list);
		
		return rs;
	}
	
	/**
	 * 修改保存数据
	 * @param beanPublicHelp
	 * huangzh 2015-11-12
	 * @return
	 * @throws Exception
	 */
	@Override
	public Result saveServiceAgreeMent(BeanPublicHelp beanPublicHelp)
			throws Exception {
		Result rs = new Result();
		
		if(beanPublicHelp.getMemo() == null){
			rs.setError(MSG.PARAMS_ERROR,"内容不能为空");
			return rs;
		}
		
		beanPublicHelp.setCreateTime(DateUtils.getLocalDate());
		
		publicHelpMapper.update(beanPublicHelp);
		
		return rs;
	}

	/**
	 * 根据标题查询
	 * @param title 标题
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-12-10
	 */
	@Override
	public Result searchByTitle(String title,HttpServletRequest request) throws Exception {
		Result rs = new Result();
		
		BeanPublicHelp publicHelp = publicHelpMapper.findByTitle(title);
		
		rs.put("publicHelp", publicHelp);
		
		return rs;
	}
	
	
}
