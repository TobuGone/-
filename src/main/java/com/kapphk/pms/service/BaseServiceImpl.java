package com.kapphk.pms.service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.druid.support.json.JSONUtils;
import com.kapphk.pms.bean.BeanAppIOSUpdate;
import com.kapphk.pms.dao.mapper.BaseMapper;
import com.kapphk.pms.util.ValidateUtils;


/**
 * service接口实现类 用于继承
 * @author Folo 2014年10月15日
 */
public abstract class BaseServiceImpl<M, T>{
	
	static final int OK = 10001;
	static final int ERROR = 20001;
	static final int NOT_FOUND = 20003;
	static final int PARAMS_ERROR= 21001;
	static final int TOKEN_FAILURE = 10001;
	static final int VALUE_EMPTY = 20006;
	static final int EXIST = 20007;
	static final int NOT_EXIST = 20009;
	static final int STOP = 20008;
	static final int CUS_ERROR = 20005;
	
	/**
	 * Mapper用于继承的类对象
	 */
	private BaseMapper<M, T> bm;
	
	public void setMapper(BaseMapper<M, T> mapper){
		this.bm = mapper;
	}
	
	public BaseMapper<M, T> getMapper(){
		if(null == bm) init();
		return bm;
	}
	
	/**
	 * 初始化
	 * @author Folo 2014年10月15日
	 */
	public abstract void init();
	
	/**
	 * 分页查找数据
	 * @author Folo 2014年10月15日
	 */
	public List<M> findByPage(M m, int offset, int pageSize){
		return getMapper().findByPage(m, offset, pageSize);
	}
	
	/**
	 * 分页查找总条数
	 * @author Folo 2014年10月15日
	 */
	public int findByPageCount(M m){
		return getMapper().findByPageCount(m);
	}
	
	/**
	 * 根据id查询数据
	 * @author Folo 2014年10月15日
	 */
	public M findById(T id){
		return getMapper().findById(id);
	}
	
	/**
	 * 加载id为传入id的数据
	 * @author Folo 2014年10月15日
	 */
	public List<M> load(List<T> ids){
		return getMapper().load(ids);
	}
	
	/**
	 * 查询所有数据
	 * @author Folo 2014年10月15日
	 */
	public List<M> all(){
		return getMapper().all();
	}
	
	/**
	 * 查询总条数
	 * @author Folo 2014年10月15日
	 */
	public int count(){
		return getMapper().count();
	}
	
	/**
	 * 更新数据
	 * @author Folo 2014年10月15日
	 */
	public int update(M m){
		return getMapper().update(m);
	}
	
	/**
	 * 添加数据
	 * @author Folo 2014年10月15日
	 */
	public int insert(M m){
		return getMapper().insert(m);
	}
	
	/**
	 * 添加多条数据（直接添加到数据库 不做非空判断）
	 * @author Folo 2014年10月15日
	 */
	public int inserts(List<M> list){
		return getMapper().inserts(list);
	}
	
	/**
	 * 删除单条数据
	 * @author Folo 2014年10月15日
	 */
	public int delete(T id){
		return getMapper().delete(id);
	}
	
	/**
	 * 删除多条数据
	 * @author Folo 2014年10月15日
	 */
	public int deletes(List<T> ids){
		return getMapper().deletes(ids);
	}
	
	/**
	 * 拼结返回数据
	 * @param b
	 * @param msg
	 */
	public String writeResult(boolean b,String msg){
		Map<String,Object> map = new HashMap<String, Object>() ;
		map.put("suc", b) ;
		map.put("msg", msg) ;
		return JSONUtils.toJSONString(map) ;
	}
	
	/**
	 * 是否过滤,true为过滤，false为不过滤
	 * @throws Exception 
	 */
	public boolean isFilter(HttpServletRequest request) throws Exception{
		Properties prop = new Properties() ;
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("prop.properties") ;
		prop.load(in) ;
		Object obj = prop.get("filter") ;
		if(!ValidateUtils.isBlank(obj)){
			Object name = request.getSession().getAttribute("userName") ;
			if("root".equals(name)){
				return false ;
			}else{
				if("0".equals(String.valueOf(obj))){ //表示不过滤
					return false ;
				}else if("1".equals(String.valueOf(obj))){//表示过滤
					return true ;
				}else{
					return false ;
				}
			}
		}
		return false ;
	}

	/**
	 * @param appIOSUpdate
	 * @return
	 */
	public int upAppIOS(BeanAppIOSUpdate appIOSUpdate) {
		// TODO Auto-generated method stub
		return 0;
	}
}
