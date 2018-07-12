package com.kapphk.pms.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kapphk.pms.bean.BeanAppAndroidUpdate;

/**
 * Android端app更新的数据操作接口
 * @author huzi 2018-01-25
*/
public interface BeanAppAndroidUpdateMapper extends BaseMapper<BeanAppAndroidUpdate, Long> {
	
	/**
	 * 分页查找数据
	 * 
	 */
	public List<Map<String,Object>> getAppAndroidList(@Param(value = "param") BeanAppAndroidUpdate appAndroidUpdate,
			@Param(value = "versionName")String versionName, 
			@Param(value = "startTime")String startTime, 
			@Param(value = "endTime")String endTime,
			@Param(value = "offset") int offset,
			@Param(value = "pageSize") int pageSize);
	
	
	/**
	 * 分页查找总条数
	 * 
	 */
	public int getAppAndroidCount(@Param(value = "param") BeanAppAndroidUpdate appAndroidUpdate,
			@Param(value = "versionName")String versionName, 
			@Param(value = "startTime")String startTime, 
			@Param(value = "endTime")String endTime,
			@Param(value = "offset") int offset,
			@Param(value = "pageSize") int pageSize);
	
	
	
	public List<Map<String, Object>> getAppAndroidUpdate();

	
	/**
	 * 根据版本名称查询数据
	 * @author  huzi 2018-05-15
	 */
	public BeanAppAndroidUpdate findByVersionName(String versionName);


	
	/**
	 * 查询最新版本应用
	 * @author  huzi 2018-05-15
	 */
	public BeanAppAndroidUpdate finMaxVersion();
	
}

