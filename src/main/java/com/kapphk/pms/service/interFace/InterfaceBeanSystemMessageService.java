package com.kapphk.pms.service.interFace;

import java.util.List;
import java.util.Map;

import com.kapphk.pms.bean.BeanMessagePush;
import com.kapphk.pms.bean.PaginationCondition;
import com.kapphk.pms.util.Result;

/**   
 * @ClassName: InterfaceBeanSystemMessageService    
 * @Description: 系统消息管理 
 * @author XS  
 * @date 2016-1-28 下午5:13:10    
 *         
 */
public interface InterfaceBeanSystemMessageService {
	
	
	public List<Map<String, Object>> findPagination(Map<String,Object> condition,PaginationCondition pc);
	
	public BeanMessagePush findById(Long id);
	
	
	/**
	 * PMS 固件更新(旧方式)
	 * @param rs
	 * @throws Exception
	 */
	public void getEquipmentUpdateOld(Result rs)throws Exception;

	
	/**
	 * PMS 固件更新
	 * @param rs
	 * @throws Exception
	 */
	public void getEquipmentUpdate(Result rs)throws Exception;
	/**
	 * Android app更新
	 * 2018-01-29  huzi
	 * @param rs
	 * @throws Exception
	 */
	public void getAppAndroidUpdate(Result rs)throws Exception;
	
	/**
	 * ios app更新
	 * 2018-04-24  huzi
	 * @param rs
	 * @throws Exception
	 */
	public void getAppIOSUpdate(Result rs)throws Exception;

}
