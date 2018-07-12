package com.kapphk.pms.service.web.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kapphk.pms.bean.BeanAppAndroidUpdate;
import com.kapphk.pms.dao.mapper.BeanAppAndroidUpdateMapper;
import com.kapphk.pms.service.BaseServiceImpl;
import com.kapphk.pms.service.web.BeanAppAndroidService;
import com.kapphk.pms.util.Contents;
import com.kapphk.pms.util.DateUtils;
import com.kapphk.pms.util.FileUploadUtils;
import com.kapphk.pms.util.Result;
import com.kapphk.pms.util.ValidateUtils;
@Service("beanAppAndroidUpdateService")
public class BeanAppAndroidServiceImpl extends BaseServiceImpl<BeanAppAndroidUpdate,Long> implements BeanAppAndroidService {

	@Autowired
	private BeanAppAndroidUpdateMapper appAndroidUpdateMapper;
	
	@Override
	public void init() {
		super.setMapper(appAndroidUpdateMapper);
	}
	
	/**
	 * 查询更新版本列表
	 * @param appAndroidUpdate
	 * @param versionName 版本名称
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param page 
	 * @param rows
	 * @return
	 */
	@Override
	public Result getAppAndroidList(BeanAppAndroidUpdate appAndroidUpdate,
			String versionName, String startTime, String endTime, int page,int rows) {
		
		Result rs = new Result();	
		
		List<Map<String,Object>> list = appAndroidUpdateMapper.getAppAndroidList(appAndroidUpdate, versionName, startTime, endTime, (page-1)*rows, rows);
		int count = appAndroidUpdateMapper.getAppAndroidCount(appAndroidUpdate, versionName, startTime, endTime, (page-1)*rows, rows);
		rs.put("total", count);
		rs.put("rows", list);
		
		return rs;
	}

	
	/**
	 * 新增或修改版本文件  
	 * @param appAndroidUpdate
	 * @param file
	 * @return
	 * 修改于2018-04-19  huzi
	 */
	@Override
	public Result saveAppAndroid(BeanAppAndroidUpdate appAndroidUpdate, MultipartFile file) {
		
		Result rs = new Result();
		
		//新增
		if(ValidateUtils.isBlank(appAndroidUpdate.getId())){
			String originalFilename = file.getOriginalFilename();	
			String fileType = file.getOriginalFilename().substring(originalFilename.lastIndexOf("."), originalFilename.length());
			String path1 = FileUploadUtils.class.getClassLoader().getResource("../..").getPath();
			String path2 = "/upload/appAndroid/" + DateUtils.getLocalDate("yyyyMMddHHmmss") + new Random().nextInt(6) + fileType;
			FileOutputStream outStream = null;
			File f = new File(path1+"/upload/appAndroid");
			if(!f.exists()){	//如果目录不存在，则创建
				f.mkdirs();
			}
			try {
				outStream = new FileOutputStream(path1 + path2);
				outStream.write(file.getBytes()) ;
				outStream.flush();
				outStream.close() ;
			} catch (IOException e) {
				e.printStackTrace();
			}
			appAndroidUpdate.setPath(path2);							//文件路径
			appAndroidUpdate.setCreateTime(DateUtils.getLocalDate());	//更新时间
			appAndroidUpdateMapper.insert(appAndroidUpdate);
			
		}
		//修改
		else {
			BeanAppAndroidUpdate vid = appAndroidUpdateMapper.findById(appAndroidUpdate.getId());
			if(vid != null || appAndroidUpdate.getId().equals(vid) ){
				String originalFilename = file.getOriginalFilename();//获取原始文件名
				if (originalFilename != "") {
				
					String fileType = file.getOriginalFilename().substring(originalFilename.lastIndexOf("."), originalFilename.length());
					String path1 = FileUploadUtils.class.getClassLoader().getResource("../..").getPath();
					String path2 = "/upload/appAndroid/" + DateUtils.getLocalDate("yyyyMMddHHmmss") + new Random().nextInt(6) + fileType;
					FileOutputStream outStream = null;
					File f = new File(path1+"/upload/appAndroid");
					if(!f.exists()){	//如果目录不存在，则创建
						f.mkdirs();
					}
					try {
						outStream = new FileOutputStream(path1 + path2);
						outStream.write(file.getBytes()) ;
						outStream.flush();
						outStream.close() ;
					} catch (IOException e) {
						e.printStackTrace();
					}
					appAndroidUpdate.setPath(path2);//文件路径	
				}
				appAndroidUpdate.setCreateTime(DateUtils.getLocalDate());//更新时间					
				appAndroidUpdateMapper.update(appAndroidUpdate);
				
			}
			
		}
		return rs;
	}

	
	
	
	/**
	 * 批量删除
	 * @param asList
	 * @return
	 */
	@Override
	public Result delAppAndroid(List<Long> asList) {
		Result rs = new Result();
		
		if(!ValidateUtils.isEmptyForCollection(asList)){
			int count = appAndroidUpdateMapper.deletes(asList);
			rs.put("count", count);
		}else{
			rs.setStatus(Contents.ERROR);
			rs.setMsg("参数有误");
			return rs;
		}
		
		return rs;
	}

	
	/**
	 * 根据版本名称查询数据
	 */
	@Override
	public BeanAppAndroidUpdate findByVersionName(String versionName) {
		return appAndroidUpdateMapper.findByVersionName(versionName);
		
	}

	
	/**
	 * 查询最新版本应用
	 */
	@Override
	public BeanAppAndroidUpdate finMaxVersion() {
		return appAndroidUpdateMapper.finMaxVersion();
	}

	
	
}
