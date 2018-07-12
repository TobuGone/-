package com.kapphk.pms.service.web.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kapphk.pms.bean.BeanErrorProblem;
import com.kapphk.pms.dao.mapper.BeanErrorProblemMapper;
import com.kapphk.pms.service.BaseServiceImpl;
import com.kapphk.pms.service.web.BeanErrorProblemService;
import com.kapphk.pms.util.Contents;
import com.kapphk.pms.util.DateUtils;
import com.kapphk.pms.util.Result;
import com.kapphk.pms.util.ValidateUtils;
@Service("beanErrorProblemService")
public class BeanErrorProblemServiceImpl extends BaseServiceImpl<BeanErrorProblem, Long>implements BeanErrorProblemService {

	@Autowired
	private BeanErrorProblemMapper problemMapper;
	
	@Override
	public void init() {
		super.setMapper(problemMapper);
	}

	/**
	 * 查询错误问题数据列表
	 * @param page 页数
	 * @param rows 每页显示的行数
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-13
	 */
	@Override
	public Result getErrorList(int page, int rows) throws Exception {
		Result rs = new Result();
		
		BeanErrorProblem beanErrorProblem = new BeanErrorProblem();
		
		List<BeanErrorProblem> list = problemMapper.findByPage(beanErrorProblem, (page-1)*rows, rows);
		
		int count = problemMapper.findByPageCount(beanErrorProblem);
		
		rs.put("total", count);
		rs.put("rows", list);
		
		return rs;
	}

	/**
	 * 新增或修改错误问题
	 * @param beanErrorProblem 对象
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-13
	 */
	@Override
	public Result saveError(BeanErrorProblem beanErrorProblem) throws Exception {
		Result rs = new Result();
		
		//新增
		if(ValidateUtils.isBlank(beanErrorProblem.getId())){
			BeanErrorProblem errorProblem = new BeanErrorProblem();
			errorProblem.setErrorName(beanErrorProblem.getErrorName());
			
			int count = problemMapper.findByPageCount(errorProblem);
			
			if(count != 0){
				rs.setStatus(Contents.EXIST);
				rs.setMsg("错误名称已重复，请重新输入");
				return rs;
			}
			
			BeanErrorProblem problem = new BeanErrorProblem();
			problem.setErrorNumber(beanErrorProblem.getErrorNumber());
			
			int count1 = problemMapper.findByPageCount(problem);
			
			if(count1 != 0){
				rs.setStatus(Contents.EXIST);
				rs.setMsg("错误编号已重复，请重新输入");
				return rs;
			}
			
			beanErrorProblem.setCreateTime(DateUtils.getLocalDate());
			
			problemMapper.insert(beanErrorProblem);
			
		}
		//修改
		else{
			
			BeanErrorProblem errorProblem = new BeanErrorProblem();
			errorProblem.setErrorName(beanErrorProblem.getErrorName());
			
			List<BeanErrorProblem> list = problemMapper.findByPage(errorProblem,0,1);
			
			if(!ValidateUtils.isEmptyForCollection(list) && !list.get(0).getId().equals(beanErrorProblem.getId())){
				rs.setStatus(Contents.EXIST);
				rs.setMsg("错误名称已重复，请重新输入");
				return rs;
			}
			
			BeanErrorProblem problem = new BeanErrorProblem();
			problem.setErrorNumber(beanErrorProblem.getErrorNumber());
			
			List<BeanErrorProblem> list1 = problemMapper.findByPage(problem,0,1);
			
			if(!ValidateUtils.isEmptyForCollection(list1) && !list1.get(0).getId().equals(beanErrorProblem.getId())){
				rs.setStatus(Contents.EXIST);
				rs.setMsg("错误编号已重复，请重新输入");
				return rs;
			}
			
			problemMapper.update(beanErrorProblem);
			
		}
		
		return rs;
	}

	/**
	 * 批量删除
	 * @param ids 集合id
	 * huangzh 2015-08-14
	 * @return
	 */
	@Override
	public Result delError(String ids) throws Exception {
		Result rs = new Result();
		
		if(!ValidateUtils.isBlank(ids)){
			List<Long> idList = new ArrayList<Long>();
			
			String [] strids = ids.split(",");
			
			for(String id:strids){
				idList.add(Long.parseLong(id));
			}
			
			problemMapper.deletes(idList);
		}
		
		return rs;
	}

}
