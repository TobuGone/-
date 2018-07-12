package com.kapphk.pms.service.web.impl;

/**
 * @author 胡子
 * since 2018年6月5日
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kapphk.pms.bean.BeanEquipmentUpdateOld;
import com.kapphk.pms.dao.mapper.BeanEquipmentUpdateOldMapper;
import com.kapphk.pms.service.BaseServiceImpl;
import com.kapphk.pms.service.web.BeanEquipmentOldService;
import com.kapphk.pms.util.Contents;
import com.kapphk.pms.util.DateUtils;
import com.kapphk.pms.util.FileUploadUtils;
import com.kapphk.pms.util.Result;
import com.kapphk.pms.util.ValidateUtils;
@Service("beanEquipmentOldService")
public class BeanEquipmentOldServiceImpl extends BaseServiceImpl<BeanEquipmentUpdateOld,Long> implements BeanEquipmentOldService {

	@Autowired
	private BeanEquipmentUpdateOldMapper equipmentUpdateOldMapper;
	
	@Override
	public void init() {
		super.setMapper(equipmentUpdateOldMapper);
	}
	
	/**
	 * 查询更新版本列表
	 * @param equipmentUpdateOld
	 * @param versionsName 版本名称
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param page 
	 * @param rows
	 * @return
	 */
	@Override
	public Result getEquipmentList(BeanEquipmentUpdateOld equipmentUpdateOld,
			String versionsName, String startTime, String endTime, int page,int rows) {
		Result rs = new Result();	
		
		List<Map<String,Object>> list = equipmentUpdateOldMapper.getEquipmentList(equipmentUpdateOld, versionsName, startTime, endTime, (page-1)*rows, rows);
		int count = equipmentUpdateOldMapper.getEquipmentCount(equipmentUpdateOld, versionsName, startTime, endTime, (page-1)*rows, rows);
		rs.put("total", count);
		rs.put("rows", list);
		
		return rs;
	}

	/**
	 * 保存或修改上传的版本文件
	 * @param equipmentUpdateOld
	 * @param file
	 * @return
	 */
	@Override
	public Result saveEquipment(BeanEquipmentUpdateOld equipmentUpdateOld, MultipartFile file) {
				Result rs = new Result();
				
				//新增
				if(ValidateUtils.isBlank(equipmentUpdateOld.getId())){
					String originalFilename = file.getOriginalFilename();
//					String fileName = originalFilename.substring(0, originalFilename.lastIndexOf("."));
					String fileType = file.getOriginalFilename().substring(originalFilename.lastIndexOf("."), originalFilename.length());
					String path1 = FileUploadUtils.class.getClassLoader().getResource("../..").getPath();
					String path2 = "/upload/equipment/" + DateUtils.getLocalDate("yyyyMMddHHmmss") + new Random().nextInt(6) + fileType;
					FileOutputStream outStream = null;
					File f = new File(path1+"/upload/equipment");
					if(!f.exists()){
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
//					equipmentUpdate.setName(fileName);
					equipmentUpdateOld.setPath(path2);
					equipmentUpdateOld.setCreateTime(DateUtils.getLocalDate());
					equipmentUpdateOldMapper.insert(equipmentUpdateOld);
				}
				//修改
				else {
					BeanEquipmentUpdateOld vid = equipmentUpdateOldMapper.findById(equipmentUpdateOld.getId());
					if(vid != null || equipmentUpdateOld.getId().equals(vid) ){
						
						String originalFilename = file.getOriginalFilename();
						if (originalFilename != "") {
							
							String fileType = file.getOriginalFilename().substring(originalFilename.lastIndexOf("."), originalFilename.length());
							String path1 = FileUploadUtils.class.getClassLoader().getResource("../..").getPath();
							String path2 = "/upload/equipment/" + DateUtils.getLocalDate("yyyyMMddHHmmss") + new Random().nextInt(6) + fileType;
							FileOutputStream outStream = null;
							File f = new File(path1+"/upload/equipment");
							if(!f.exists()){
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
							
							String filePath = path1 + vid.getPath();
							File oldFile = new File(filePath);
							if (oldFile.exists()) { 
								oldFile.delete();						//删除旧文件			
							}
							
							equipmentUpdateOld.setPath(path2);
						
						}
						equipmentUpdateOld.setCreateTime(DateUtils.getLocalDate());
						equipmentUpdateOldMapper.update(equipmentUpdateOld);
						
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
	public Result delEquipment(List<Long> asList) {
		Result rs = new Result();
		String filePath;
		String path = FileUploadUtils.class.getClassLoader().getResource("../..").getPath();//项目路径
		
		if(!ValidateUtils.isEmptyForCollection(asList)){
			
			for (Long id : asList) {	
				BeanEquipmentUpdateOld eq = equipmentUpdateOldMapper.findById(id);
				filePath = path + eq.getPath();
				File file = new File(filePath);
				if (file.exists()) { 
					file.delete();								  //删除目录文件			
				}
			}
			
			int count = equipmentUpdateOldMapper.deletes(asList); //删除记录
			rs.put("count", count);
		}else{
			rs.setStatus(Contents.ERROR);
			rs.setMsg("参数有误");
			return rs;
		}
		
		return rs;
	}

	
}
