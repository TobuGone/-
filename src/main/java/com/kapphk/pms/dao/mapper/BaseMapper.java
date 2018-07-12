package com.kapphk.pms.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * dao操作接口，用于继承
 * @author Folo 2014年10月14日
 */
public interface BaseMapper<M, T> {
	/**
	 * 分页查找数据
	 * @author Folo 2014年10月14日
	 */
	public List<M> findByPage(@Param(value = "param") M m,
			@Param(value = "offset") int offset,
			@Param(value = "pageSize") int pageSize);
	
	/**
	 * 分页查找总条数
	 * @author Folo 2014年10月14日
	 */
	public int findByPageCount(@Param(value = "param") M m);
	
	/**
	 * 根据id查询数据
	 * @author Folo 2014年10月14日
	 */
	public M findById(T id);
	
	/**
	 * 加载id为传入id的数据
	 * @author Folo 2014年10月14日
	 */
	public List<M> load(List<T> ids);
	
	/**
	 * 查询所有数据
	 * @author Folo 2014年10月14日
	 */
	public List<M> all();
	
	/**
	 * 查询总条数
	 * @author Folo 2014年10月14日
	 */
	public int count();
	
	/**
	 * 更新数据
	 * @author Folo 2014年10月14日
	 */
	public int update(M m);
	
	/**
	 * 添加数据
	 * @author Folo 2014年10月14日
	 */
	public int insert(M m);
	
	/**
	 * 添加多条数据（直接添加到数据库 不做非空判断）
	 * @author Folo 2014年10月14日
	 */
	public int inserts(List<M> list);
	
	/**
	 * 删除单条数据
	 * @author Folo 2014年10月14日
	 */
	public int delete(T id);
	
	/**
	 * 删除多条数据
	 * @author Folo 2014年10月14日
	 */
	public int deletes(@Param(value = "ids")List<T> ids);
	
	
	/**   
	* @Title: deleteByEntity    
	* @Description:  根据对象删除数据   
	* @param     设定文件    
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-27 上午9:49:16
	*/
	/**
	 * 删除多条数据
	 * @author zoneyu 2015-4-28
	 */
	public int deleteByEntity(@Param(value = "param") M m);
	
	/**
	 * 通过实体中的条件查询所有
	 * @author zoneyu 2015-4-28
	 */
	public List<M> findAll(@Param(value = "param") M m);
}
