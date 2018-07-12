package com.kapphk.pms.service.web.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kapphk.pms.bean.BeanConnectGuide;
import com.kapphk.pms.dao.mapper.BeanConnectGuideMapper;
import com.kapphk.pms.service.BaseServiceImpl;
import com.kapphk.pms.service.web.BeanConnectGuideService;
import com.kapphk.pms.util.DateUtils;
import com.kapphk.pms.util.FileUploadUtils;
import com.kapphk.pms.util.Result;
import com.kapphk.pms.util.ValidateUtils;
@Service("beanConnectGuideService")
public class BeanConnectGuideServiceImpl extends BaseServiceImpl<BeanConnectGuide, Long>implements BeanConnectGuideService {
	
	@Autowired
	private BeanConnectGuideMapper connectGuideMapper;

	@Override
	public void init() {
		super.setMapper(connectGuideMapper);
	}
	/**
	 * 查询连接指南列表
	 * @param page 页数
	 * @param rows 每页显示的行数
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-13
	 */
	@Override
	public Result getConnectList(int page, int rows) throws Exception {
		Result rs = new Result();
		
		BeanConnectGuide guide = new BeanConnectGuide();
		
		List<BeanConnectGuide> list = connectGuideMapper.findByPage(guide, (page-1)*rows, rows);
		
		int count = connectGuideMapper.findByPageCount(guide);
		
		rs.put("total", count);
		rs.put("rows", list);
		
		
		return rs;
	}
	
	/**
	 * 新增或修改连接指南
	 * @param beanConnectGuide 对象
	 * @param file 文件流
	 * @param req 请求参数
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-13
	 */
	@Override
	public Result saveConnect(BeanConnectGuide beanConnectGuide,
			MultipartFile file, HttpServletRequest req) throws Exception {
		Result rs = new Result();
		
		//新增
		if(ValidateUtils.isBlank(beanConnectGuide.getId())){
			//上传图片
			if(!file.isEmpty()){
				byte[] bytes = file.getBytes() ;
				String logPath = FileUploadUtils.uploadFile(bytes,"upload/connect","2",req) ;
				beanConnectGuide.setLogoPath("/"+logPath);
			}
			
			beanConnectGuide.setCreateTime(DateUtils.getLocalDate("yyyy-MM-dd HH:mm:ss"));
			
			connectGuideMapper.insert(beanConnectGuide);
		}
		//修改
		else{
			//上传图片
			if(!file.isEmpty()){
				byte[] bytes = file.getBytes() ;
				String logPath = FileUploadUtils.uploadFile(bytes,"upload/connect","2",req) ;
				beanConnectGuide.setLogoPath("/"+logPath);
			}
			beanConnectGuide.setCreateTime(DateUtils.getLocalDate("yyyy-MM-dd HH:mm:ss"));
			connectGuideMapper.update(beanConnectGuide);
		}
		
		return rs;
	}
	
	/**
	 * 批量删除
	 * @param ids id集合
	 * @return
	 * @throws Exception
	 * @author huangzh 2015-12-22
	 */
	@Override
	public Result delConnect(String ids) throws Exception {
		Result rs = new Result();
		
		if(!ValidateUtils.isBlank(ids)){
			List<Long> idList = new ArrayList<Long>();
			
			String [] strids = ids.split(",");
			
			for(String id:strids){
				idList.add(Long.parseLong(id));
			}
			
			connectGuideMapper.deletes(idList);
		}
		
		return rs;
	}
	
	
}
