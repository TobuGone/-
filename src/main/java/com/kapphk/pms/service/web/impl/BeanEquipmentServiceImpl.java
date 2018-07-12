package com.kapphk.pms.service.web.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kapphk.pms.bean.BeanEquipmentUpdate;
import com.kapphk.pms.dao.mapper.BeanEquipmentUpdateMapper;
import com.kapphk.pms.service.BaseServiceImpl;
import com.kapphk.pms.service.web.BeanEquipmentService;
import com.kapphk.pms.util.Contents;
import com.kapphk.pms.util.DateUtils;
import com.kapphk.pms.util.FileRead;
import com.kapphk.pms.util.FileUploadUtils;
import com.kapphk.pms.util.Result;
import com.kapphk.pms.util.ValidateUtils;
import com.kapphk.pms.util.ZipUtils;
@Service("beanEquipmentService")
public class BeanEquipmentServiceImpl extends BaseServiceImpl<BeanEquipmentUpdate,Long> implements BeanEquipmentService {

	@Autowired
	private BeanEquipmentUpdateMapper equipmentUpdateMapper;
	
	@Override
	public void init() {
		super.setMapper(equipmentUpdateMapper);
	}
	
	/**
	 * 查询更新版本列表
	 * @param equipmentUpdate
	 * @param versionCode 版本号
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param page 
	 * @param rows
	 * @return
	 */
	@Override
	public Result getEquipmentList(BeanEquipmentUpdate equipmentUpdate,
			String versionCode, String startTime, String endTime, int page,int rows) {
		Result rs = new Result();	
		
		List<Map<String,Object>> list = equipmentUpdateMapper.getEquipmentList(equipmentUpdate, versionCode, startTime, endTime, (page-1)*rows, rows);
		int count = equipmentUpdateMapper.getEquipmentCount(equipmentUpdate, versionCode, startTime, endTime, (page-1)*rows, rows);
		rs.put("total", count);
		rs.put("rows", list);
		
		return rs;
	}
	
	/**
	 * 保存上传的版本文件	2018-04-17  huzi
	 * @param equipmentUpdate
	 * @param file
	 * @return
	 */
	@Override
	public Result saveEquipment(BeanEquipmentUpdate equipmentUpdate, MultipartFile file) {

		Result rs = new Result();
		
		//新增
		if(ValidateUtils.isBlank(equipmentUpdate.getId())){
			String originalFilename = file.getOriginalFilename();
//			String fileName = originalFilename.substring(0, originalFilename.lastIndexOf("."));
			String fileType = file.getOriginalFilename().substring(originalFilename.lastIndexOf("."), originalFilename.length());
			String path1 = FileUploadUtils.class.getClassLoader().getResource("../..").getPath();
			String path2 = "/upload/equipment/" + DateUtils.getLocalDate("yyyyMMddHHmmss") + new Random().nextInt(6) + fileType;
			FileOutputStream outStream = null;
			File f = new File(path1+"/upload/equipment");
			if(!f.exists()){
				f.mkdirs();
			}
			try {
				
				String path = path1 + path2;
				outStream = new FileOutputStream(path);
				outStream.write(file.getBytes()) ;
				outStream.flush();
				outStream.close();
				
				if (fileType.equals(".zip")) {
					String dataPath = "/upload/equipment/" + DateUtils.getLocalDate("yyyyMMddHHmmss");
					String unzipFilePath = path1 + dataPath;//解压后文件存放路径
					ZipUtils.unzip(path, unzipFilePath, false);//解压zip
					
					equipmentUpdate.setPath(dataPath);//将解压文件路径更新至数据库
				}
				else {
					equipmentUpdate.setPath(path2);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
//			equipmentUpdate.setName(fileName);
			equipmentUpdate.setCreateTime(DateUtils.getLocalDate());
			
			
			//排除重复添加
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("name", equipmentUpdate.getName());
			map.put("hardwareVersion", equipmentUpdate.getHardwareVersion());
			map.put("softwareVersion", equipmentUpdate.getSoftwareVersion());
			map.put("versionCode", equipmentUpdate.getVersionCode());
			BeanEquipmentUpdate eu = equipmentUpdateMapper.findByHsv(map);
			
			if (eu != null) {
				rs.setStatus(Contents.EXIST);
				rs.setMsg("硬件型号、软件型号、版本号与已有记录冲突");
			}
			else {
				equipmentUpdateMapper.insert(equipmentUpdate);
			}
		}
		//修改
		else {
			BeanEquipmentUpdate vid = equipmentUpdateMapper.findById(equipmentUpdate.getId());
			if(vid != null || equipmentUpdate.getId().equals(vid) ){
				
				String originalFilename = file.getOriginalFilename();
				if (originalFilename != "") {
					
					String fileType = file.getOriginalFilename().substring(originalFilename.lastIndexOf("."), originalFilename.length());
					String path1 = FileUploadUtils.class.getClassLoader().getResource("../..").getPath();
					String path2 = "/upload/equipment/" + DateUtils.getLocalDate("yyyyMMddHHmmss") + new Random().nextInt(6) + fileType;
					
					//更新前删除之前存在的解压文件
					String old = path1 + vid.getPath();
					File oldFile = new File(old);
					FileRead.removeDir(oldFile);
					
					FileOutputStream outStream = null;
					File f = new File(path1+"/upload/equipment");
					if(!f.exists()){
						f.mkdirs();
					}
					try {
						
						String path = path1 + path2;
						outStream = new FileOutputStream(path);
						outStream.write(file.getBytes()) ;
						outStream.flush();
						outStream.close() ;
						
						if (fileType.equals(".zip")) {
							String dataPath = "/upload/equipment/" + DateUtils.getLocalDate("yyyyMMddHHmmss");
							String unzipFilePath = path1 + dataPath;//解压后文件存放路径
							ZipUtils.unzip(path, unzipFilePath, false);//解压zip
							
							equipmentUpdate.setPath(dataPath);//将解压文件路径更新至数据库
						}
						else {
							equipmentUpdate.setPath(path2);
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				equipmentUpdate.setCreateTime(DateUtils.getLocalDate());
				equipmentUpdateMapper.update(equipmentUpdate);
			}
		}
		return rs;
	}
	

	/**
	 * 批量删除   2018-07-02  huzi
	 * @param asList
	 * @return
	 */
	@Override
	public Result delEquipment(List<Long> asList) {
		Result rs = new Result();
		String filePath;
		String path = FileUploadUtils.class.getClassLoader().getResource("../..").getPath();//项目路径
		
		if(!ValidateUtils.isEmptyForCollection(asList)){
			
			for (Long id : asList) {	
				BeanEquipmentUpdate eq = equipmentUpdateMapper.findById(id);
				filePath = path + eq.getPath();
				File file = new File(filePath);
				if (file.exists()) { 
					FileRead.removeDir(file);						//删除目录文件			
				}
			}
			
			int count = equipmentUpdateMapper.deletes(asList);		//删除记录
			rs.put("count", count);
			
		}else{
			rs.setStatus(Contents.ERROR);
			rs.setMsg("参数有误");
			return rs;
		}
		
		return rs;
	}


	/**
	 * 根据硬件版本、软件版本、版本号 查询数据
	 * @author huzi 2018年05月15日
	 */
	@Override
	public BeanEquipmentUpdate findByHardwareAndSoftwar(Map<String, Object> condition) {
		return equipmentUpdateMapper.findByHardwareAndSoftwar(condition);
	}

	
	/**
	 * 查询最新版本应用
	 * @author huzi 2018年05月15日
	 */
	@Override
	public BeanEquipmentUpdate finMaxVersionCode() {
		return equipmentUpdateMapper.finMaxVersionCode();
	}	

	
}
