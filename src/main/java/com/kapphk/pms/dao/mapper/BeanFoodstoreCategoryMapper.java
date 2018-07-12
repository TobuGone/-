package com.kapphk.pms.dao.mapper;

import com.kapphk.pms.bean.BeanFoodstoreCategory;

/**
 * 主键的数据操作接口
 * @author zoneyu 2016-01-25
*/
public interface BeanFoodstoreCategoryMapper extends BaseMapper<BeanFoodstoreCategory, Long> {

	public void deleteByStoreId(Long storeId);
	
	
}