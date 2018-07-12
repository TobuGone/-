package com.kapphk.pms.controller.interFace;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.kapphk.pms.bean.BeanAppAndroidUpdate;
import com.kapphk.pms.bean.BeanAppIOSUpdate;
import com.kapphk.pms.bean.BeanEquipmentUpdate;
import com.kapphk.pms.bean.PaginationCondition;
import com.kapphk.pms.service.interFace.InterfaceBeanSystemMessageService;
import com.kapphk.pms.service.web.BeanAppAndroidService;
import com.kapphk.pms.service.web.BeanAppIOSService;
import com.kapphk.pms.service.web.BeanEquipmentService;
import com.kapphk.pms.util.Contents;
import com.kapphk.pms.util.FileRead;
import com.kapphk.pms.util.FileUploadUtils;
import com.kapphk.pms.util.Result;
import com.kapphk.pms.util.ValidateUtils;

/**   
 * @ClassName: InterfaceBeanDocumentController    
 * @Description: 系统消息管理   
 * @author XS  
 * @date 2016-1-28 下午5:19:11    
 *         
 */
@RestController
@RequestMapping("/message/")
public class InterfaceBeanSystemMessageController {
	
	
	@Autowired
	private InterfaceBeanSystemMessageService systemMessageService;
	
	@Autowired
	private BeanEquipmentService beanEquipmentService;
	
	@Autowired
	private BeanAppAndroidService beanAppAndroidService;
	
	@Autowired
	private BeanAppIOSService beanAppIOSService;
	
	@RequestMapping("findSystemMessage.aspf")
	public Result findSystemMessage(PaginationCondition pc,Result rs){
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			
	       List<Map<String,Object>> list = 	systemMessageService.findPagination(condition, pc);
	       rs.put("systemMsgs", list != null ? list : new ArrayList<Object>());
		} catch (Exception e) {
          e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 * PMS固件版本更新(旧方式)
	 * 2018-06-05  huzi
	 * @param rs
	 * @return
	 */
	@RequestMapping("getEquipmentUpdate.aspf")
	public Result getEquipmentUpdateOld(Result rs){
		try {
			systemMessageService.getEquipmentUpdateOld(rs);
		} catch (Exception e) {
			e.printStackTrace();
			rs.setStatus(Contents.ERROR);
		}
		return rs;
	}
	
	
	/**
	 * PMS固件版本更新
	 * 2018-06-11  huzi
	 * @param hardwareVersion   硬件型号
	 * @param softwareVersion	软件型号
	 * @param versionCode		版本号
	 * @param rs					  
	 * @return					最新版本的版本号
	 * 							
	 */
	@RequestMapping("getEquipmentVersion.aspf")
	public Result getEquipmentVersion( String hardwareVersion, String softwareVersion, String versionCode,Result rs){
		
		
			if(ValidateUtils.isBlank(hardwareVersion) || ValidateUtils.isBlank(softwareVersion) 
													  || ValidateUtils.isBlank(versionCode) 
			){
				rs.setStatus(Contents.PARAMS_ERROR);
				rs.setMsg("参数错误") ;
				return rs;
				
			}else{
				
				try {
				
					//查询出最新版本的固件
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("hardwareVersion", hardwareVersion);
					map.put("softwareVersion", softwareVersion);
					BeanEquipmentUpdate hs = beanEquipmentService.findByHardwareAndSoftwar(map);
					
					if (hs != null) {
					
						//判断如果不是最新版本，则将最新版本信息返回给app
						if (!hs.getVersionCode().equals(versionCode)) 
						{
							rs.put("versionCode", hs.getVersionCode());
						}
						else {
							rs.setStatus(Contents.CUS_ERROR);
							rs.setMsg("当前版本已是最新");
						}
					}else {
					     rs.setStatus(Contents.NOT_EXIST);	
					     rs.setMsg("当前服务器无固件资源");
					}
			} catch (Exception e) {
				e.printStackTrace();
				rs.setStatus(Contents.ERROR);
			}
		}
		return rs;
	}
	
	
	/**
	 * PMS固件版本更新
	 * 2018-06-11  huzi
	 * @param request		         用于获取app传送的.myl文件
	 * @param response			用于存储zip文件路径
	 * @param hardwareVersion   硬件型号
	 * @param softwareVersion	软件型号
	 * @param versionCode		版本号
	 * @param rs					  
	 * @return					1.最新版本的zip文件路径
	 * 							2.最新版本的版本号
	 */
	@RequestMapping("getNewEquipmentUpdate.aspf")
	public Result getEquipmentUpdate(HttpServletRequest request,HttpServletResponse response,
									 String hardwareVersion,
									 String softwareVersion,
									 String versionCode, 
									 Result rs){
		
			//创建session:用于存储zip文件路径
		 	response.setCharacterEncoding("UTF=8");
	        response.setContentType("text/html;charset=UTF-8");
	        HttpSession session = request.getSession();	//使用request对象的getSession()获取session，如果session不存在则创建一个
		
			if(ValidateUtils.isBlank(hardwareVersion) || ValidateUtils.isBlank(softwareVersion) 
													  || ValidateUtils.isBlank(versionCode) 
			){
				rs.setStatus(Contents.PARAMS_ERROR);
				rs.setMsg("参数错误") ;
				return rs;
				
			}else{
				
				try {
				
					//查询出最新版本的固件
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("hardwareVersion", hardwareVersion);
					map.put("softwareVersion", softwareVersion);
					BeanEquipmentUpdate hs = beanEquipmentService.findByHardwareAndSoftwar(map);
					
					if (hs != null) {
					
						//判断如果不是最新版本，则读取.myl文件内容比较文件
						if (!hs.getVersionCode().equals(versionCode)) {
							
							String rootPath = FileUploadUtils.class.getClassLoader().getResource("../..").getPath();//目录所在根路径
						
							//获取最新版本固件的解压目录路径
	//						String rootPath = request.getContextPath();//获取到项目的根路径 下一步找到你要对比的文件路径 
							System.out.println(rootPath);
							String unzipPath = rootPath + hs.getPath(); 			//全路径
							
							//调用方法比对文件,并将zip文件路径返回给app
							rs = FileRead.filtrateFile(request, unzipPath, false);	
							JSONObject json  = rs.getData();			 	  		//转成json
							String zipPath = (String)json.get("zipPath");     		//json转成string
	
							//将数据存储到session中
					        session.setAttribute("zipPath", zipPath);
							
					        //返回给app
							rs.put("zipPath", zipPath);
							rs.put("versionCode", hs.getVersionCode());
						}
						else {
							rs.setStatus(Contents.CUS_ERROR);
							rs.setMsg("当前版本已是最新");
						}
					}else {
					     rs.setStatus(Contents.NOT_EXIST);	
					}
			} catch (Exception e) {
				e.printStackTrace();
				rs.setStatus(Contents.ERROR);
			}
		}
		return rs;
	}
	
	/**
	 * 删除PMS固件更新生成的临时文件
	 * 2018-04-10  huzi
	 * @param hardwareVersion   硬件型号
	 * @param softwareVersion	软件型号
	 * @param rs
	 * @return
	 */
	@RequestMapping("deleteZip.aspf")
	public Result deleteZip(HttpServletRequest request, HttpServletResponse response, Result rs){
		
		try {
			
			String rootPath = FileUploadUtils.class.getClassLoader().getResource("../..").getPath();//目录所在根路径
			
			String zipPath = (String) request.getSession().getAttribute("zipPath");
			zipPath = rootPath + zipPath;
			File zipFile = new File(zipPath);	
			if (zipFile.exists()) { 
				zipFile.delete();				//删除压缩包		
			}
			
			String fileStr = (String) request.getSession().getAttribute("fileStr");
			File mylFile = new File(fileStr);	
			if (mylFile.exists()) { 
				mylFile.delete();				//删除.myl文件		
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			rs.setStatus(Contents.ERROR);
		}
		return rs;
	}
	

	
	
	
//	/**
//	 * PMS固件版本更新
//	 * 2018-04-10  huzi
//	 * @param hardwareVersion   硬件型号
//	 * @param softwareVersion	软件型号
//	 * @param rs
//	 * @return
//	 */
//	@RequestMapping("getNewEquipmentUpdate.aspf")
//	public Result getEquipmentUpdate(String hardwareVersion,String softwareVersion, Result rs){
//		
//		try {
//			hardwareVersion = new String (hardwareVersion.getBytes("ISO8859-1"),"utf-8");
//			softwareVersion = new String (softwareVersion.getBytes("ISO8859-1"),"utf-8");
//			
//			Map<String,Object> map = new HashMap<String,Object>();
//			map.put("hardwareVersion", hardwareVersion);
//			map.put("softwareVersion", softwareVersion);
//			
//			BeanEquipmentUpdate hs = beanEquipmentService.findByHardwareAndSoftwar(map);
//			
//			if (hs != null) {
//				rs.put("versionCode", hs.getVersionCode());
//				rs.put("path", hs.getPath());
//			}
//			else {
//				rs.setStatus(Contents.NOT_EXIST);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			rs.setStatus(Contents.ERROR);
//		}
//		return rs;
//	}
	
	
	
	/**
	 * Android应用版本更新
	 * 2018-04-10  huzi
	 * @param rs
	 * @return
	 */
	@RequestMapping("getAppAndroidUpdate.aspf")
	@ResponseBody
	public Result getAppAndroidUpdate(String type, Result rs){
		try {
			
			//查询最新版本应用
			BeanAppAndroidUpdate maxVersionApp = beanAppAndroidService.finMaxVersion();
			
			//请求1，给出最新版本数据
			if ("1".equals(type)) {
				
				//给出最新版本的数据
				rs.put("appName", maxVersionApp.getName());						//应用名称
				rs.put("path", maxVersionApp.getPath());						//文件路径
				rs.put("versionName", maxVersionApp.getVersionName());			//版本名称
				rs.put("versionCode", maxVersionApp.getVersionCode());			//版本号
				rs.put("updateDescription", maxVersionApp.getRemark());			//更新说明
				rs.put("updateDate", maxVersionApp.getCreateTime());			//更新时间
				rs.put("updatePersonnel", maxVersionApp.getUpdatePersonnel());	//更新人员
				rs.put("downloadCount", maxVersionApp.getDownloadCount());		//下载次数
			
			//请求2，表示用户已成功下载app，下载次数累计+1
			}else if("2".equals(type)){		
				
				int count = maxVersionApp.getDownloadCount();
				count++;
				maxVersionApp.setDownloadCount(count);
				beanAppAndroidService.update(maxVersionApp);
				
				//更新下载次数后再次查询最新版本数据
				BeanAppAndroidUpdate newVersionApp = beanAppAndroidService.finMaxVersion();
					
				//给出最新版本的数据
				rs.put("appName", newVersionApp.getName());						
				rs.put("path", newVersionApp.getPath());						
				rs.put("versionName", newVersionApp.getVersionName());			
				rs.put("versionCode", newVersionApp.getVersionCode());			
				rs.put("updateDescription", newVersionApp.getRemark());			
				rs.put("updateDate", newVersionApp.getCreateTime());			
				rs.put("updatePersonnel", newVersionApp.getUpdatePersonnel());	
				rs.put("downloadCount", newVersionApp.getDownloadCount());		
			}
		} catch (Exception e) {
			e.printStackTrace();
			rs.setStatus(Contents.ERROR);
		}
		return rs;
	}
	


	/**
	 * ios应用版本更新
	 * 2018-04-24  huzi
	 * @param rs
	 * @return
	 */
	@RequestMapping("getAppIOSUpdate.aspf")
	@ResponseBody
	public Result getAppIOSUpdate(Result rs){
		try {
			
			//查询最新版本应用
			BeanAppIOSUpdate maxVersionApp = beanAppIOSService.finMaxVersion();
				
			//给出最新版本的数据
			rs.put("appName", maxVersionApp.getName());						//应用名称
			rs.put("versionName", maxVersionApp.getVersionName());			//版本名称
			rs.put("versionCode", maxVersionApp.getVersionCode());			//版本号
			rs.put("updateDescription", maxVersionApp.getRemark());			//更新说明
			rs.put("updateDate", maxVersionApp.getCreateTime());			//更新时间
			rs.put("updatePersonnel", maxVersionApp.getUpdatePersonnel());	//更新人员
				
		} catch (Exception e) {
			e.printStackTrace();
			rs.setStatus(Contents.ERROR);
		}
		return rs;
	}
	
	}



