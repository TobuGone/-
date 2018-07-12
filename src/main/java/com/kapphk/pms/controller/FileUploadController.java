package com.kapphk.pms.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kapphk.pms.bean.BeanMenuBook;
import com.kapphk.pms.dao.mapper.BeanMenuBookMapper;
import com.kapphk.pms.service.interFace.InterfaceBeanMenuBookService;
import com.kapphk.pms.util.Contents;
import com.kapphk.pms.util.FileUploadUtils;
import com.kapphk.pms.util.Result;
import com.kapphk.pms.util.ValidateUtils;

/**
 * 公用的文件上传
 * @author zoneyu 15-4-29
 */
@RestController
@RequestMapping("/upload/")
public class FileUploadController{
	
	@Autowired
	private InterfaceBeanMenuBookService menuBookService;
	
	@Autowired
	private BeanMenuBookMapper menuBookMapper;


	/**
	 * 公用的文件上传
	 */
	@RequestMapping("/fileUpload.aspf")
	public Result commonFileUpload(String photoData,String fileType,HttpServletRequest request){
		Result rs = new Result();
		try {
			rs = FileUploadUtils.commonFileUpload(photoData,fileType,request) ;
		} catch (Exception e) {
			e.printStackTrace();
			rs.setStatus("20001");
			rs.setMsg("出现错误") ;
		}
		return rs ;
	}
	
	/**
	 * 公用的文件上传（二进制流）
	 */
	@RequestMapping("/fileUploadByByte.aspf")
	public Result commonFileUploadByByte(HttpServletRequest request){
		Result rs = new Result();
		try {
			rs = FileUploadUtils.commonFileUploadByByte(request) ;
			
		} catch (Exception e) {
			e.printStackTrace();
			rs.setStatus("20001") ;
			rs.setMsg("出现错误") ;
		}
		return rs ;
	}
	
	/**
	 * pms的文件上传（二进制流）
	 * @throws UnsupportedEncodingException 
	 * @author huzi  修改于2018-04-25
	 * 
	 */
	@RequestMapping("/pmsFileUploadByByte.aspf")
	public Result pmsFileUploadByByte(Long userId,String fileName,Long type,HttpServletRequest request) throws UnsupportedEncodingException{
		
//		fileName = new String (fileName.getBytes("ISO8859-1"),"utf-8");//如果是增量更新到云服务器之前的项目中，需将此条代码注释掉
		Result rs = new Result();

		if(ValidateUtils.isBlank(userId) || ValidateUtils.isBlank(fileName) || ValidateUtils.isBlank(type)){
			rs.setStatus(Contents.PARAMS_ERROR);
			rs.setMsg("参数错误") ;
		}else{
			try {
				
				//查询用户是否已上传过该菜谱
				BeanMenuBook menuBook = new BeanMenuBook();
				menuBook.setUid(userId);
				menuBook.setName(fileName);
				List<BeanMenuBook> findAll = menuBookMapper.findAll(menuBook);
				
				//请求1	上传请求  后台做校验  查询所上传菜谱是否存在  存在则返回提示
				if (type == 1) {
					
					if (ValidateUtils.isBlank(menuBook.getId())) {
						if (findAll.size() > 0) {
							rs.setStatus(Contents.EXIST);
							rs.setMsg("该菜谱已存在");
							return rs;
						}
					} 
					else if (!ValidateUtils.isEmptyForCollection(findAll)) {
							rs.setStatus(Contents.EXIST);
							rs.setMsg("该菜谱已存在");
							return rs;
					}
					
					//上传pms文件
					rs = FileUploadUtils.pmsFileUploadByByte(request) ;
					//根据pms文件生成菜谱
					menuBookService.saveCustomMenuBookByPath(userId,fileName,type,rs);
				}
				//请求2	覆盖请求  删除已存在的重新添加
				else if (type == 2) {
					
					//先删除用户之前上传的菜谱
					menuBookService.deleteUserMenuBookFile(menuBook, rs);
					rs = FileUploadUtils.pmsFileUploadByByte(request) ;
					menuBookService.saveCustomMenuBookByPath(userId,fileName,type,rs);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				rs.setStatus("20001") ;
				rs.setMsg("出现错误") ;
			}	
		}
		return rs ;
	}
	
	/**
	 * pms的文件上传（二进制流）
	 */
	@RequestMapping("/pmsFileTemporary.aspf")
	public Result pmsFileTemporary(Long userId,String fileName,HttpServletRequest request){
		Result rs = new Result();
//		userId=(long) 1;
//		fileName="红烧排骨v1";
//		scId = (long) 40;
		if(ValidateUtils.isBlank(userId) || ValidateUtils.isBlank(fileName)){
			rs.setStatus(Contents.PARAMS_ERROR);
			rs.setMsg("参数错误") ;
		}else{
			try {
				//上传pms文件
				rs = FileUploadUtils.pmsFileUploadByByte(request) ;
				//根据pms文件生成菜谱
				menuBookService.saveFileTemporaryh(userId,fileName,rs);
			} catch (Exception e) {
				e.printStackTrace();
				rs.setStatus("20001") ;
				rs.setMsg("出现错误") ;
			}	
		}
		return rs ;
	}
	
	/**
	 * 临时菜谱文件上传
	 * @param userId
	 * @param fileName
	 * @return
	 */
	@RequestMapping("/pmsFileCheck.aspf")
	public Result pmsFileCheck(Long userId,String fileName){
		Result rs = new Result();
		if(ValidateUtils.isBlank(userId) || ValidateUtils.isBlank(fileName)){
			rs.setStatus(Contents.PARAMS_ERROR);
			rs.setMsg("参数错误") ;
		}
		BeanMenuBook book = new BeanMenuBook();
		book.setName(fileName);
		book.setUid(userId);
		book.setStatus(2);
		List<BeanMenuBook> list = menuBookMapper.findByAll(book);
		if(ValidateUtils.isEmptyForCollection(list)){
			rs.put("count", 1);
		}else{
			rs.put("count", 2);
		}
		return rs;
	}
	
	
	/**
	 * 用户分享所产生pms文件的上传（二进制流）
	 * @author  huzi
	 * @date    2018年6月19日	
	 * @param 	userId		用户id
	 * @param 	fileName   .pms文件名
	 * @param 	request		接收.pms文件
	 * @return
	 */
	@RequestMapping("/sharePmsFileUploadByByte.aspf")
	public Result sharePmsFileUploadByByte(Long userId,String fileName,HttpServletRequest request){
		Result rs = new Result();
		
		if(ValidateUtils.isBlank(userId) || ValidateUtils.isBlank(fileName)){
			rs.setStatus(Contents.PARAMS_ERROR);
			rs.setMsg("参数错误") ;
			
		}else{
			try {
				rs = FileUploadUtils.pmsFileUploadByByte(request) ;	//上传pms文件
				menuBookService.saveCustomMenuBookShareByPath(userId,fileName,rs);//根据pms文件生成菜谱
				
			} catch (Exception e) {
				e.printStackTrace();
				rs.setStatus("20001") ;
				rs.setMsg("出现错误") ;
			}	
		}
		return rs ;
	}
}
