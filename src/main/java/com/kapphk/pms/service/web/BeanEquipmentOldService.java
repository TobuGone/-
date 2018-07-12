package com.kapphk.pms.service.web;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kapphk.pms.bean.BeanEquipmentUpdateOld;
import com.kapphk.pms.service.BaseService;
import com.kapphk.pms.util.Result;
/**
 * @author 胡子
 * since 2018年6月5日
 */
public interface BeanEquipmentOldService extends BaseService<BeanEquipmentUpdateOld, Long> {
	
	/**
	 * 查询更新版本列表
	 * @param equipmentUpdate
	 * @param versionsName 版本名称
	 * @param startTime 开始时间
	 * @param endTime 结束时间w
	 * @param page 
	 * @param rows
	 * @return
	 */
	public Result getEquipmentList(BeanEquipmentUpdateOld equipmentUpdateOld,
			String versionsName, String startTime, String endTime, int page,
			int rows);

	/**
	 * 保存上传的版本文件
	 * @param equipmentUpdate
	 * @param file
	 * @return
	 */
	public Result saveEquipment(BeanEquipmentUpdateOld equipmentUpdateOld,
			MultipartFile file);

	/**
	 * 批量删除
	 * @param asList
	 * @return
	 */
	public Result delEquipment(List<Long> asList);

}
