package com.kapphk.pms.dao.mapper;

import com.kapphk.pms.bean.BeanMenubookCategory;

/**
 * 的数据操作接口
 * @author zoneyu 2016-01-22
*/
public interface BeanMenubookCategoryMapper extends BaseMapper<BeanMenubookCategory, Long> {
	/**
	 * 根据菜单id，删除步骤 
	 * @param menuid
	 */
	public void deleteByMenuId(Long menuid);
}