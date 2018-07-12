package com.kapphk.pms.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kapphk.pms.bean.BeanPublicHelp;

/**
 * 主键的数据操作接口
 * @author zoneyu 2016-01-12
*/
public interface BeanPublicHelpMapper extends BaseMapper<BeanPublicHelp, Long> {
	/**
	 * 根据标题查询
	 * @param title 标题
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-12-10
	 */
	public BeanPublicHelp findByTitle(@Param(value="title")String title);
	
	
	
   List<BeanPublicHelp> findByCondition(@Param("param") Map<String,Object> condition);
   
   
}