package com.kapphk.pms.service.interFace.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.kapphk.pms.bean.BeanErrorProblem;
import com.kapphk.pms.dao.mapper.BeanErrorProblemMapper;
import com.kapphk.pms.service.interFace.InterfaceBeanErrorProblemService;
import com.kapphk.pms.util.Contents;
import com.kapphk.pms.util.Result;
import com.kapphk.pms.util.ValidateUtils;
import com.sun.crypto.provider.RSACipher;


@Service
public class InterfaceBeanErrorProblemServiceImpl implements
		InterfaceBeanErrorProblemService {
	
	@Autowired
	private BeanErrorProblemMapper errorProblemMapper;

	/**   
	* @Title: findByCondition    
	* @Description: TODO(这里用一句话描述这个方法的作用)    
	* @param @param condition
	* @param @return    设定文件    
	* @throws    
	*/

	@Override
	public Result findError(String errorNumber, String errorName, Result rs) {
			Map<String,Object> condition = new HashMap<String, Object>();
			condition.put("errorNumber", errorNumber);
			condition.put("errorName", errorName);
			List<Map<String, Object>> list = errorProblemMapper.findCondition(condition);
			rs.put("error",list);
			return rs;
	}

	@Override
	public List<BeanErrorProblem> errorDetail(String errorNumber,String errorName) {
		List<BeanErrorProblem> list = null;
		try {
			if(!ValidateUtils.isBlank(errorNumber) || !ValidateUtils.isBlank(errorName)){
				BeanErrorProblem item = new BeanErrorProblem();
				item.setErrorNumber(errorNumber);
				item.setErrorName(errorName);
				return errorProblemMapper.findByPage(item, 0, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<BeanErrorProblem> findByCondition(Map<String, Object> condition) {
		return errorProblemMapper.findByCondition(condition);
	}

}
