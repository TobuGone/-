package com.kapphk.pms.dao.mapper;

import com.kapphk.pms.bean.BeanFoodstorMenubook;

/**
 * 主键的数据操作接口
 * @author zoneyu 2016-01-25
*/
public interface BeanFoodstorMenubookMapper extends BaseMapper<BeanFoodstorMenubook, Long> {

	public void deleteByStoreId(Long storeid);
}