/**   
* @Title: InterfaceBeanAppUserService.java 
* @Package com.kapphk.pms.service.interFace 
* @Description: TODO(用一句话描述该文件做什么) 
* @author A18ccms A18ccms_gmail_com   
* @date 2016-1-25 上午10:31:01 
* @version V1.0   
*/
package com.kapphk.pms.service.interFace;

import java.util.List;
import java.util.Map;

import com.kapphk.pms.bean.BeanUser;
import com.kapphk.pms.bean.BeanUserMenuCategory;
import com.kapphk.pms.service.BaseService;
import com.kapphk.pms.util.Result;

/**   
 * @ClassName: InterfaceBeanAppUserService    
 * @Description: 用户管理  
 * @author XS  
 * @date 2016-1-25 上午10:31:01  
 * 
 */
public interface InterfaceBeanAppUserService extends BaseService<BeanUser, Long>{
	
	public List<BeanUser> findByCondition(Map<String,Object> Condition);
	
	
	public void insert(BeanUser user,String [] equipments);
	
	//void
	public int update(BeanUser user);
	
	
	public Map<String,Object> findByUserId( Long userId);
	
	public void login(Integer type,String userName,String password,Result rs);
	
	public void register(Integer type,String platform,String uid,String userName,String password,String validCode,String nickName,String logoPath, String cuisineMenuCategoryIds , String flavorMenuCategoryIds,String[]equipments,Result rs);

	/**手机验证码*/
	public void validatePhone(String phone, String validCode, Result rs)throws Exception;

	//inserts
	public void insertss(List<BeanUserMenuCategory> bumcList)throws Exception;

}
