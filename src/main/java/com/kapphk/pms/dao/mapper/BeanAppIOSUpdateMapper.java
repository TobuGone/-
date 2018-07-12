package com.kapphk.pms.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kapphk.pms.bean.BeanAppIOSUpdate;

/**
 * IOS端app更新的数据操作接口
 * @author huzi 2018-01-25
*/
public interface BeanAppIOSUpdateMapper extends BaseMapper<BeanAppIOSUpdate, Long> {
	
	/**
	 * 分页查找数据
	 * 
	 */
	public List<Map<String,Object>> getAppIOSList(@Param(value = "param") BeanAppIOSUpdate appIOSUpdate,
			@Param(value = "versionName")String versionName, 
			@Param(value = "startTime")String startTime, 
			@Param(value = "endTime")String endTime,
			@Param(value = "offset") int offset,
			@Param(value = "pageSize") int pageSize);
	
	
	/**
	 * 分页查找总条数
	 * 
	 */
	public int getAppIOSCount(@Param(value = "param") BeanAppIOSUpdate appIOSUpdate,
			@Param(value = "versionName")String versionName, 
			@Param(value = "startTime")String startTime, 
			@Param(value = "endTime")String endTime,
			@Param(value = "offset") int offset,
			@Param(value = "pageSize") int pageSize);
	
	
	
	public List<Map<String, Object>> getAppIOSUpdate();

	
	/**
	 * 根据版本名称查询数据
	 */
	public BeanAppIOSUpdate findByVersionName(String versionName);


	
	/**
	 * 查询最新版本应用
	 * @author huzi 2018年04月24日
	 */
	public BeanAppIOSUpdate finMaxVersion();
	
	
	
}

