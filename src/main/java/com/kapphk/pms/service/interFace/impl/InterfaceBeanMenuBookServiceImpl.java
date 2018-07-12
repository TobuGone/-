package com.kapphk.pms.service.interFace.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kapphk.pms.bean.BeanMenuBook;
import com.kapphk.pms.bean.BeanMenuBookShare;
import com.kapphk.pms.bean.BeanMenubookProcedure;
import com.kapphk.pms.bean.BeanMenubookShareProcedure;
import com.kapphk.pms.bean.BeanUserMenuBook;
import com.kapphk.pms.bean.PaginationCondition;
import com.kapphk.pms.dao.mapper.BeanMenuBookAuditMapper;
import com.kapphk.pms.dao.mapper.BeanMenuBookMapper;
import com.kapphk.pms.dao.mapper.BeanMenuBookShareMapper;
import com.kapphk.pms.dao.mapper.BeanMenubookCategoryMapper;
import com.kapphk.pms.dao.mapper.BeanMenubookProcedureMapper;
import com.kapphk.pms.dao.mapper.BeanMenubookShareProcedureMapper;
import com.kapphk.pms.dao.mapper.BeanUserMenuBookMapper;
import com.kapphk.pms.service.interFace.InterfaceBeanMenuBookService;
import com.kapphk.pms.util.Contents;
import com.kapphk.pms.util.DataUtils;
import com.kapphk.pms.util.DateUtils;
import com.kapphk.pms.util.FileUploadUtils;
import com.kapphk.pms.util.PmsFile;
import com.kapphk.pms.util.PmsFile.TimeNode;
import com.kapphk.pms.util.Result;
import com.kapphk.pms.util.ValidateUtils;

/**   
 * @ClassName: InterfaceBeanMenuBookServiceImpl    
 * @Description: 菜谱管理
 * @author XS  
 * @date 2016-1-26 上午10:09:57    
 *         
 */
@Service
public class InterfaceBeanMenuBookServiceImpl implements InterfaceBeanMenuBookService {

	@Autowired
	private BeanMenuBookMapper menuBookMapper;
	
	@Autowired
	private BeanUserMenuBookMapper userMenuBookMapper;
	
	@Autowired
	private BeanMenubookProcedureMapper menubookProcedureMapper;
	
	@Autowired
	private BeanMenubookCategoryMapper menubookCategoryMapper;
	
	@Autowired
	private BeanMenuBookAuditMapper menuBookAuditMapper;
	
	@Autowired
	private BeanMenuBookShareMapper menuBookShareMapper;
	
	@Autowired
	private BeanMenubookShareProcedureMapper menubookShareProcedureMapper;
	
	
	/**   
	 * @Title: findByCondition    
	 * @Description: 查询菜谱 
	 * @param @param condition
	 * @param @return    设定文件    
	 * @throws    
	 */
	@Override
	public List<Map<String, Object>> findByCondition(
			Map<String, Object> condition ) {
		 return menuBookMapper.findByCondition(condition );
	}


	/**   
	* @Title: findCollectionByCondition    
	* @Description: 查询我的收藏菜谱  
	* @param @param condition
	* @param @param pc
	* @param @return    设定文件    
	* @throws    
	*/
	@Override
	public List<Map<String, Object>> findCollectionByCondition(
			Map<String, Object> condition, PaginationCondition pc) {
	   return userMenuBookMapper.findByCondition(condition, pc);
	}


	/**   
	* @Title: findMenuBookPagination    
	* @Description: 分页查询菜谱 
	* @param @param condition
	* @param @param pc
	* @param @return    设定文件    
	* @throws    
	*/
	@Override
	public List<Map<String, Object>> findMenuBookPagination(
			Map<String, Object> condition, PaginationCondition pc) {
		 return menuBookMapper.findMenuBookPagination(condition, pc);
	}


	/**   
	* @Title: findById    
	* @Description: TODO 查询菜谱详情
	* @param @param id
	* @param @return    设定文件    
	* @throws  
	* 
	* 修改于：2018-03-22  huzi
	*/
	@Override
	public Map<String,Object> updateMenuBookById(Long id,Long userId) {
		
		BeanMenuBook menuBook = menuBookMapper.findById(id);
		
		menuBook.setClickNumber(menuBook.getClickNumber()+1);
		
		menuBookMapper.update(menuBook);
		
		 return menuBookMapper.searchMenuBookById(id,userId);
	}


	/**   
	* @Title: saveCollection    
	* @Description:  保存用户收藏菜谱 
	* @param @param userMenuBook    设定文件    
	* @throws    
	*/
	@Override
	public void saveCollection(BeanUserMenuBook userMenuBook) {
		userMenuBookMapper.insert(userMenuBook);
		
	}


	/**   
	* @Title: deleteCollection    
	* @Description:  删除我的收藏
	* @param @param userCollectionId    设定文件    
	* @throws    
	*/
	@Override
	public void deleteCollection(Long userCollectionId) {
		 userMenuBookMapper.delete(userCollectionId);
		
	}

	/**
	 * 搜索菜谱
	 * @param menubookName
	 * @param rs
	 * @return
	 */
	@Override
	public Result searchMenuBook(String menubookName, Integer pages,
			Integer pageSize) {
		
		Result rs = new Result();
		
		BeanMenuBook book = new BeanMenuBook();
		book.setName(menubookName);
		book.setAuditStatus("1");
		List<BeanMenuBook> list = menuBookMapper.findByPage(book, (pages-1)*pageSize, pageSize);
		
		int count = menuBookMapper.findByPageCount(book); 
		
		rs.put("total", count);
		rs.put("list", list);
		
		return rs;
	}

	/**
	 * 取消收藏菜谱
	 * Lotus 2016-04-11
	 */
	@Override
	public void cancelCollection(BeanUserMenuBook userMenuBook,Result rs) {
		try {
			if(ValidateUtils.isBlank(userMenuBook.getUserId()) || ValidateUtils.isBlank(userMenuBook.getMenuBookId())){
				rs.setStatus(Contents.PARAMS_ERROR);
				return;
			}
			userMenuBook.setType("2");
			userMenuBookMapper.deleteByEntity(userMenuBook);
		} catch (Exception e) {
            e.printStackTrace();
            rs.setStatus(Contents.ERROR);
		}
	}


	/**
	 * 保存用户自定义菜单
	 * Lotus 2016-04-13
	 */
	@Override
	public void saveCustomMenuBook(BeanMenuBook menuBook,Result rs) {
		if(ValidateUtils.isBlank(menuBook.getUid()) 
				|| ValidateUtils.isBlank(menuBook.getId())
			){
			rs.setStatus(Contents.PARAMS_ERROR);
			rs.setMsg("参数错误");
		}
		try {
			List<BeanMenuBook> list=menuBookMapper.findByPage(menuBook, 0, 1);
			if(!ValidateUtils.isBlank(list)){
				menuBookMapper.update(menuBook);	
			}
		} catch (Exception e) {
			rs.setStatus(Contents.ERROR);
			rs.setMsg("保存失败");
		}
	}


	@Override
	public void deleteUserMenuBookFile(BeanMenuBook userMenuBook, Result rs)
			throws Exception {
		if(ValidateUtils.isBlank(userMenuBook.getId())
				|| ValidateUtils.isBlank(userMenuBook.getUid())){
			rs.setStatus(Contents.PARAMS_ERROR);
			rs.setMsg("参数错误");
			return;
		}
		menuBookMapper.deleteByEntity(userMenuBook);
	}

	

	/**
	 * 解析PMS文件,保存到菜谱表及菜谱步骤表
	 * Lotus 2016-04-18
	 * 
	 * 修改于2018-03-01 huzi
	 */
	@Override
	public void saveCustomMenuBookByPath(Long userId, String fileName, Long type,Result rs)
			throws Exception {
	
		//fileName = new String (fileName.getBytes("ISO8859-1"),"utf-8");//如果是增量更新到云服务器项目中，需将此条代码注释掉
		
		if(ValidateUtils.isBlank(userId) || ValidateUtils.isBlank(fileName) || ValidateUtils.isBlank(type)){
			rs.setMsg("参数有误");
			rs.setStatus(Contents.ERROR);
			return;
		}
		// 判断菜谱名是否重复
		BeanMenuBook book = new BeanMenuBook();
		book.setName(fileName);
		book.setUid(userId);
		book.setStatus(1);
		List<BeanMenuBook> list = menuBookMapper.findByPage(book, 0, 1);
		BeanMenuBook menuBook = new BeanMenuBook();
		if(type==1){
			if(!ValidateUtils.isEmptyForCollection(list) && list.get(0).getAuditStatus().equals("1")){//当菜谱存在且状态为1（1为正常显示，2为管理员已删除，不显示于列表）
				rs.setMsg("菜谱名已存在,请重新输入");
				if(userId==-1){
					rs.setStatus(Contents.ERROR);
				}else{
					rs.setStatus(Contents.EXIST);
				}
				return;
			}else{
				menuBook.setId(null);
			}
		}else{
			menuBook.setId(list.get(0).getId());
		}
		
		String pmsPath = rs.getData().getString("path");
		pmsPath = FileUploadUtils.class.getClassLoader().getResource("../../")
				.getPath()
				+ pmsPath;
		if (!ValidateUtils.isBlank(pmsPath)) {

			File file = new File(pmsPath);
			if (file.exists()) {
				FileInputStream fi = new FileInputStream(file);
				byte[] bytes = new byte[(int) file.length()];
				fi.read(bytes);
				fi.close();

				PmsFile pmsFile = new PmsFile(bytes);
				List<BeanMenubookProcedure> procedures = new ArrayList<BeanMenubookProcedure>();
				menuBook.setUid(userId);
				menuBook.setName(fileName);
				menuBook.setDescribes(ValidateUtils.isBlank(pmsFile.text)?"":pmsFile.text);
				menuBook.setCreateTime(DateUtils.getLocalDate("yyyy-MM-dd HH:mm:ss"));
				menuBook.setFilePath(rs.getData().getString("path"));
				if(userId==-1){
					menuBook.setAuditStatus("1");
				}
				
				menuBook.setOverTime(Integer.toString(pmsFile.getLuboLenth()));//设置录波数据长度
				

				if(!ValidateUtils.isBlank(pmsFile.bufJPG)){
					// 生成图片
					String path = FileUploadUtils.class.getClassLoader()
							.getResource("../../").getPath();
					String path1 = "/upload/menubook/"
							+ DateUtils.getLocalDate("yyyyMMdd");
					path = path + path1;
					File file1 = new File(path);
					if (!file1.exists()) {
						file1.mkdirs();
					}
					String fileName2 = DateUtils.getLocalDate("yyyyMMddHHmmss")
							+ DataUtils.getRadom(8) + ".jpg";
					String filePath = path + "/" + fileName2;
					String visitePath = path1 + "/" + fileName2;
					FileOutputStream fo = new FileOutputStream(filePath);
					fo.write(pmsFile.bufJPG);
					fo.close();
					menuBook.setLogoPath(visitePath);
				}
				
		 		//菜谱步骤   (新增菜谱预处理    修改于2018-03-01 huzi)
				Map<String, Object> map1 = new HashMap<String, Object>();
				Map<String, Object> map2 = new HashMap<String, Object>();
				Map<String, Integer> mapId = new HashMap<String, Integer>();
				
				
				for (TimeNode timeNode : pmsFile.listTimeNode) {
					
					int minute = timeNode.Times / 60;
			 		int second = timeNode.Times % 60;
					
					BeanMenubookProcedure item = new BeanMenubookProcedure();
					item.setMinute(String.valueOf(minute));//分
					item.setSecond(String.valueOf(second));//秒
					item.setDescribes(timeNode.Tips);//文字提示
					item.setCreateTime(DateUtils.getLocalDate("yyyy-MM-dd HH:mm:ss"));//创建日期
					
			 		 String foods = "";
			 		 
					//遍历步骤表食材名称
					for (int i = 0; i < timeNode.FoodNames.length; i++) {
			    		if (ValidateUtils.isBlank(timeNode.FoodNames[i])) {
							continue;
						} 
			    		else {
			    			mapId.put(timeNode.FoodNames[i],timeNode.FoodIDs[i]);
			    			
							for (int j = 0; j < pmsFile.foodName.length; j++) {
								if(timeNode.FoodNames[i].equals(pmsFile.foodName[j])){//如果预处理食材名称跟步骤表（时间节点）食材名称相同，则在步骤表食材数据后面拼接上相应的食材预处理描述
									String food = (String) (ValidateUtils.isBlank(timeNode.FoodIDs[i]) ? 0 : timeNode.FoodIDs[i] + "|"
											+ timeNode.FoodNames[i] + "|"
											+ timeNode.FoodWgts[i]  + "|"
											+ pmsFile.foodYvDescribe[j] + ";");
									foods += food;
									break;
								}
							}	
						}
			    	}
						item.setFoods(foods);
						procedures.add(item); 
					}
				
				
				//遍历预处理食材名称
				String foods = "";
				for (int i = 0; i < pmsFile.foodName.length; i++) {
					map1.put(pmsFile.foodName[i],pmsFile.foodWgt[i]);
					map2.put(pmsFile.foodName[i],pmsFile.foodYvDescribe[i]);
				}
				
				//遍历的不是值   而是key  key1和key2相同   所以没区别
				for (String key1 : map1.keySet()) {
					String food1 = (String) (key1 + ":" + map1.get(key1) + "g:"+map2.get(key1)+";");//key1=食材名称   map1.get(key1)=食材重量  map2.get(key1)=食材预处理描述
					foods += food1;
				}
				
				//设置食材字段
				menuBook.setFoods(foods);
//				System.out.println(foods);	 
				
				// 判断插入或更新菜谱表
				if (ValidateUtils.isBlank(menuBook.getId())) {//菜谱id为空时  插入
					menuBookMapper.insert(menuBook);
				} else {
					menuBookMapper.update(menuBook);
				}
				
				
				// 保存类别
				menubookCategoryMapper.deleteByMenuId(menuBook.getId());
				
				// 保存步骤表
				BeanMenubookProcedure delitem = new BeanMenubookProcedure();
				delitem.setMenuId(menuBook.getId());
				menubookProcedureMapper.deleteByEntity(delitem);
				if (!ValidateUtils.isEmptyForCollection(procedures)) {
					for (BeanMenubookProcedure item : procedures) {
						item.setMenuId(menuBook.getId());
					}
					menubookProcedureMapper.inserts(procedures);
				}
				
				// 保存上传记录
				if (!ValidateUtils.isBlank(userId)) {
					BeanUserMenuBook userMenuBook = new BeanUserMenuBook();
					userMenuBook.setUserId(userId);
					userMenuBook.setMenuBookId(menuBook.getId());
					int menuCount = userMenuBookMapper.findByPageCount(userMenuBook);
					if (menuCount == 0){
						// 1=上传；2=收藏
						userMenuBook.setType("1");
						userMenuBook.setFilePath(rs.getData().getString("path"));
						userMenuBook.setCreateTime(DateUtils
								.getLocalDate("yyyy-MM-dd HH:mm:ss"));
						userMenuBookMapper.insert(userMenuBook);
					}
				}
				// 返回菜谱id
				rs.put("id", menuBook.getId());
			}
		}
	}
	


	/**
	 * 下载菜谱文件
	 * Lotus 2016-04-18
	 * 
	 * 修改于2018-03-01 huzi
	 */
	@Override
	public void downloadMenuBookFile(BeanMenuBook menuBook, Result rs)throws Exception {
		if (ValidateUtils.isBlank(menuBook.getId())
				|| ValidateUtils.isBlank(menuBook.getUid())) {
			rs.setStatus(Contents.PARAMS_ERROR);
			rs.setMsg("参数错误");
		}
		//List<BeanMenuBook> list = menuBookMapper.findByPage(menuBook, 0, 1);
		BeanMenuBook m=menuBookMapper.findById(menuBook.getId());//读取源文件url  需要菜单id
		if (!ValidateUtils.isBlank(m)) {
			//BeanMenuBook m=list.get(0);
			rs.put("fileNmae", m.getName());
			String resPath = FileUploadUtils.class.getClassLoader()
					.getResource("../../").getPath();

			File file = new File(resPath + m.getFilePath());//源文件
			byte[] bytes = new byte[]{};//初始化源文件字节数组
			if(file.exists()){
				FileInputStream fi = new FileInputStream(file);//源文件路径  
				bytes = new byte[(int) file.length()];
				fi.read(bytes);
				fi.close();
			}
			
			String path = "/upload/menupms/" + DateUtils.getLocalDate("yyyyMMdd");
			String fullPath = resPath + path;
			File file1 = new File(fullPath);
			if (!file1.exists()) {
				file1.mkdirs();
			}
			String fileName ="/"+ DateUtils.getLocalDate("yyyyMMddHHmmss")+ DataUtils.getRadom(8) + ".pms";
			fullPath=fullPath+fileName;
			File downFile=new File(fullPath);
			FileOutputStream fo=new FileOutputStream(downFile);//解析后文件downFile：新文件路径   
			fo.write(bytes);//输出
			fo.close();
			
			path = path + fileName;
			rs.put("path", path);
		}
	}

	/**
	 * 菜谱置顶
	 * Lotus 2016-04-19
	 */
	@Override
	public void topMenuBook(BeanUserMenuBook menuBook, Result rs)throws Exception {
		if(ValidateUtils.isBlank(menuBook.getMenuBookId())
				|| ValidateUtils.isBlank(menuBook.getUserId())){
			rs.setStatus(Contents.PARAMS_ERROR);
			rs.setMsg("参数错误");
			return;
		}
		userMenuBookMapper.cancelTopMenuBookByUserId(menuBook.getUserId());
		List<BeanUserMenuBook> list=userMenuBookMapper.findAll(menuBook);
		if(!ValidateUtils.isEmptyForCollection(list)){
			BeanUserMenuBook item=list.get(0);
			item.setIsTop("1");
			userMenuBookMapper.update(item);	
		}
	}


	/**
	 * 获取审核信息
	 * Lotus 2016-04-26
	 */
	@Override
	public void getMenuBookAudit(Long uid, Result rs) throws Exception {
		if(ValidateUtils.isBlank(uid)){
			rs.setStatus(Contents.PARAMS_ERROR);
			rs.setMsg("参数错误");
			return;
		}
		List<Map<String, Object>> list = menuBookAuditMapper.getMenuBookAudit(uid);
		rs.put("auditDetail", list);
	}

	/**
	 * 解析PMS临时文件,保存到菜谱表及菜谱步骤表
	 * 
	 * 修改于2018-03-01 huzi
	 * 
	 */
	@Override
	public void saveFileTemporaryh(Long userId, String fileName, Result rs) throws Exception {
		
		fileName = new String (fileName.getBytes("ISO8859-1"),"utf-8");//huzi
		//System.out.println(fileName);
				
				BeanMenuBook book = new BeanMenuBook();
				book.setName(fileName);
				book.setUid(userId);
				book.setStatus(2);
				List<BeanMenuBook> list = menuBookMapper.findByAll(book);

				String pmsPath = rs.getData().getString("path");
				pmsPath = FileUploadUtils.class.getClassLoader().getResource("../../")
						.getPath()
						+ pmsPath;
				if (!ValidateUtils.isBlank(pmsPath)) {

					File file = new File(pmsPath);
					if (file.exists()) {
						FileInputStream fi = new FileInputStream(file);
						byte[] bytes = new byte[(int) file.length()];
						fi.read(bytes);
						fi.close();

						PmsFile pmsFile = new PmsFile(bytes);
						BeanMenuBook menuBook = new BeanMenuBook();
						List<BeanMenubookProcedure> procedures = new ArrayList<BeanMenubookProcedure>();
						menuBook.setUid(userId);
						menuBook.setStatus(2);
						menuBook.setName(fileName);
						menuBook.setDescribes(ValidateUtils.isBlank(pmsFile.text)?"":pmsFile.text);
						menuBook.setCreateTime(DateUtils
								.getLocalDate("yyyy-MM-dd HH:mm:ss"));
						menuBook.setFilePath(rs.getData().getString("path"));
						if(userId==-1){
							menuBook.setAuditStatus("1");
						}
						

						if(!ValidateUtils.isBlank(pmsFile.bufJPG)){
							// 生成图片
							String path = FileUploadUtils.class.getClassLoader()
									.getResource("../../").getPath();
							String path1 = "/upload/menubook/"
									+ DateUtils.getLocalDate("yyyyMMdd");
							path = path + path1;
							File file1 = new File(path);
							if (!file1.exists()) {
								file1.mkdirs();
							}
							String fileName2 = DateUtils.getLocalDate("yyyyMMddHHmmss")
									+ DataUtils.getRadom(8) + ".jpg";
							String filePath = path + "/" + fileName2;
							String visitePath = path1 + "/" + fileName2;
							FileOutputStream fo = new FileOutputStream(filePath);
							fo.write(pmsFile.bufJPG);
							fo.close();
							menuBook.setLogoPath(visitePath);
						}

						//菜谱步骤   ( 修改于2018-03-01 huzi)
						Map<String, Object> map1 = new HashMap<String, Object>();
						Map<String, Object> map2 = new HashMap<String, Object>();
						Map<String, Integer> mapId = new HashMap<String, Integer>();
						for (TimeNode timeNode : pmsFile.listTimeNode) {
							BeanMenubookProcedure item = new BeanMenubookProcedure();
							item.setMinute(String.valueOf(timeNode.Times / 60));//分
							item.setSecond(String.valueOf(timeNode.Times % 60));//秒
							item.setDescribes(timeNode.Tips);//文字提示
							item.setCreateTime(DateUtils.getLocalDate("yyyy-MM-dd HH:mm:ss"));//创建日期
							String foods = "";
							
							//遍历步骤表食材名称
							for (int i = 0; i < timeNode.FoodNames.length; i++) {
					    		if (ValidateUtils.isBlank(timeNode.FoodNames[i])) {
									continue;
								} 
					    		else {
					    			mapId.put(timeNode.FoodNames[i],timeNode.FoodIDs[i]);
					    			
									for (int j = 0; j < pmsFile.foodName.length; j++) {
										if(timeNode.FoodNames[i].equals(pmsFile.foodName[j])){//如果预处理食材名称跟步骤表（时间节点）食材名称相同，则在步骤表食材数据后面拼接上相应的食材预处理描述
											String food = (String) (ValidateUtils.isBlank(timeNode.FoodIDs[i]) ? 0 : timeNode.FoodIDs[i] + "|"
													+ timeNode.FoodNames[i] + "|"
													+ timeNode.FoodWgts[i]  + "|"
													+ pmsFile.foodYvDescribe[j] + ";");
											foods += food;
											break;
										}
									}	
								}
					    	}
								item.setFoods(foods);
								procedures.add(item); 
							}
						 
						
						//遍历预处理食材名称
						String foods = "";
						for (int i = 0; i < pmsFile.foodName.length; i++) {
							map1.put(pmsFile.foodName[i],pmsFile.foodWgt[i]);
							map2.put(pmsFile.foodName[i],pmsFile.foodYvDescribe[i]);
						}
						
						//遍历的不是值   而是key  key1和key2相同   所以没区别
						for (String key1 : map1.keySet()) {
							String food1 = (String) (key1 + ":" + map1.get(key1) + "g:"+map2.get(key1)+";");//key1=食材名称   map1.get(key1)=食材重量  map2.get(key1)=食材预处理描述
							foods += food1;
						}
						
						menuBook.setFoods(foods);
						
						// 判断插入或更新
						if (ValidateUtils.isEmptyForCollection(list)) {
							menuBook.setId(null);
							menuBookMapper.insert(menuBook);
						} else {
							menuBook.setId(list.get(0).getId());
							menuBookMapper.update(menuBook);
						}
						
						// 保存步骤
						BeanMenubookProcedure delitem = new BeanMenubookProcedure();
						delitem.setMenuId(menuBook.getId());
						menubookProcedureMapper.deleteByEntity(delitem);
						if (!ValidateUtils.isEmptyForCollection(procedures)) {
							for (BeanMenubookProcedure item : procedures) {
								item.setMenuId(menuBook.getId());
							}
							menubookProcedureMapper.inserts(procedures);
						}
						// 返回菜谱id
						rs.put("id", menuBook.getId());
					}
				}
	}


	/**
	 * 根据所分享菜谱名查询    2018-05-21  huzi
	 */
	@Override
	public BeanMenuBookShare findMenuBookShareByName(String name) {
		return menuBookShareMapper.findMenuBookShareByName(name);
	}
	
	/**
	 * 解析PMS文件,保存到用户分享对应的菜谱表及步骤表
	 * 2018-05-22 huzi
	 */
	@Override
	public void saveCustomMenuBookShareByPath(Long userId, String fileName,Result rs)throws Exception {
	
		//fileName = new String (fileName.getBytes("ISO8859-1"),"utf-8");
		//System.out.println(fileName);
		
		if(ValidateUtils.isBlank(userId) || ValidateUtils.isBlank(fileName)){
			rs.setMsg("参数有误");
			rs.setStatus(Contents.ERROR);
			return;
		}
		
		BeanMenuBookShare newMenuBookShare = new BeanMenuBookShare();//创建一个新的菜谱对象
		
		String pmsPath = rs.getData().getString("path");
		pmsPath = FileUploadUtils.class.getClassLoader().getResource("../../")
				.getPath()
				+ pmsPath;
		if (!ValidateUtils.isBlank(pmsPath)) {

			File file = new File(pmsPath);
			if (file.exists()) {
				FileInputStream fi = new FileInputStream(file);
				byte[] bytes = new byte[(int) file.length()];
				fi.read(bytes);
				fi.close();

				PmsFile pmsFile = new PmsFile(bytes);
				List<BeanMenubookShareProcedure> procedures = new ArrayList<BeanMenubookShareProcedure>();
				newMenuBookShare.setUid(userId);
				newMenuBookShare.setName(fileName);
				newMenuBookShare.setDescribes(ValidateUtils.isBlank(pmsFile.text)?"":pmsFile.text);
				newMenuBookShare.setCreateTime(DateUtils.getLocalDate("yyyy-MM-dd HH:mm:ss"));
				newMenuBookShare.setFilePath(rs.getData().getString("path"));

				
				if(!ValidateUtils.isBlank(pmsFile.bufJPG)){
					// 生成图片
					String path = FileUploadUtils.class.getClassLoader().getResource("../../").getPath();
					String path1 = "/upload/menubook/" + DateUtils.getLocalDate("yyyyMMdd");
					path = path + path1;
					File file1 = new File(path);
					if (!file1.exists()) {
						file1.mkdirs();
					}
					String fileName2 = DateUtils.getLocalDate("yyyyMMddHHmmss") + DataUtils.getRadom(8) + ".jpg";
					String filePath = path + "/" + fileName2;
					String visitePath = path1 + "/" + fileName2;
					FileOutputStream fo = new FileOutputStream(filePath);
					fo.write(pmsFile.bufJPG);
					fo.close();
					newMenuBookShare.setLogoPath(visitePath);
				}
				
		 		//菜谱步骤
				Map<String, Object> map1 = new HashMap<String, Object>();
				Map<String, Object> map2 = new HashMap<String, Object>();
				Map<String, Integer> mapId = new HashMap<String, Integer>();
				for (TimeNode timeNode : pmsFile.listTimeNode) {
					BeanMenubookShareProcedure item = new BeanMenubookShareProcedure();
					item.setMinute(String.valueOf(timeNode.Times / 60));//分
					item.setSecond(String.valueOf(timeNode.Times % 60));//秒
					item.setDescribes(timeNode.Tips);//文字提示
					item.setCreateTime(DateUtils.getLocalDate("yyyy-MM-dd HH:mm:ss"));//创建日期
					String foods = "";
					
					//遍历步骤表食材名称
					for (int i = 0; i < timeNode.FoodNames.length; i++) {
			    		if (ValidateUtils.isBlank(timeNode.FoodNames[i])) {
							continue;
						} 
			    		else {
			    			mapId.put(timeNode.FoodNames[i],timeNode.FoodIDs[i]);
			    			
							for (int j = 0; j < pmsFile.foodName.length; j++) {
								if(timeNode.FoodNames[i].equals(pmsFile.foodName[j])){//如果预处理食材名称跟步骤表（时间节点）食材名称相同，则在步骤表食材数据后面拼接上相应的食材预处理描述
									String food = (String) (ValidateUtils.isBlank(timeNode.FoodIDs[i]) ? 0 : timeNode.FoodIDs[i] + "|"
											+ timeNode.FoodNames[i] + "|"
											+ timeNode.FoodWgts[i]  + "|"
											+ pmsFile.foodYvDescribe[j] + ";");
									foods += food;
									break;
								}
							}	
						}
			    	}
						item.setFoods(foods);
						procedures.add(item); 
					}
				 
				
				// 遍历预处理食材名称
				String foods = "";
				for (int i = 0; i < pmsFile.foodName.length; i++) {
					map1.put(pmsFile.foodName[i],pmsFile.foodWgt[i]);
					map2.put(pmsFile.foodName[i],pmsFile.foodYvDescribe[i]);
				}
				
				// 遍历的不是值   而是key  key1和key2相同   所以没区别
				for (String key1 : map1.keySet()) {
					String food1 = (String) (key1 + ":" + map1.get(key1) + "g:"+map2.get(key1)+";");//key1=食材名称   map1.get(key1)=食材重量  map2.get(key1)=食材预处理描述
					foods += food1;
				}
				
				// 设置食材字段
				newMenuBookShare.setFoods(foods);
				
				// 判断插入或更新菜谱表
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("userId", userId);
				map.put("fileName", fileName);
				
				BeanMenuBookShare oldShare = menuBookShareMapper.findByUserIdAndFileName(map);//根据用户id和菜谱名查询菜谱分享记录
				
				if (oldShare == null) {//对象为空时  插入
					menuBookShareMapper.insert(newMenuBookShare);
				} else {													//针对同一个用户多次分享相同菜谱名的文件，数据库永远只存最新的记录
					newMenuBookShare.setId(oldShare.getId());				//对象不为空时  将之前存在的菜谱id赋给新的对象    删除原菜谱步骤数据    删除原菜谱 
					menubookShareProcedureMapper.deleteByMenuId(oldShare.getId());	
					menuBookShareMapper.delete(oldShare.getId());	
					menuBookShareMapper.insert(newMenuBookShare);
					
				}
				
				// 保存类别
				menubookCategoryMapper.deleteByMenuId(newMenuBookShare.getId());
				
				// 保存步骤表
				BeanMenubookShareProcedure delitem = new BeanMenubookShareProcedure();
				delitem.setMenuId(newMenuBookShare.getId());
				menubookShareProcedureMapper.deleteByEntity(delitem);
				if (!ValidateUtils.isEmptyForCollection(procedures)) {
					for (BeanMenubookShareProcedure item : procedures) {
						item.setMenuId(newMenuBookShare.getId());
					}
					menubookShareProcedureMapper.inserts(procedures);
				}
				
				// 返回菜谱id
				rs.put("id", newMenuBookShare.getId());
			}
		}
	}
	
	
}
