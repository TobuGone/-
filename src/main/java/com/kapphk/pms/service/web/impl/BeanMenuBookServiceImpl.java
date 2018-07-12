package com.kapphk.pms.service.web.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kapphk.pms.bean.BeanMenuBook;
import com.kapphk.pms.bean.BeanMenuBookAudit;
import com.kapphk.pms.bean.BeanMenubookCategory;
import com.kapphk.pms.bean.BeanMenubookProcedure;
import com.kapphk.pms.dao.mapper.BeanMenuBookAuditMapper;
import com.kapphk.pms.dao.mapper.BeanMenuBookMapper;
import com.kapphk.pms.dao.mapper.BeanMenubookCategoryMapper;
import com.kapphk.pms.dao.mapper.BeanMenubookProcedureMapper;
import com.kapphk.pms.service.BaseServiceImpl;
import com.kapphk.pms.service.web.BeanMenuBookService;
import com.kapphk.pms.util.Contents;
import com.kapphk.pms.util.DataUtils;
import com.kapphk.pms.util.DateUtils;
import com.kapphk.pms.util.FileUploadUtils;
import com.kapphk.pms.util.PmsFile;
import com.kapphk.pms.util.PmsFile.TimeNode;
import com.kapphk.pms.util.Result;
import com.kapphk.pms.util.ValidateUtils;
@Service("beanMenuBookService")
public class BeanMenuBookServiceImpl extends BaseServiceImpl<BeanMenuBook, Long>implements BeanMenuBookService {

	@Autowired
	private BeanMenuBookMapper menuBookMapper;
	
	@Autowired
	private BeanMenubookProcedureMapper procedureMapper;
	
	@Autowired
	private BeanMenubookCategoryMapper menubookCategoryMapper;
	
	@Autowired
	private BeanMenuBookAuditMapper menuBookAuditMapper;
	
	@Override
	public void init() {
		super.setMapper(menuBookMapper);
	}
	
	
	/**
	 * 查询盖头图片列表
	 * @param page 页数
	 * @param rows 每页显示的行数
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-21
	 */
	@Override
	public Result getAdvertList(int page, int rows) throws Exception {
		Result rs = new Result();
		
		BeanMenuBook menuBook = new BeanMenuBook();
		menuBook.setIsAdvert("1");
		
		
		List<BeanMenuBook> list = menuBookMapper.findByPage(menuBook, (page-1)*rows, rows);
		
		int count = menuBookMapper.findByPageCount(menuBook);
		
		rs.put("total", count);
		rs.put("rows", list);
		
		return rs;
	}
	

	/**
	 * 设置或修改盖头图片
	 * @param id 菜谱id
	 * @param menuId 要修改的菜谱id
	 * @param status 状态
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-21
	 */
	@Override
	public Result saveAdvert(Long id, Long menuId, String status)
			throws Exception {
		Result rs = new Result();
		
		//新增设置
		if(ValidateUtils.isBlank(id)){
			BeanMenuBook beanMenuBook = menuBookMapper.findById(menuId);
			
			if(beanMenuBook == null){
				rs.setStatus(Contents.ERROR);
				rs.setMsg("数据有误，请稍后再试");
				return rs;
			}
			beanMenuBook.setIsAdvert("1");
			beanMenuBook.setStatus(Integer.parseInt(status));
			
			menuBookMapper.update(beanMenuBook);
		}
		//修改
		else{
			if(menuId.equals(id)){
				BeanMenuBook beanMenuBook = menuBookMapper.findById(menuId);
				
				if(beanMenuBook == null){
					rs.setStatus(Contents.ERROR);
					rs.setMsg("数据有误，请稍后再试");
					return rs;
				}
				beanMenuBook.setIsAdvert("1");
				beanMenuBook.setStatus(Integer.parseInt(status));
				
				menuBookMapper.update(beanMenuBook);
			}else{
				BeanMenuBook beanMenuBook = menuBookMapper.findById(menuId);
				
				if(beanMenuBook == null){
					rs.setStatus(Contents.ERROR);
					rs.setMsg("数据有误，请稍后再试");
					return rs;
				}
				beanMenuBook.setIsAdvert("1");
				beanMenuBook.setStatus(Integer.parseInt(status));
				
				menuBookMapper.update(beanMenuBook);
				
				BeanMenuBook menuBook = menuBookMapper.findById(id);
				
				menuBook.setIsAdvert("0");
				menuBook.setStatus(1);
				menuBookMapper.update(menuBook);
				
			}
		}
		
		return rs;
	}
	
	

	/**
	 * 删除盖头广告
	 * @param id 菜谱id
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-21
	 */
	@Override
	public Result delAdvert(String ids) throws Exception {
		Result rs = new Result();
		
		String[] menuids = ids.split(",");
		
		for(String id:menuids){
			BeanMenuBook menuBook = menuBookMapper.findById(Long.parseLong(id));
			menuBook.setIsAdvert("0");
			menuBook.setStatus(1);
			menuBookMapper.update(menuBook);
		}
		
		
		return rs;
	}
	
	
	/**
	 * 查询菜谱列表
	 * @param searchName 菜谱名称
	 * @param page 页数
	 * @param rows 每页显示的行数
	 * @return result
	 * @throws Exception
	 * @author huangzh 2016-01-21
	 */
	@Override
	public Result searchMenuBookList(String searchName,String recommend, int page, int rows)
			throws Exception {
		Result rs = new Result();
		
		List<Map<String, Object>> list = menuBookMapper.findMapPage(searchName,recommend,(page-1)*rows,rows);
		
		int count = menuBookMapper.findMapPageCount(searchName,recommend);
		
		rs.put("total", count);
		rs.put("rows", list);
		
		return rs;
	}

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
	@Override
	public Result saveMenuBook(BeanMenuBook beanMenuBook, HttpServletRequest req, MultipartFile file,
			List<Map<String, Object>> list,List<Map<String, Object>> clist) throws Exception {
		Result rs = new Result();
		
		//新增
		if(ValidateUtils.isBlank(beanMenuBook.getId())){
			BeanMenuBook book = new BeanMenuBook();
			book.setName(beanMenuBook.getName());
			book.setUid(beanMenuBook.getUid());
			
			int count = menuBookMapper.findByPageCount(book);
			
			if(count != 0){
				rs.setStatus(Contents.EXIST);
				rs.setMsg("当前用户所添加的菜谱名称重复，请重新输入");
				return rs;
			}
			
			
			//上传图片
			if(!file.isEmpty()){
				byte[] bytes = file.getBytes() ;
				String logPath = FileUploadUtils.uploadFile(bytes,"upload/menubook","2",req) ;
				beanMenuBook.setLogoPath("/"+logPath);
			}
			
			beanMenuBook.setCreateTime(DateUtils.getLocalDate());
			beanMenuBook.setAuditStatus("1");//后台添加的菜谱默认：审核通过
			
			menuBookMapper.insert(beanMenuBook);
			
			
			for(Map<String, Object> map:list){
				
				BeanMenubookProcedure procedure = new BeanMenubookProcedure();
				
				procedure.setMenuId(beanMenuBook.getId());
				procedure.setMinute(map.get("minute").toString());
				procedure.setSecond(map.get("second").toString());
				procedure.setDescribes(map.get("describe").toString());
				procedure.setFoods(map.get("foods").toString());
				procedure.setCreateTime(DateUtils.getLocalDate());
				
				procedureMapper.insert(procedure);
			}
			
			//保存类别
			for(Map<String, Object> map:clist){
				//一级
				BeanMenubookCategory firstcategory = new BeanMenubookCategory();
				firstcategory.setMenuId(beanMenuBook.getId());
				firstcategory.setCategoryId(Long.parseLong(map.get("firstId").toString()));
				firstcategory.setCreateTime(DateUtils.getLocalDate());
				
				menubookCategoryMapper.insert(firstcategory);
				
				//二级
				BeanMenubookCategory secondcategory = new BeanMenubookCategory();
				secondcategory.setMenuId(beanMenuBook.getId());
				secondcategory.setCategoryId(Long.parseLong(map.get("secondId").toString()));
				secondcategory.setCreateTime(DateUtils.getLocalDate());
				
				menubookCategoryMapper.insert(secondcategory);
			}
			
		}
		//修改
		else{
			BeanMenuBook book = new BeanMenuBook();
			book.setName(beanMenuBook.getName());
			book.setUid(beanMenuBook.getUid());
			
			List<BeanMenuBook> books = menuBookMapper.findByPage(book, 0, 1);
			
			if(ValidateUtils.isEmptyForCollection(books) || books.get(0).getId().equals(beanMenuBook.getId())){
				
				//上传图片
				if(!file.isEmpty()){
					byte[] bytes = file.getBytes() ;
					String logPath = FileUploadUtils.uploadFile(bytes,"upload/menubook","2",req) ;
					beanMenuBook.setLogoPath("/"+logPath);
				}
				
				//beanMenuBook.setCreateTime(DateUtils.getLocalDate());
				
				menuBookMapper.update(beanMenuBook);
				
				//修改步骤，先删除在重新添加
				procedureMapper.deleteByMenuId(beanMenuBook.getId());
				
				for(Map<String, Object> map:list){
					
					BeanMenubookProcedure procedure = new BeanMenubookProcedure();
					
					procedure.setMenuId(beanMenuBook.getId());
					procedure.setMinute(map.get("minute").toString());
					procedure.setSecond(map.get("second").toString());
					procedure.setDescribes(map.get("describe").toString());
					procedure.setFoods(map.get("foods").toString());
					procedure.setCreateTime(DateUtils.getLocalDate());
					
					procedureMapper.insert(procedure);
				}
				
				//修改分类，先删除在重新添加
				menubookCategoryMapper.deleteByMenuId(beanMenuBook.getId());
				
				for(Map<String, Object> map:clist){
					//一级
					BeanMenubookCategory firstcategory = new BeanMenubookCategory();
					firstcategory.setMenuId(beanMenuBook.getId());
					firstcategory.setCategoryId(Long.parseLong(map.get("firstId").toString()));
					firstcategory.setCreateTime(DateUtils.getLocalDate());
					
					menubookCategoryMapper.insert(firstcategory);
					
					//二级
					BeanMenubookCategory secondcategory = new BeanMenubookCategory();
					secondcategory.setMenuId(beanMenuBook.getId());
					secondcategory.setCategoryId(Long.parseLong(map.get("secondId").toString()));
					secondcategory.setCreateTime(DateUtils.getLocalDate());
					
					menubookCategoryMapper.insert(secondcategory);
				}
				
				
			}else{
				rs.setStatus(Contents.EXIST);
				rs.setMsg("当前用户所修改的菜谱名称重复，请重新输入");
				return rs;
			}
			
		}
		
		return rs;
	}

	
	/**
	 * 查询单条数据
	 * @param id
	 * @return
	 * huangzh 2015-08-14
	 * @throws Exception
	 */
	@Override
	public BeanMenuBook findMenuBookById(Long id) {
		return menuBookMapper.findMenuBookById(id);
	}

	/**
	 * 根据菜单id，查询对应的分类id集合
	 *  huangzh 2015-08-14
	 * @throws Exception
	 */
	@Override
	public List<Map<String, Object>> findCategoryIds(Long id) {
		return menuBookMapper.findCategoryIds(id);
	}

	/**
	 * 批量删除
	 * @param ids id集合
	 * @return
	 * @throws Exception
	 * @author huzi 2018-06-19
	 */
	@Override
	public Result delMenuBook(List<Long> ids) throws Exception {
		Result rs = new Result();
		String pmsPath;
		String logoPath;
		String path = FileUploadUtils.class.getClassLoader().getResource("../..").getPath();//项目路径
		
		if(!ValidateUtils.isEmptyForCollection(ids)){
			
			for (Long id : ids) {
				procedureMapper.deleteByMenuId(id);					//删除菜谱对应的步骤
				
				if (id != null) {
					
					BeanMenuBook mb = menuBookMapper.findById(id);	
					
					pmsPath = path + mb.getFilePath();				//删除菜谱对应的.pms文件
					File pmsFile = new File(pmsPath);
					if (pmsFile.exists()) { 
						pmsFile.delete();					
					}
					
					logoPath = path + mb.getLogoPath();				//删除菜谱对应的图片文件
					File logFile = new File(logoPath);
					if (logFile.exists()) { 
						logFile.delete();					
					}
				}
			}
			int count = menuBookMapper.deletes(ids);				//删除菜谱
			rs.put("count", count);
			return rs;
		}
		
		rs.setStatus(Contents.PARAMS_ERROR);
		rs.setMsg("参数错误!!");
		return rs;
	}

	
	/**
	 * 导入菜谱
	 * @param importCategoryNumber
	 * @param req
	 * @param file
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-08-14
	 */
	@Override
	public Result saveImport(List<Map<String, Object>> clist, HttpServletRequest req, MultipartFile mfile,Long userId) throws Exception {
		
		Result rs = new Result();
		
		//上传文件
		if(!mfile.isEmpty()){
			Result rsPath = FileUploadUtils.commonFileUploadVideoByByte(req,"/upload/pms");
			
			String path = (String) rsPath.getData().get("savePath");
			
			String fileName = mfile.getOriginalFilename();
			
			fileName = fileName.substring(0,fileName.lastIndexOf("."));
			
			
			BeanMenuBook book = new BeanMenuBook();
			book.setName(fileName);
			book.setUid(userId);

			int count = menuBookMapper.findByPageCount(book);
			
			if(count != 0){
				rs.setStatus(Contents.EXIST);
				rs.setMsg("当前用户所导入的菜谱名称重复，请重新输入");
				return rs;
				
			}
			
			//new 一个菜谱对象
			BeanMenuBook beanMenuBook = new BeanMenuBook();
			beanMenuBook.setName(fileName);//菜谱名称为上传的文件名
			beanMenuBook.setUid(userId);//后台上传用户默认id为-1
			
			
			File file = new File(path);
	 		
	 		FileInputStream fin = new FileInputStream(file);
	 		
	 	    byte[] bytes = new byte[(int) file.length()];
	 	   
	 	    fin.read(bytes);
	 	   
	 	    fin.close();
	 	    
	 	    PmsFile pmsFile = new PmsFile(bytes);
	 	    
	 	    beanMenuBook.setDescribes(pmsFile.text);//菜谱描述
	 	   
	 	   	//菜谱封面图片
	 	   	if(pmsFile.bufJPG.length >0){

		 	   String imgPath = "/upload/pms/"+DateUtils.getLocalDate("yyyyMMdd");
		 	   
		 	   beanMenuBook.setLogoPath(getImagePath(pmsFile.bufJPG,imgPath)); 
	 	   	}
	 	   
	 	    beanMenuBook.setCreateTime(DateUtils.getLocalDate());
	 	    
	 	    beanMenuBook.setFoods(getFoods(pmsFile.listTimeNode));//获取全部的食材
	 	    
	 	    beanMenuBook.setAuditStatus("1");//后台添加的菜谱默认：审核通过
	 	  
	 	  	menuBookMapper.insert(beanMenuBook);
	 	  
		 	//保存类别
			for(Map<String, Object> map:clist){
				//一级
				BeanMenubookCategory firstcategory = new BeanMenubookCategory();
				firstcategory.setMenuId(beanMenuBook.getId());
				firstcategory.setCategoryId(Long.parseLong(map.get("firstId").toString()));
				firstcategory.setCreateTime(DateUtils.getLocalDate());
				
				menubookCategoryMapper.insert(firstcategory);
				
				//二级
				BeanMenubookCategory secondcategory = new BeanMenubookCategory();
				secondcategory.setMenuId(beanMenuBook.getId());
				secondcategory.setCategoryId(Long.parseLong(map.get("secondId").toString()));
				secondcategory.setCreateTime(DateUtils.getLocalDate());
				
				menubookCategoryMapper.insert(secondcategory);
			}
			
				
	 	    //解析时间节点
			
		   List<TimeNode> listTimeNode = pmsFile.listTimeNode;
		   
		   List<BeanMenubookProcedure> procedures = new ArrayList<BeanMenubookProcedure>(); 
		   
		   //循环记录每个节点步骤，封装成对象集合   
	 	   for(TimeNode node:listTimeNode){
	 		   
	 		  BeanMenubookProcedure beanMenubookProcedure = new BeanMenubookProcedure();
	 		  beanMenubookProcedure.setMenuId(beanMenuBook.getId());
	 		   
	 		  int minute = (node.Times)/60;
	 		  int second = (node.Times)%60;
	 		  
	 		  beanMenubookProcedure.setMinute(String.valueOf(minute));//分钟
	 		  beanMenubookProcedure.setSecond(String.valueOf(second));//秒
	 		  beanMenubookProcedure.setDescribes(node.Tips);//步骤描述
	 		   
	 		  String foods = "";
	 		  
	 		  for(int i = 0;i<node.FoodNames.length;i++){
	 			  
	 			   if(node.FoodNames[i] == null || node.FoodNames[i].equals("")){
	 				   continue;
	 			   }
	 			  
	 			  foods += node.FoodNames[i]+""+node.FoodWgts[i]+"g；";
	 		  }
	 		  
	 		  beanMenubookProcedure.setFoods(foods);
	 		  
	 		  beanMenubookProcedure.setCreateTime(DateUtils.getLocalDate());
	 		 
	 		  procedures.add(beanMenubookProcedure);
	 		
	 	   }
	 	   procedureMapper.inserts(procedures);
		
		}
		
		return rs;
	}
	
	
	//获取全部食材
	public String getFoods(List<TimeNode> listTimeNode){
	   Set<String> set=new HashSet<String>();
	 	   
 	   for(TimeNode node:listTimeNode){
 		   
 		   for(int i = 0;i<node.FoodNames.length;i++){
 			   
 			   if(node.FoodNames[i] == null || node.FoodNames[i].equals("")){
 				   continue;
 			   }
 			  
 			  set.add(node.FoodNames[i]);
 		   }
 		  
 	   }
	 	   
 	   String foodsAll = "";
	 	   
 	   for(String foodsName:set){
	 		   
 		   int weight = 0;
	 		   
 		   for(TimeNode node:listTimeNode){
	 	 		   
 	 		   for(int i = 0;i<node.FoodNames.length;i++){
	 	 			   
 	 			  if(node.FoodNames[i] == null || node.FoodNames[i].equals("")){
 	 				   continue;
 	 			  }
 	 			  
 	 			  if(node.FoodNames[i].equals(foodsName)){
	 	 				weight += node.FoodWgts[i];
 	 			  }
 	 		   }
 	 	   }
 		  foodsAll += foodsName+""+weight+"g；";
 	   }
 	   
 	   return foodsAll;
	}
	
	
	
	//获取文件中的图片
	public String getImagePath(byte[] bufJPG,String tarPath) throws Exception{
		
	   InputStream in = new ByteArrayInputStream(bufJPG);
	   
	   String imgPath = BeanMenuBookServiceImpl.class.getClassLoader().getResource("../../").getPath()+tarPath;
	   
	   File file = new File(imgPath) ;
		if(!file.exists()){//不存在就创建目录
			file.mkdirs() ;
		}
       
       String imgName = DateUtils.getLocalDate("yyyyMMddHHmmss")+DataUtils.getRadom(8)+".jpg";

       File file1 =new File(imgPath,imgName);//可以是任何图片格式.jpg,.png等
       
       FileOutputStream fos=new FileOutputStream(file1);
          
       byte[] b = new byte[1024];
       
       int nRead = 0;
       
       while ((nRead = in.read(b)) != -1) {
           fos.write(b, 0, nRead);
       }
       
       fos.flush();
       
       fos.close();
       
       in.close();
		
	   return tarPath+"/"+imgName;
	}
	
	
	/**
	 * 根据食材学名查询菜谱（下拉框）
	 * @param name 食材学名
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-08-14
	 */
	@Override
	public List<Map<String, Object>> getMenuBookComboxByName(String name,String isAdvert,String recommend)
			throws Exception {
		return menuBookMapper.getMenuBookComboxByName(name,isAdvert, recommend);
	}

	
	/**
	 * 根据审核状态查询菜谱列表
	 * @param searchNickName 用户昵称
	 * @param AuditStatus 审核状态（1、通过，0、不通过，2、待审核）
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-08-14
	 */
	@Override
	public Result getMenuBookListByAuditStatus(String searchNickName,String searchMenuBookName,
			String auditStatus, int page, int rows) throws Exception {
		
		Result rs = new Result();
		
		List<Map<String, Object>> list = menuBookMapper.findMenuBookListByAuditStatus(searchNickName,searchMenuBookName,auditStatus,(page-1)*rows,rows);
		
		int count = menuBookMapper.findMenuBookListByAuditStatusCount(searchNickName,searchMenuBookName,auditStatus);
		
		rs.put("total", count);
		rs.put("rows", list);
		
		return rs;
	}

	/**
	 * 查询用户收藏的菜谱列表
	 * @param searchNickName 用户昵称
	 * @param page 页数
	 * @param rows 每页显示的行数
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-08-14
	 */
	@Override
	public Result getCollectList(String searchNickName, int page, int rows)
			throws Exception {
		
		Result rs = new Result();
		
		List<Map<String, Object>> list = menuBookMapper.findCollectList(searchNickName,(page-1)*rows,rows);
		
		int count = menuBookMapper.findCollectListCount(searchNickName);
		
		rs.put("total", count);
		rs.put("rows", list);
		
		return rs;
	}

	/**
	 * 菜谱审核通过
	 * @param id 菜谱id
	 * @param categoryNumber 分类次数
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-08-14
	 */
	@Override
	public Result saveAdopt(Long id, int categoryNumber,
			List<Map<String, Object>> clist) throws Exception {
		Result rs = new Result();
		
		
		BeanMenuBook menuBook = menuBookMapper.findById(id);
		
		if(menuBook != null){
			
			menuBook.setAuditStatus("1");
			menuBook.setAuditTime(DateUtils.getLocalDate());
			menuBookMapper.update(menuBook);
			
			//保存审核信息
			BeanMenuBookAudit menuBookAudit=new BeanMenuBookAudit();
			menuBookAudit.setUid(menuBook.getUid());
			menuBookAudit.setMenuId(menuBook.getId());
			menuBookAudit.setCreateTime(DateUtils.getLocalDate());
			menuBookAudit.setAuditStatus("1");
			menuBookAuditMapper.insert(menuBookAudit);
			
			//修改分类，先删除在重新添加
			menubookCategoryMapper.deleteByMenuId(menuBook.getId());
			
			for(Map<String, Object> map:clist){
				//一级
				BeanMenubookCategory firstcategory = new BeanMenubookCategory();
				firstcategory.setMenuId(menuBook.getId());
				firstcategory.setCategoryId(Long.parseLong(map.get("firstId").toString()));
				firstcategory.setCreateTime(DateUtils.getLocalDate());
				
				menubookCategoryMapper.insert(firstcategory);
				
				//二级
				BeanMenubookCategory secondcategory = new BeanMenubookCategory();
				secondcategory.setMenuId(menuBook.getId());
				secondcategory.setCategoryId(Long.parseLong(map.get("secondId").toString()));
				secondcategory.setCreateTime(DateUtils.getLocalDate());
				
				menubookCategoryMapper.insert(secondcategory);
			}
		}
		
		return rs;
	}

	/**
	 * 菜谱审核不通过
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-08-14
	 */
	@Override
	public Result saveNoAdopt(BeanMenuBook beanMenuBook) throws Exception {
		Result rs = new Result();
		
		
		beanMenuBook.setAuditStatus("0");
		beanMenuBook.setAuditTime(DateUtils.getLocalDate());
		menuBookMapper.update(beanMenuBook);
		
		//保存审核信息
		BeanMenuBookAudit menuBookAudit=new BeanMenuBookAudit();
		menuBookAudit.setUid(beanMenuBook.getUid());
		menuBookAudit.setMenuId(beanMenuBook.getId());
		menuBookAudit.setCreateTime(DateUtils.getLocalDate("yyyy-MM-dd HH:mm:ss"));
		menuBookAudit.setAuditStatus("0");
		menuBookAudit.setAuditRemark(beanMenuBook.getRemark());
		menuBookAuditMapper.insert(menuBookAudit);
		
		return rs;
	}

	/**
	 * 推荐产品
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-08-14
	 */
	@Override
	public Result saveRecommend(Long menuId) {
		Result rs = new Result();
		
		BeanMenuBook beanMenuBook = menuBookMapper.findById(menuId);
		
		beanMenuBook.setRecommend("1");
		menuBookMapper.update(beanMenuBook);
		
		return rs;
	}

	
	/**
	 * 推荐的菜谱
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-08-14
	 */
	@Override
	public Result recommendMenuBook(Long id, String recommend) {
		Result rs = new Result();
		
		BeanMenuBook menuBook = new BeanMenuBook();
		menuBook.setId(id);
		
		if(recommend.equals("1")){
			menuBook.setRecommend("0");
		}
		
		if(recommend.equals("0")){
			menuBook.setRecommend("1");
		}
	
		menuBookMapper.update(menuBook);
		
		return rs;
	}

	
	/**
	 * 设为盖头广告
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-08-14
	 */
	@Override
	public Result setAdvert(Long id, String isAdvert) {
		Result rs = new Result();
		
		BeanMenuBook menuBook = new BeanMenuBook();
		menuBook.setId(id);
		
		if(isAdvert.equals("1")){
			menuBook.setIsAdvert("0");
		}
		
		if(isAdvert.equals("0")){
			menuBook.setIsAdvert("1");
		}
	
		menuBookMapper.update(menuBook);
		
		return rs;
	}

	/**
	 * 设为最新
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-08-14
	 */
	@Override
	public Result setNewTime(Long id,String isNew) {
		Result rs = new Result();
		
		BeanMenuBook menuBook = new BeanMenuBook();
		menuBook.setId(id);
		
		if(isNew.equals("1")){
			menuBook.setIsNew("0");
		}
		
		if(isNew.equals("0")){
			menuBook.setIsNew("1");
		}
	
		menuBookMapper.update(menuBook);
		
		return rs;
	}

	/**
	 * 更新上传的文件路径
	 * @param beanMenuBook
	 * @return
	 */
	@Override
	public Result upFilePath(BeanMenuBook beanMenuBook,List<Map<String, Object>> clist) {
		Result rs = new Result();
		
		if(!ValidateUtils.isBlank(beanMenuBook.getId()) && !ValidateUtils.isEmptyForCollection(clist)){
			menuBookMapper.update(beanMenuBook);
			//保存类别
			for(Map<String, Object> map:clist){
				//一级
				BeanMenubookCategory firstcategory = new BeanMenubookCategory();
				firstcategory.setMenuId(beanMenuBook.getId());
				firstcategory.setCategoryId(Long.parseLong(map.get("firstId").toString()));
				firstcategory.setCreateTime(DateUtils.getLocalDate());
				
				menubookCategoryMapper.insert(firstcategory);
				
				//二级
				BeanMenubookCategory secondcategory = new BeanMenubookCategory();
				secondcategory.setMenuId(beanMenuBook.getId());
				secondcategory.setCategoryId(Long.parseLong(map.get("secondId").toString()));
				secondcategory.setCreateTime(DateUtils.getLocalDate());
				
				menubookCategoryMapper.insert(secondcategory);
			}
		}
		return rs;
	}

	/**
	 * 根据文件名（路径中获取）查询菜谱  
	 * file_path like '%/20180326/%';
	 */
	@Override
	public List<BeanMenuBook> findByFilePath(String datePath) {
		 return menuBookMapper.findByFilePath(datePath);
	}



}
