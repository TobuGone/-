package com.kapphk.pms.service.interFace;

import java.util.List;
import java.util.Map;

import com.kapphk.pms.bean.BeanMenuBook;
import com.kapphk.pms.bean.BeanMenuBookShare;
import com.kapphk.pms.bean.BeanUserMenuBook;
import com.kapphk.pms.bean.PaginationCondition;
import com.kapphk.pms.util.Result;

public interface InterfaceBeanMenuBookService {
	
	
	
    public List<Map<String,Object>> findByCondition(Map<String,Object> condition );
    
    /**
     * 查询菜谱详情
     * 修改于：2018-03-22  huzi
	 * 修改内容：  findMenuBookById 改为  updateMenuBookById
     * @param id
     * @param userId
     * @return
     */
    public Map<String,Object> updateMenuBookById(Long id,Long userId);
    
    
	/**   
	* @Title: findCollectionByCondition    
	* @Description:  查询我收藏的菜谱   
	* @param     设定文件    
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-26 下午1:53:48
	*/
	public List<Map<String, Object>> findCollectionByCondition(Map<String, Object> condition, PaginationCondition pc);
	
	/**   
	* @Title: findMenuBookPagination    
	* @Description: 分页查询菜谱   
	* @param     设定文件    
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-26 下午1:54:51
	*/
	public List<Map<String,Object>> findMenuBookPagination(Map<String, Object> condition, PaginationCondition pc);
	
	
	/**   
	* @Title: saveCollection    
	* @Description:  保存用户收藏菜谱   
	* @param     设定文件    
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-26 下午5:25:31
	*/
	public void saveCollection(BeanUserMenuBook userMenuBook);
	
	/**   
	* @Title: cancelCollection    
	* @Description:  取消用户收藏菜谱   
	* @param     设定文件    
	* @return     返回类型    
	* @author Lotus
	* @date 2016-4-11
	*/
	public void cancelCollection(BeanUserMenuBook userMenuBook, Result rs);
	
	/**   
	* @Title: deleteCollection    
	* @Description: 删除我的收藏  
	* @param     设定文件    
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-27 下午4:55:46
	*/
	public void deleteCollection(Long userCollectionId);

	/**
	 * 搜索菜谱
	 * @param menubookName
	 * @param rs
	 * @return
	 */
	public Result searchMenuBook(String menubookName, Integer pages,
			Integer pageSize);

	/**
	 * 保存用户自定义菜单
	 * Lotus 2016-04-13
	 */
	public void saveCustomMenuBook(BeanMenuBook menuBook, Result rs);

	/**
	 * 删除用户菜谱文件
	 * Lotus 2016-04-13
	 */
	public void deleteUserMenuBookFile(BeanMenuBook userMenuBook, Result rs)throws Exception;

	/**
	 * 解析PMS文件,保存到菜谱表及菜谱步骤表
	 * Lotus 2016-04-18
	 */
	public void saveCustomMenuBookByPath(Long userId, String fileName,Long type, Result rs)throws Exception;

	/**
	 * 下载到菜谱表
	 * Lotus 2016-04-19
	 */
	public void downloadMenuBookFile(BeanMenuBook menuBook, Result rs)throws Exception;

	/**
	 * 菜谱置顶
	 * Lotus 2016-04-19
	 */
	public void topMenuBook(BeanUserMenuBook menuBook, Result rs)throws Exception;

	/**
	 * 获取审核信息
	 * Lotus 2016-04-26
	 */
	public void getMenuBookAudit(Long uid, Result rs)throws Exception;
	
	/**
	 * 解析PMS临时文件,保存到菜谱表及菜谱步骤表
	 * 
	 */
	public void saveFileTemporaryh(Long userId, String fileName, Result rs)throws Exception;
	
	/**
	 * 根据所分享菜谱名查询
	 * huzi 2018-05-18
	 */
	public BeanMenuBookShare findMenuBookShareByName(String name);

	/**
	 * 解析PMS文件,保存到菜谱表及菜谱步骤表(分享菜谱)
	 * @param userId
	 * @param fileName
	 * @param type
	 * @param rs
	 * @throws Exception
	 */
	public void saveCustomMenuBookShareByPath(Long userId, String fileName, Result rs) throws Exception;

}
