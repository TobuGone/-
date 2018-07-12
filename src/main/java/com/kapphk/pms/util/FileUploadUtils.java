package com.kapphk.pms.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 文件上传工具类
 * @author zoneyu 14-6-9
 */
public class FileUploadUtils {

	private FileUploadUtils(){}

	/**
	 * 公用的文件上传
	 */
	public static Result commonFileUpload(String photoData, String fileType,HttpServletRequest request) {
		Result rs = new Result();
		if(!ValidateUtils.isBlank(photoData) && !ValidateUtils.isBlank(fileType)){
			//目标文件路径
			String targetPath_l = "";
			String targetPath_s = "";
			String visitePath = "" ;//放问路径
			String path = FileUploadUtils.class.getClassLoader().getResource("../../").getPath() ;
			boolean b = false ;
			ZipUtils zip = new ZipUtils() ;
			if("1".equals(fileType)){//头像  /upload/logPath
				path = path +"upload/logPath" ;
				String radom = DateUtils.getLocalDate("yyyyMMddHHmmss") + DataUtils.getRadom(8) ;
				visitePath = "/upload/logPath/"+radom+".png" ;
				targetPath_l = path +"/"+radom+".png" ;
				b = ImageBase64Util.GenerateImage(photoData, targetPath_l) ;
				//压缩小图
				zip.compressPic(path+"/", path+"/", radom+".png", radom+".png", 300, 300, true) ;
			}else if("2".equals(fileType)){//证件类，同意放在papers目录
				path = path +"upload/papers" ;
				String radom = DateUtils.getLocalDate("yyyyMMddHHmmss") + DataUtils.getRadom(8) ;
				String filename_l = radom+"-l.png" ;
				String filename_s = radom+"-s.png" ;
				visitePath = "/upload/papers/"+filename_s ;
				targetPath_l = path +"/"+filename_l ; //原图
				targetPath_s = path +"/"+filename_s ; //小图 
				ImageBase64Util.GenerateImage(photoData, targetPath_l) ;
				b = ImageBase64Util.GenerateImage(photoData, targetPath_s) ;
				//压缩小图
				zip.compressPic(path+"/", path+"/", filename_s, filename_s, 300, 300, true) ;
			}else if("3".equals(fileType)){
				//头像  /upload/logPath
				path = path +"upload/banner" ;
				String radom = DateUtils.getLocalDate("yyyyMMddHHmmss") + DataUtils.getRadom(8) ;
				visitePath = "/upload/banner/"+radom+".png" ;
				targetPath_l = path +"/"+radom+".png" ;
				b = ImageBase64Util.GenerateImage(photoData, targetPath_l) ;
			}
			if(!b){
				rs.setStatus(Contents.ERROR) ;
				rs.setMsg("上传失败") ;
			}else{
				System.out.println("路径："+visitePath);
				rs.put("path", visitePath) ;
			}
		}else{
			rs.setStatus(Contents.PARAMS_ERROR) ;
			rs.setMsg("参数错误") ;
		}
		return rs ;
	}

	static long maxSize = 5240880;
	
	/**
	 * 公用的文件上传（二进制流）
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Result commonFileUploadByByte(HttpServletRequest request) throws Exception {
		System.out.println("二进制上传进入。。。。。。。。。。。。。。。。。。。。。。。。。。");
		
		Result rs = new Result();
		String visitePath = "" ;
		Map<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		
		String dirName = request.getParameter("dir");
		if (dirName == null){
			dirName = "image";
		}
		
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request ;
		Iterator iter = multiRequest.getFileNames();
		while (iter.hasNext()) {
			MultipartFile file = multiRequest.getFile((String) iter.next());
			if(file != null){
				String fileName = file.getOriginalFilename();
				//判断文件大小
				if(file.getSize() > maxSize) {
					rs.setStatus(Contents.PARAMS_ERROR) ;
					rs.setMsg("上传文件大小超过5M") ;
					return rs ;
				}
				
				//检查扩展名
				String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
				if(!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt)){
					rs.setStatus(Contents.PARAMS_ERROR) ;
					rs.setMsg("上传文件扩展名是不允许的扩展名"+fileExt+",只允许" + extMap.get(dirName) + "格式") ;
					return rs ;
				}
				
				String path = FileUploadUtils.class.getClassLoader().getResource("../../").getPath() ;  
				String path1 = "/upload/public/"+DateUtils.getLocalDate("yyyyMMdd") ;//文件的上级目录
				path = path + path1;
				File file1 = new File(path) ;
				if(!file1.exists()){//不存在就创建目录
					file1.mkdir() ;
				}
				
				//文件路径
				String fileName2 = DateUtils.getLocalDate("yyyyMMddHHmmss")+DataUtils.getRadom(8)+".png" ;
				String filePath = path + "/" + fileName2;
				visitePath += path1+"/"+fileName2+"|" ;//访问路径
				file.transferTo(new File(filePath));
			}
		}
		if(!ValidateUtils.isBlank(visitePath)){
			visitePath = visitePath.substring(0, visitePath.length() - 1) ;
		}
		System.out.println("路径："+visitePath);
		rs.put("path", visitePath) ;
		return rs ;
	}
	
	/**
	 * pms的文件上传（二进制流）
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Result pmsFileUploadByByte(HttpServletRequest request) throws Exception {
		System.out.println("二进制上传进入。。。。。。。。。。。。。。。。。。。。。。。。。。");
		
		Result rs = new Result();
		String visitePath = "" ;
		
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
		Iterator iter = multiRequest.getFileNames();
		while (iter.hasNext()) {
			MultipartFile file = multiRequest.getFile((String) iter.next());
			if(file != null){
				String fileName = file.getOriginalFilename();
//				//判断文件大小
//				if(file.getSize() > maxSize) {
//					rs.setStatus(Contents.PARAMS_ERROR) ;
//					rs.setMsg("上传文件大小超过5M") ;
//					return rs ;
//				}
				
				//检查扩展名
				String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
				if(!fileExt.equals("pms")){
					rs.setStatus(Contents.PARAMS_ERROR) ;
					rs.setMsg("上传文件扩展名是不允许的扩展名"+fileExt+",只允许.pms格式") ;
					return rs ;
				}
				
				String path = FileUploadUtils.class.getClassLoader().getResource("../../").getPath() ;  
				String path1 = "/upload/menupms/"+DateUtils.getLocalDate("yyyyMMdd") ;//文件的上级目录
				path = path + path1;
				File file1 = new File(path) ;
				if(!file1.exists()){//不存在就创建目录
					file1.mkdirs() ;
				}
				
				//文件路径
				String fileName2 = DateUtils.getLocalDate("yyyyMMddHHmmss")+DataUtils.getRadom(8)+".pms" ;
				String filePath = path + "/" + fileName2;
				visitePath += path1+"/"+fileName2+"|" ;//访问路径
				file.transferTo(new File(filePath));
			}
		}
		if(!ValidateUtils.isBlank(visitePath)){
			visitePath = visitePath.substring(0, visitePath.length() - 1) ;
		}
		System.out.println("路径："+visitePath);
		rs.put("path", visitePath) ;
		return rs ;
	}
	
	/**
	 * 图片上传(web)
	 * @param logPath           //文件原路径
	 * @param targetPath        //目标文件夹，比如: photo/user
	 * @param type              //类型，0：不压缩，1：头像，压缩成300*300，2：轮播图，压缩成480*210
	 * @return
	 */
	public static String uploadFile(byte[] bytes, String targetPath, 
			String type, HttpServletRequest request) throws Exception {
		String visitePath = "" ;//放问路径
		if(!ValidateUtils.isEmpty(bytes) && !ValidateUtils.isBlank(type)){
			//获取项目更路径
			//String webRoot = getIP.getWebRoot(request) ;
			//目标文件路径
			String targetPath_l = "";
			String targetPath_s = "";
			String path = FileUploadUtils.class.getClassLoader().getResource("../../").getPath() ;
			if("0".equals(type)){//证件类，同意放在papers目录
				path = path +"/"+ targetPath ;
				File file = new File(path) ;
				if(!file.exists()) file.mkdir() ;
				
				String radom = DateUtils.getLocalDate("yyyyMMddHHmmss") + DataUtils.getSixRadom() ;
				String filename_l = radom+"-l.png" ;
				String filename_s = radom+"-s.png" ;
				visitePath = targetPath+"/"+filename_s ;
				targetPath_l = path +"/"+filename_l ; //大图
				targetPath_s = path +"/"+filename_s ; //小图
				copyFile(bytes, targetPath_l) ; //大图
				copyFile(bytes, targetPath_s, path,filename_s,300,300) ;
			}else if("1".equals(type)){//头像
				path = path + targetPath ;
				File file = new File(path) ;
				if(!file.exists()) file.mkdir() ;
				
				String radom = DateUtils.getLocalDate("yyyyMMddHHmmss") + DataUtils.getSixRadom() ;
				String fileName = radom+".png" ;
				targetPath_s = path + "/" + fileName ;
				visitePath = targetPath+"/"+fileName ;
				copyFile(bytes, targetPath_s, path,fileName,300,300) ;
			}else if("2".equals(type)){//轮播图
				path = path + targetPath ;
				File file = new File(path) ;
				if(!file.exists()) file.mkdir() ;
				
				String radom = DateUtils.getLocalDate("yyyyMMddHHmmss") + DataUtils.getSixRadom() ;
				String fileName = radom+".png" ;
				targetPath_s = path + "/" + fileName ;
				visitePath = targetPath+"/"+fileName ;
				copyFile(bytes, targetPath_s, path,fileName) ;
			}
		}
		return visitePath ;
	} ;
	
	/**
	 * 写出数据到文件
	 * @param srcPath                //原文件路径
	 * @param targetPath             //目标文件路径
	 * @param args                   //可变参数，用于图片压缩，传入则压缩，不传，不压缩。参数位置必须是，第一个位置为path，
	 * 类型为String（比如：../gsf-web/photo/user）,
	 * 第二个位置为文件名，类型为String（比如：20141222345678-l.png）,第三个为图片的宽度，类型为int（比如：300*300）
	 */
	public static void copyFile(byte[] bytes,String targetPath,Object... args) throws Exception {
		FileOutputStream outStream = new FileOutputStream(new File(targetPath));
		outStream.write(bytes) ;
		outStream.close() ;
		
		if(!ValidateUtils.isempty(args)){
			if(args.length == 4){
				String path = (String) args[0] ;
				String fileName = (String) args[1] ;
				int width = (Integer) args[2] ;
				int height = (Integer) args[3] ;
				//添加图片压缩
				ZipUtils zip = new ZipUtils() ;
				zip.compressPic(path+"/", path+"/", fileName, fileName, width, height, true) ;
			}
		}
	}
	
	
	
	
static long maxVideoSize = 52428800;
	
	/**
	 * 公用的文件上传（二进制流）
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Result commonFileUploadVideoByByte(HttpServletRequest request,String tpath) throws Exception {
		Result rs = new Result();
		
		String dirName = request.getParameter("dir");
		
		if (dirName == null){
			dirName = "pmsFile";
		}
		
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request ;
		Iterator iter = multiRequest.getFileNames();
		while (iter.hasNext()) {
			MultipartFile file = multiRequest.getFile((String) iter.next());
			if(!file.isEmpty()){
				String fileName = file.getOriginalFilename();
				//判断文件大小
				if(file.getSize() > maxVideoSize) {
					rs.setStatus("20001");
					rs.setMsg("上传文件大小超过50M") ;
					return rs ;
				}
				
				String path = FileUploadUtils.class.getClassLoader().getResource("../../").getPath() ;  
				String path1 = tpath+"/"+DateUtils.getLocalDate("yyyyMMdd") ;//文件的上级目录
				path = path + path1;
				File file1 = new File(path) ;
				if(!file1.exists()){//不存在就创建目录
					file1.mkdirs() ;
				}
				
				String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
				
				//文件路径
				String fileName2 = DateUtils.getLocalDate("yyyyMMddHHmmss")+DataUtils.getRadom(8)+"."+fileExt ;
				
				String filePath = path + "/" + fileName2;
				
				file.transferTo(new File(filePath));
				
				rs.put("savePath",filePath);
			}
		}
	
		return rs ;
	}
	
	
	
	
}
