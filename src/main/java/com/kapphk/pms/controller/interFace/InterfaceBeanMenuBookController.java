package com.kapphk.pms.controller.interFace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kapphk.pms.bean.BeanMenuBook;
import com.kapphk.pms.bean.BeanMenuBookComment;
import com.kapphk.pms.bean.BeanMenubookProcedure;
import com.kapphk.pms.bean.BeanUserMenuBook;
import com.kapphk.pms.bean.MenuFirstCategory;
import com.kapphk.pms.bean.MenuSecondCategory;
import com.kapphk.pms.bean.PaginationCondition;
import com.kapphk.pms.service.interFace.InterfaceBeanMenuBookCommentService;
import com.kapphk.pms.service.interFace.InterfaceBeanMenuBookService;
import com.kapphk.pms.service.interFace.InterfaceBeanMenuCategoryService;
import com.kapphk.pms.service.interFace.InterfaceBeanMenubookProcedureService;
import com.kapphk.pms.service.web.BeanMenuBookService;
import com.kapphk.pms.util.Contents;
import com.kapphk.pms.util.DateUtils;
import com.kapphk.pms.util.Result;
import com.kapphk.pms.util.ValidateUtils;

/**   
 * @ClassName: InterfaceBeanMenuBookController    
 * @Description: 菜谱管理   
 * @author XS  
 * @date 2016-1-26 上午10:11:41    
 *         
 */
@RequestMapping("/menubook/")
@RestController
public class InterfaceBeanMenuBookController {

	@Autowired
	private InterfaceBeanMenuBookService menuBookService;
	
	
	@Autowired
	private InterfaceBeanMenuCategoryService menuCategoryService;
	
	@Autowired
	private InterfaceBeanMenubookProcedureService menubookProcedureService;
	
	
	@Autowired
	private InterfaceBeanMenuBookCommentService menuBookCommentService;
	
	
	@Autowired
	private BeanMenuBookService beanMenuBookService;

	
	/**   
	* @Title: uploadMenuBook    
	* @Description:  上传菜谱
	* @param     设定文件    
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-29 上午10:17:21
	*/
	@RequestMapping("uploadMenuBook.aspf")
	public Result uploadMenuBook(HttpServletRequest request, Long userId,MultipartFile file){
		Result rs = new Result();
		try {
			if(ValidateUtils.isBlank(userId) || file == null){
				rs.setStatus(Contents.PARAMS_ERROR);
				return rs;
			}
			rs   = beanMenuBookService.saveImport(null, request, file,userId);
		} catch (Exception e) {
			rs = new Result();
		   e.printStackTrace();
		   rs.setStatus(Contents.ERROR);
		}
		return rs;
	}
	
	
	
	
	/**   
	* @Title: findMenuBook    
	* @Description: 查询用户收藏的菜谱  
	* @param     设定文件    
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-26 上午10:51:26
	*/
	@RequestMapping("findCollectionMenuBook.aspf")
	public Result findMenuBook(Long userId,PaginationCondition pc,Result rs){
		try {
			if(ValidateUtils.isBlank(userId)){
				rs.setStatus(Contents.PARAMS_ERROR);
				return rs;
			}
			Map<String,Object> condition = new HashMap<String, Object>();
			condition.put("userId", userId);
			condition.put("status", 1);
			condition.put("type", 2);//收藏
			List<Map<String, Object>> list = menuBookService.findCollectionByCondition(condition, pc);
			rs.put("menubooks", list != null ? list : new ArrayList<Object>());
		} catch (Exception e) {
		  e.printStackTrace();
		  rs.setStatus(Contents.ERROR);
		}
		return rs;
	}
	
	/**   
	* @Title: findMyMenuBook    
	* @Description:  我的菜谱审
	* @param     设定文件    
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-28 下午4:50:22
	*/
	@RequestMapping("findMyMenuBook.aspf")
	public Result findMyMenuBook(Long userId,Result rs){
		try {
			if(ValidateUtils.isBlank(userId)){
				rs.setStatus(Contents.PARAMS_ERROR);
				return rs;
			}
			Map<String,Object> condition = new HashMap<String, Object>();
			condition.put("status", 1);
			condition.put("uid", userId);
			List<Map<String, Object>> list = menuBookService.findByCondition(condition);
			rs.put("menubooks", list != null ? list : new ArrayList<Object>());
		} catch (Exception e) {
			e.printStackTrace();
			rs.setStatus(Contents.ERROR);
		}
		return rs;
	}
	
	/**   
	* @Title: findAd    
	* @Description: 查询广告 
	* @param     设定文件    
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-26 上午10:51:45
	*/
	@RequestMapping("findAd.aspf")
	public Result findAd(Result rs){
		try {
			Map<String,Object> condition = new HashMap<String, Object>();
			condition.put("isAdvert", 1);
			condition.put("status", 1);
			 condition.put("auditStatus", 1);
			List<Map<String, Object>> list = menuBookService.findByCondition(condition);
			rs.put("ads", list != null ? list : new ArrayList<Object>());
		} catch (Exception e) {
		  e.printStackTrace();
		  rs.setStatus(Contents.ERROR);
		}
		return rs;
	} 
	
	/**   
	* @Title: findMenuCategory    
	* @Description:  查询菜谱  
	* @param     设定文件    
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-27 上午10:42:57
	*/
	@RequestMapping("findMenuCategory.aspf")
	public Result findMenuCategory(Integer pType,Result rs){
		try {
			Map<String,Object> condition = new HashMap<String, Object>();
			if(pType != null){
				if(pType == 1){
					condition.put("cType", "1");
				}else if(pType == 2){
					condition.put("cType", "2");
				}
			}
			List<Map<String, Object>> list = menuCategoryService.findByCondition(condition);
			rs.put("menuCategorys", list != null ? list : new ArrayList<Object>());
		} catch (Exception e) {
			e.printStackTrace();
			rs.setStatus(Contents.ERROR);
		}
		return rs;
	}
	
	
	/**   
	* @Title: findSecondCategory    
	* @Description: 查询菜谱二级分类   
	* @param     设定文件    
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-26 上午11:37:31
	*/
	@RequestMapping("findSecondCategory.aspf")
	public Result findSecondCategory(Integer rows ,String recommend,Result rs){
		try {
			Map<String,Object> condition = new HashMap<String, Object>();
			condition.put("level", "2");
			condition.put("status", "1");
			if(!ValidateUtils.isBlank(recommend)){
				condition.put("recommend", recommend);
			}
			
			List<Map<String,Object>>	list = null;
			if(rows != null && rows > 0){
				PaginationCondition pc = new PaginationCondition();
				pc.setRows(rows);
				list =	menuCategoryService.findPagination(condition, pc);
			}else{
				list =	menuCategoryService.findByCondition(condition);
			}
			rs.put("menuCategorys", list != null ? list : new ArrayList<Object>());
             
		} catch (Exception e) {
			e.printStackTrace();
			rs.setStatus(Contents.ERROR);
		}
		return rs;
	}
	
	
	/**   
	* @Title: findMenuBook    
	* @Description: 查询菜谱 
	* @param    categoryId二级菜谱类别id  ； order排序（ 1=最新、2=点击量、3=推荐）
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-26 下午2:25:07
	*/
	@RequestMapping("findMenuBook.aspf")
	public Result findMenuBook(String categoryId,Integer order,String name,PaginationCondition pc,Result rs){
		try {
			Map<String,Object> condition = new HashMap<String, Object>();
			condition.put("categoryId", categoryId);
			if(order != null){
				if(order == 3){
				 condition.put("recommend", "1");		
				}else{
					condition.put("order", order);		
				}
			}
			condition.put("name", name);
	        condition.put("status", "1");
	        condition.put("auditStatus", 1);
		    List<Map<String,Object>> list =	menuBookService.findMenuBookPagination(condition, pc);
			rs.put("menubooks", list != null ? list : new ArrayList<Object>());
		} catch (Exception e) {
			e.printStackTrace();
			rs.setStatus(Contents.ERROR);
		}
		return rs;
	}
	
	
	/**   
	* @Title: findTowMenuCategory    
	* @Description: 查询一级、二级菜谱关系 
	* @param     设定文件    
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-26 下午4:13:53
	*/
	@RequestMapping("findTowMenuCategory.aspf")
	public Result findTowMenuCategory(Long categoryId,Integer order,PaginationCondition pc,Result rs){
		try {

		    List<MenuFirstCategory> list =	menuCategoryService.findTowMenuCategory(categoryId, pc);
		    if(!CollectionUtils.isEmpty(list)){
		    	for(MenuFirstCategory fc:list){
		               List<MenuSecondCategory> scList = fc.getSecondCategorieList();
		               if(!CollectionUtils.isEmpty(scList)){
		            	   for(int i = 0 ;i < scList.size() ;i++){
		            		   MenuSecondCategory sc = scList.get(i);
		            		   if(sc.getMscId() == null || sc.getMscId() == 0){
		            			   scList.remove(i);
		            			   i--;
		            		   }
		            	   }
		               }
		    	}
		    }
			rs.put("menucategorys", list != null ? list : new ArrayList<Object>());
		} catch (Exception e) {
			e.printStackTrace();
			rs.setStatus(Contents.ERROR);
		}
		return rs;
	}
	
	
	/**   
	* @Title: findMenuBookDetails   
	* @Description:  查询菜谱详情   (微信分享的菜谱展示)
	* @param     设定文件    
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-26 下午4:13:53
	*/
	@RequestMapping("findMenuBookDetails.aspf")
	public Result findMenuBookDetails(Long menuBookId,Long userId,Result rs){
		try {
			if(ValidateUtils.isBlank(menuBookId)){
				rs.setStatus(Contents.PARAMS_ERROR);
				return rs;
			}
			//查询菜谱
			Map<String,Object> menuBook = menuBookService.updateMenuBookById(menuBookId,userId);
			rs.put("menuBook", menuBook != null ? menuBook: new HashMap<String, Object>());
			//查询菜的制作步骤
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("menuId", menuBookId);
			List<BeanMenubookProcedure> list = menubookProcedureService.findByCondition(condition);
			rs.put("menubooksteps", list != null ? list : new ArrayList<Object>());
			//查询菜谱评论
			condition.clear();
			condition.put("menuBookId", menuBookId);
			List<Map<String,Object>> list2 = menuBookCommentService.findByCondition(condition);
			rs.put("menubookcomments", list2 != null ?list2: new ArrayList<Object>());
		} catch (Exception e) {
			e.printStackTrace();
			rs.setStatus(Contents.ERROR);
		}
		return rs;
	}
	
	
	
	/**   
	* @Title: saveComments    
	* @Description:  菜谱评论   
	* @param     设定文件    
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-26 下午5:20:51
	*/
	@RequestMapping("saveComments.aspf")
	public Result saveComments(BeanMenuBookComment mbc ,Result rs){
		try {
			if(ValidateUtils.isBlank(mbc.getMenuBookId()) || ValidateUtils.isBlank(mbc.getUserId())
			 ||ValidateUtils.isBlank(mbc.getContent())){
				rs.setStatus(Contents.PARAMS_ERROR);
				return rs;
			}
			mbc.setCreateTime(DateUtils.getLocalDate());
			menuBookCommentService.insert(mbc);
		} catch (Exception e) {
			 e.printStackTrace();
			 //rs.setStatus(Contents.ERROR);
		}
		return rs;
	}
	
	/**   
	* @Title: saveCollection    
	* @Description:保存用户收藏菜谱   
	* @param     设定文件    
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-26 下午5:28:11
	*/
	@RequestMapping("saveCollection.aspf")
	public Result saveCollection(BeanUserMenuBook userMenuBook ,Result rs){
		try {
			if(ValidateUtils.isBlank(userMenuBook.getUserId()) || ValidateUtils.isBlank(userMenuBook.getMenuBookId())){
				rs.setStatus(Contents.PARAMS_ERROR);
				return rs;
			}
			userMenuBook.setCreateTime(DateUtils.getLocalDate());
			userMenuBook.setType("2");
			menuBookService.saveCollection(userMenuBook);
			rs.put("id", userMenuBook.getId());
		} catch (Exception e) {
            e.printStackTrace();
            rs.setStatus(Contents.ERROR);
		}
		return rs;
	}
	
	/**
	 * 取消收藏菜谱
	 * Lotus 2016-04-11
	 */
	@RequestMapping("cancelCollection.aspf")
	public Result cancelCollection(BeanUserMenuBook userMenuBook ,Result rs){
		menuBookService.cancelCollection(userMenuBook, rs);
		return rs;
	}
	/**   
	* @Title: saveCollection    
	* @Description:保存用户收藏菜谱   
	* @param     设定文件    
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-26 下午5:28:11
	*/
	@RequestMapping("deleteCollection.aspf")
	public Result deleteCollection(Long userCollectionId ,Result rs){
		try {
			if( ValidateUtils.isBlank(userCollectionId)){
				rs.setStatus(Contents.PARAMS_ERROR);
				return rs;
			}
			 menuBookService.deleteCollection(userCollectionId);
		} catch (Exception e) {
            e.printStackTrace();
            rs.setStatus(Contents.ERROR);
		}
		return rs;
	}
	
	
	/**
	 * 搜索菜谱
	 * @param menubookName
	 * @param rs
	 * @return
	 */
	@RequestMapping("searchMenuBook.aspf")
	public Result searchMenuBook(String menubookName,Integer pages,Integer pageSize, Result rs){
		
		rs = menuBookService.searchMenuBook(menubookName, pages, pageSize);
		
		return rs;
	}
	
	/**
	 * 保存用户自定义菜谱
	 * Lotus 2016-04-13
	 */
	@RequestMapping("saveCustomMenuBook.aspf")
	public Result saveCustomMenuBook(BeanMenuBook menuBook,Result rs){
			try {
				menuBookService.saveCustomMenuBook(menuBook, rs);
			} catch (Exception e) {
				e.printStackTrace();
				rs.setStatus(Contents.ERROR);
			}	
		return rs;
	}
	
	/**
	 * 删除用户菜谱文件
	 * Lotus 2016-04-13
	 * @throws Exception 
	 */
	@RequestMapping("deleteUserMenuBookFile.aspf")
	public Result deleteUserMenuBookFile(BeanMenuBook menuBook,Result rs){
			try {
				menuBookService.deleteUserMenuBookFile(menuBook,rs);
			} catch (Exception e) {
				e.printStackTrace();
				rs.setStatus(Contents.ERROR);
			}	
		return rs;
	}
	
	/**
	 * 下载菜谱文件
	 * Lotus 2016-04-13
	 * @throws Exception 
	 */
	@RequestMapping("downloadMenuBookFile.aspf")
	public Result downloadMenuBookFile(BeanMenuBook menuBook,Result rs){
			try {
				menuBookService.downloadMenuBookFile(menuBook,rs);
			} catch (Exception e) {
				e.printStackTrace();
				rs.setStatus(Contents.ERROR);
			}	
		return rs;
	}
	
	/**
	 * 菜谱置顶
	 * Lotus 2016-04-19
	 */
	@RequestMapping("topMenuBook.aspf")
	public Result topMenuBook(Long id,Long uid,Result rs){
		try {
			BeanUserMenuBook menuBook=new BeanUserMenuBook();
			menuBook.setMenuBookId(id);
			menuBook.setUserId(uid);
			menuBookService.topMenuBook(menuBook,rs);
		} catch (Exception e) {
			e.printStackTrace();
			rs.setStatus(Contents.ERROR);
		}	
	return rs;
	}
	
	/**
	 * 获取菜谱审核记录
	 * Lotus 2016-04-26
	 */
	@RequestMapping("getMenuBookAudit.aspf")
	public Result getMenuBookAudit(Long uid,Result rs){
		try {
			menuBookService.getMenuBookAudit(uid,rs);
		} catch (Exception e) {
			e.printStackTrace();
			rs.setStatus(Contents.ERROR);
		}
		return rs;
	}
	
	
}
