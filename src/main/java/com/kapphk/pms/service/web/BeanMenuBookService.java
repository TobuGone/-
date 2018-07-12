package com.kapphk.pms.service.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.kapphk.pms.bean.BeanMenuBook;
import com.kapphk.pms.service.BaseService;
import com.kapphk.pms.util.Result;

public interface BeanMenuBookService extends BaseService<BeanMenuBook, Long>{

	/**
	 * 查询菜谱列表
	 * @param searchName 菜谱名称
	 * @param page 页数
	 * @param rows 每页显示的行数
	 * @return result
	 * @throws Exception
	 * @author huangzh 2016-01-21
	 */
	public Result searchMenuBookList(String searchName, String recommend,int page, int rows)throws Exception;

	/**
	 * 新增或修改菜谱
	 * @param beanMenuBook 对象
	 * @param firstId 一级分类id
	 * @param secondId 二级分类id
	 * @param addNumber 步骤数量
	 * @param req 
	 * @param file 文件流
	 * @return Result
	 * @throws Exception
	 * @author huangzh 2016-01-21
	 */
	public Result saveMenuBook(BeanMenuBook beanMenuBook,HttpServletRequest req, MultipartFile file,
			List<Map<String, Object>> list,List<Map<String, Object>> clist)throws Exception;

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
	 * 批量删除
	 * @param ids id集合
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-08-14
	 */
	public Result delMenuBook(List<Long> ids)throws Exception;

	/**
	 * 导入菜谱
	 * @param importCategoryNumber
	 * @param req
	 * @param file
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-08-14
	 */
	public Result saveImport(List<Map<String, Object>> clist,
			HttpServletRequest req, MultipartFile file,Long userId)throws Exception;

	/**
	 * 根据食材学名查询菜谱（下拉框）
	 * @param name 食材学名
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-08-14
	 */
	public List<Map<String, Object>> getMenuBookComboxByName(String name,String isAdvert,String recommend)throws Exception;

	/**
	 * 根据审核状态查询菜谱列表
	 * @param searchNickName 用户昵称
	 * @param AuditStatus 审核状态（1、通过，0、不通过，2、待审核）
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-08-14
	 */
	public Result getMenuBookListByAuditStatus(String searchNickName,String searchMenuBookName,
			String auditStatus, int page, int rows)throws Exception;

	/**
	 * 查询用户收藏的菜谱列表
	 * @param searchNickName 用户昵称
	 * @param page 页数
	 * @param rows 每页显示的行数
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-08-14
	 */
	public Result getCollectList(String searchNickName, int page, int rows)throws Exception;

	/**
	 * 菜谱审核通过
	 * @param id 菜谱id
	 * @param categoryNumber 分类次数
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-08-14
	 */
	public Result saveAdopt(Long id, int categoryNumber,
			List<Map<String, Object>> clist)throws Exception;

	/**
	 * 菜谱审核不通过
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-08-14
	 */
	public Result saveNoAdopt(BeanMenuBook beanMenuBook)throws Exception;

	/**
	 * 查询盖头图片列表
	 * @param page 页数
	 * @param rows 每页显示的行数
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-21
	 */
	public Result getAdvertList(int page, int rows)throws Exception;

	/**
	 * 设置或修改盖头图片
	 * @param id 菜谱id
	 * @param menuId 要修改的菜谱id
	 * @param status 状态
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-21
	 */
	public Result saveAdvert(Long id, Long menuId, String status)throws Exception;

	/**
	 * 删除盖头广告
	 * @param id 菜谱id
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-21
	 */
	public Result delAdvert(String ids)throws Exception;

	/**
	 * 推荐产品
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-08-14
	 */
	public Result saveRecommend(Long menuId);

	/**
	 * 推荐的菜谱
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-08-14
	 */
	public Result recommendMenuBook(Long id, String recommend);

	/**
	 * 设为盖头广告
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-08-14
	 */
	public Result setAdvert(Long id, String isAdvert);

	/**
	 * 设为最新
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-08-14
	 */
	public Result setNewTime(Long id,String isNew);

	/**
	 * 更新上传的文件路径
	 * @param beanMenuBook
	 * @param clist 
	 * @return
	 */
	public Result upFilePath(BeanMenuBook beanMenuBook, List<Map<String, Object>> clist);
	
	
	/**
	 * 根据文件名（路径中获取）查询菜谱  
	 * file_path like '%/20180326/%';
	 * @param filePath
	 * @return
	 */
	public List<BeanMenuBook> findByFilePath(String datePath);
	

}
