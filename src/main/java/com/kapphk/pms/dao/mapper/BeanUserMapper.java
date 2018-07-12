
package com.kapphk.pms.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kapphk.pms.bean.BeanUser;

/**
 * 用户的数据操作接口
 * @author zoneyu 2016-01-14
*/
public interface BeanUserMapper extends BaseMapper<BeanUser, Long> {
	/**
	 * 查询手机用户数据列表
	 * @param page 页数
	 * @param rows 每页显示的行数
	 * @return
	 * @throws Exception
	 * @author huzi 2018-06-21
	 */
	public List<Map<String, Object>> findAllPage(
			@Param(value="searchUserName")String searchUserName, 
			@Param(value="searchNickName")String searchNickName,
			@Param(value="searchNumber")String searchNumber,
			@Param(value="page")Integer page,
			@Param(value="rows")Integer rows);

	
	/**
	 * 查询手机用户数据的总条数
	 * @param searchUserName
	 * @param searchNickName
	 * @return
	 * @author huzi 2018-06-21
	 */
	public int findAllPageCount(
			@Param(value="searchUserName")String searchUserName, 
			@Param(value="searchNickName")String searchNickName,
			@Param(value="searchNumber")String searchNumber);
	
	
	/**
	 * 查询单条数据
	 * @param id
	 * @return
	 * huangzh 2015-11-12
	 * @throws Exception
	 */
	public Map<String, Object> findMapById(Long uid);

	/**   
	* @Title: findByCondition    
	* @Description: 查询用户信息   
	* @param @param Condition
	* @param @return    设定文件    
	* @throws    
	*/ 
	public List<BeanUser> findByCondition(@Param("param")Map<String, Object> Condition);
	
	
	public Map<String,Object> findByUserId(@Param("userId") Long userId);
	
	
	/**
	 * 禁用或者使用用户
	 * @param ids id
	 * @param type 状态
	 * @param rank 级别
	 * @return
	 * @author huzi 2018-04-23
	 */
	public int upUserStatus(@Param("ids")List<Long> ids,@Param("type")String type);


}