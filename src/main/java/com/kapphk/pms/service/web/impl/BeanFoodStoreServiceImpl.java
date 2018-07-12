package com.kapphk.pms.service.web.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kapphk.pms.bean.BeanFoodStore;
import com.kapphk.pms.bean.BeanFoodstorMenubook;
import com.kapphk.pms.bean.BeanFoodstoreCategory;
import com.kapphk.pms.dao.mapper.BeanFoodStoreMapper;
import com.kapphk.pms.dao.mapper.BeanFoodstorMenubookMapper;
import com.kapphk.pms.dao.mapper.BeanFoodstoreCategoryMapper;
import com.kapphk.pms.service.BaseServiceImpl;
import com.kapphk.pms.service.web.BeanFoodStoreService;
import com.kapphk.pms.util.Contents;
import com.kapphk.pms.util.DateUtils;
import com.kapphk.pms.util.FileUploadUtils;
import com.kapphk.pms.util.Result;
import com.kapphk.pms.util.ValidateUtils;
@Service("beanFoodStoreService")
public class BeanFoodStoreServiceImpl extends BaseServiceImpl<BeanFoodStore, Long>implements BeanFoodStoreService {

	@Autowired
	private BeanFoodStoreMapper storeMapper;
	
	@Autowired
	private BeanFoodstoreCategoryMapper foodstoreCategoryMapper; 
	
	@Autowired
	private BeanFoodstorMenubookMapper foodstorMenubookMapper;

	@Override
	public void init() {
		super.setMapper(storeMapper);
	}

	/**
	 * 查询食材库数据列表
	 * @param searchName 食材学名
	 * @param page 页数
	 * @param rows 每页显示的行数
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-25
	 */
	@Override
	public Result searchFoodStoreList(String searchName, int page, int rows)
			throws Exception {
		Result rs = new Result();
		
		List<Map<String, Object>> list = storeMapper.findMapByPage(searchName, (page-1)*rows, rows);
		
		int count =  storeMapper.findMapByPageCount(searchName);
		
		rs.put("total", count);
		rs.put("rows",list);
		
		return rs;
	}

	/**
	 * 新增或修改食材库
	 * @param foodStore 食材库对象
	 * @param firstId 一级分类id
	 * @param secondId 二级分类id
	 * @param file 文件流
	 * @param req
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-25
	 */
	@Override
	public Result saveFoodStore(BeanFoodStore foodStore, Long firstId,
			Long secondId, Long[] menuId, MultipartFile file,
			HttpServletRequest req) throws Exception {
		Result rs = new Result();
		
		//新增
		if(ValidateUtils.isBlank(foodStore.getId())){
			
			Long fid= storeMapper.findByFoodName(foodStore.getName());
			
			if(fid != null){
				rs.setStatus(Contents.EXIST);
				rs.setMsg("食材学名已重复，请重新输入");
				return rs;
			}
			
			//上传图片
			if(!file.isEmpty()){
				byte[] bytes = file.getBytes() ;
				String logPath = FileUploadUtils.uploadFile(bytes,"upload/foodStore","2",req) ;
				foodStore.setLogoPath("/"+logPath);
			}
			
			foodStore.setCreateTime(DateUtils.getLocalDate());
			
			storeMapper.insert(foodStore);
			
			
			List<BeanFoodstoreCategory> clist = new ArrayList<BeanFoodstoreCategory>();
			
			//一级分类
			BeanFoodstoreCategory fCategory = new BeanFoodstoreCategory();
			fCategory.setCategoryId(firstId);
			fCategory.setStoreId(foodStore.getId());
			fCategory.setCreateTime(DateUtils.getLocalDate());
			clist.add(fCategory);
			
			
			//二级分类
			BeanFoodstoreCategory sCategory = new BeanFoodstoreCategory();
			sCategory.setCategoryId(secondId);
			sCategory.setStoreId(foodStore.getId());
			sCategory.setCreateTime(DateUtils.getLocalDate());
			clist.add(sCategory);
			
			foodstoreCategoryMapper.inserts(clist);
			
			if(menuId != null && menuId.length > 0){
				//食材库与菜谱关系
				List<BeanFoodstorMenubook> list = new ArrayList<BeanFoodstorMenubook>();
				
				for(Long id:menuId){
					BeanFoodstorMenubook foodstorMenubook = new BeanFoodstorMenubook();
					foodstorMenubook.setMenuId(id);
					foodstorMenubook.setStoreId(foodStore.getId());
					foodstorMenubook.setCreateTime(DateUtils.getLocalDate());
					list.add(foodstorMenubook);
				}
				
				foodstorMenubookMapper.inserts(list);	
				
			}
		}
		//修改
		else{
			Long fid= storeMapper.findByFoodName(foodStore.getName());
			
			if(fid ==null || foodStore.getId().equals(fid) ){
				//上传图片
				if(!file.isEmpty()){
					byte[] bytes = file.getBytes() ;
					String logPath = FileUploadUtils.uploadFile(bytes,"upload/foodStore","2",req) ;
					foodStore.setLogoPath("/"+logPath);
				}
				
				storeMapper.update(foodStore);
				
				//删除全部的关联
				foodstoreCategoryMapper.deleteByStoreId(foodStore.getId());
				
				foodstorMenubookMapper.deleteByStoreId(foodStore.getId());
				
				
				List<BeanFoodstoreCategory> clist = new ArrayList<BeanFoodstoreCategory>();
				
				//一级分类
				BeanFoodstoreCategory fCategory = new BeanFoodstoreCategory();
				fCategory.setCategoryId(firstId);
				fCategory.setStoreId(foodStore.getId());
				fCategory.setCreateTime(DateUtils.getLocalDate());
				clist.add(fCategory);
				
				
				//二级分类
				BeanFoodstoreCategory sCategory = new BeanFoodstoreCategory();
				sCategory.setCategoryId(secondId);
				sCategory.setStoreId(foodStore.getId());
				sCategory.setCreateTime(DateUtils.getLocalDate());
				clist.add(sCategory);
				
				foodstoreCategoryMapper.inserts(clist);
				
				if(!ValidateUtils.isempty(menuId)){
					//食材库与菜谱关系
					List<BeanFoodstorMenubook> mlist = new ArrayList<BeanFoodstorMenubook>();
					
					for(Long id:menuId){
						BeanFoodstorMenubook foodstorMenubook = new BeanFoodstorMenubook();
						foodstorMenubook.setMenuId(id);
						foodstorMenubook.setStoreId(foodStore.getId());
						foodstorMenubook.setCreateTime(DateUtils.getLocalDate());
						mlist.add(foodstorMenubook);
					}
					
					foodstorMenubookMapper.inserts(mlist);
				}
			}else{
				rs.setStatus(Contents.EXIST);
				rs.setMsg("食材学名已重复，请重新输入");
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
	public Map<String, Object> findMapById(Long id) throws Exception {
		return  storeMapper.findMapById(id);
	}

	/**
	 * 删除食材库
	 * @param ids
	 * @return
	 * @throws Exception
	 * huangzh 2015-08-14
	 */
	@Override
	public Result delFoodStore(Long[] ids) throws Exception {
		Result rs = new Result();
		
		for(Long id:ids){
			storeMapper.delete(id);
			//删除全部的关联
			foodstoreCategoryMapper.deleteByStoreId(id);
			
			foodstorMenubookMapper.deleteByStoreId(id);
			
		}
		
		return rs;
	}

	/**
	 * 食材下拉框数据
	 * @return List
	 * @throws Exception
	 * @author huangzh 2016-01-20
	 */
	@Override
	public List<Map<String, Object>> getSecondCombox(Long foodId) {
			return storeMapper.getSecondCombox(foodId);
	}
	
	
	

}
