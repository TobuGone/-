package com.kapphk.pms.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kapphk.pms.bean.BeanEquipmentUpdate;
import com.kapphk.pms.bean.BeanMenuBook;
import com.kapphk.pms.bean.PaginationCondition;

/**
 * 菜谱的数据操作接口
 * @author zoneyu 2016-01-29
*/
public interface BeanMenuBookMapper extends BaseMapper<BeanMenuBook, Long> {
	

	/**
	 * 查询菜谱列表
	 * @param searchName 菜谱名称
	 * @param page 页数
	 * @param rows 每页显示的行数
	 * @return result
	 * @throws Exception
	 * @author huangzh 2016-01-21
	 */
	public List<Map<String, Object>> findMapPage(@Param(value="searchName")String searchName,
			@Param(value="recommend")String recommend,
			@Param(value="page")Integer page,
			@Param(value="rows")Integer rows);

	
	public int findMapPageCount(@Param(value="searchName")String searchName,@Param(value="recommend")String recommend);
	
	/**
	 * 查询单条数据
	 * @param id
	 * @return
	 * huangzh 2015-08-14
	 * @throws Exception
	 */
	public BeanMenuBook findMenuBookById(Long id);


	public List<Map<String, Object>> findCategoryIds(Long id);

	/**
	 * 根据食材学名查询菜谱（下拉框）
	 * @param name 食材学名
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-08-14
	 */
	public List<Map<String, Object>> getMenuBookComboxByName(@Param(value="name")String name,@Param(value="isAdvert")String isAdvert,@Param(value="recommend")String recommend);

	/**
	 * 根据审核状态查询菜谱列表
	 * @param searchNickName 用户昵称
	 * @param AuditStatus 审核状态（1、通过，0、不通过，2、待审核）
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-08-14
	 */
	public List<Map<String, Object>> findMenuBookListByAuditStatus(
			@Param(value="searchNickName")String searchNickName,
			@Param(value="searchMenuBookName")String searchMenuBookName,
			@Param(value="auditStatus")String auditStatus,
			@Param(value="page")Integer page,
			@Param(value="rows")Integer rows);


	public int findMenuBookListByAuditStatusCount(@Param(value="searchNickName")String searchNickName,
			@Param(value="searchMenuBookName")String searchMenuBookName,
			@Param(value="auditStatus")String auditStatus);


	public List<Map<String, Object>> findCollectList(
			@Param(value="searchNickName")String searchNickName,
			@Param(value="page")Integer page,
			@Param(value="rows")Integer rows);


	public int findCollectListCount(@Param(value="searchNickName")String searchNickName);

	
	
    public List<Map<String,Object>> findByCondition(@Param("param")Map<String,Object> condition);
    
	public List<Map<String, Object>> findMenuBookPagination(@Param("param")
	Map<String, Object> condition,@Param("pc")  PaginationCondition pc); 
	
	public Map<String,Object> searchMenuBookById(@Param("id")Long id,@Param("userId") Long userId);


	public List<BeanMenuBook> findByAll(@Param("param")BeanMenuBook book);
	
	/**
	 * 根据文件名（路径中获取）查询菜谱  
	 * file_path like '%/20180326/%';
	 * @param filePath
	 * @return
	 */
	public List<BeanMenuBook> findByFilePath(String datePath);
	
	
	/**
	 * 根据硬件版本和软件版本查询数据
	 */
	public BeanEquipmentUpdate findByHardwareAndSoftwar(Map<String,Object> condition);
	
	
	
}