package com.kapphk.pms.service;

import java.util.List;

/**
 * service接口 用于继承
 * @author Folo 2014年10月15日
 */
public interface BaseService<M, T> {
	/**
	 * 分页查找数据
	 * @author Folo 2014年10月15日
	 */
	public List<M> findByPage(M m, int offset, int pageSize) throws Exception;
	
	/**
	 * 分页查找总条数
	 * @author Folo 2014年10月15日
	 */
	public int findByPageCount(M m) throws Exception;
	
	/**
	 * 根据id查询数据
	 * @author Folo 2014年10月15日
	 */
	public M findById(T id) throws Exception;
	
	/**
	 * 加载id为传入id的数据
	 * @author Folo 2014年10月15日
	 */
	public List<M> load(List<T> ids) throws Exception;
	
	/**
	 * 查询所有数据
	 * @author Folo 2014年10月15日
	 */
	public List<M> all() throws Exception;
	
	/**
	 * 查询总条数
	 * @author Folo 2014年10月15日
	 */
	public int count() throws Exception;
	
	/**
	 * 更新数据
	 * @author Folo 2014年10月15日
	 */
	public int update(M m) throws Exception;
	
	/**
	 * 添加数据
	 * @author Folo 2014年10月15日
	 */
	public int insert(M m) throws Exception;
	
	/**
	 * 添加多条数据（直接添加到数据库 不做非空判断）
	 * @author Folo 2014年10月15日
	 */
	public int inserts(List<M> list) throws Exception;
	
	/**
	 * 删除单条数据
	 * @author Folo 2014年10月15日
	 */
	public int delete(T id);
	
	/**
	 * 删除多条数据
	 * @author Folo 2014年10月15日
	 */
	public int deletes(List<T> ids);
}
