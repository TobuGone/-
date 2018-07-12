package com.kapphk.pms.service.interFace.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kapphk.pms.bean.BeanPublicHelp;
import com.kapphk.pms.dao.mapper.BeanPublicHelpMapper;
import com.kapphk.pms.service.interFace.InterfaceBeanPublicHelpService;
import com.kapphk.pms.util.Contents;
import com.kapphk.pms.util.Result;
import com.kapphk.pms.util.ValidateUtils;
@Service("interfaceBeanPublicHelpService")
public class InterfaceBeanPublicHelpServiceImpl implements
		InterfaceBeanPublicHelpService {
	@Autowired
	private BeanPublicHelpMapper publicHelpMapper;
	
	/**   
	* @Title: findByCondition    
	* @Description: TODO(这里用一句话描述这个方法的作用)    
	* @param @param condition
	* @param @return    设定文件    
	* @throws    
	*/
	@Override
	public List<BeanPublicHelp> findByCondition(Map<String, Object> condition) {
		return publicHelpMapper.findByCondition(condition);
	}
	
	/**
	 * 查询分享下载
	 */
	@Override
	public String findPublicDetail(BeanPublicHelp help) {
		if(!ValidateUtils.isBlank(help.getTitle())){
			BeanPublicHelp publicHelp = publicHelpMapper.findByTitle(help.getTitle());
			return publicHelp.getMemo();
		}
		return null;
	}

}
