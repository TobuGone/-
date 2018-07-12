package com.kapphk.pms.service.interFace.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.kapphk.pms.bean.BeanFoodCategory;
import com.kapphk.pms.bean.BeanFoodStore;
import com.kapphk.pms.bean.BeanFoodstoreCategory;
import com.kapphk.pms.bean.BeanUserFoodStore;
import com.kapphk.pms.bean.FoodStoreFirstCategory;
import com.kapphk.pms.dao.mapper.BeanFoodCategoryMapper;
import com.kapphk.pms.dao.mapper.BeanFoodStoreMapper;
import com.kapphk.pms.dao.mapper.BeanFoodstoreCategoryMapper;
import com.kapphk.pms.dao.mapper.BeanUserFoodStoreMapper;
import com.kapphk.pms.service.interFace.InterfaceBeanFoodStoreService;
import com.kapphk.pms.util.Contents;
import com.kapphk.pms.util.FileUploadUtils;
import com.kapphk.pms.util.Result;
import com.kapphk.pms.util.ValidateUtils;

/**   
 * @ClassName: InterfaceBeanFoodStoreServiceImpl    
 * @Description: 食材分类管理
 * @author XS  
 * @date 2016-1-25 下午4:19:06    
 *         
 */
@Service
public class InterfaceBeanFoodStoreServiceImpl implements InterfaceBeanFoodStoreService{
	
	@Autowired
	private BeanFoodStoreMapper foodStoreMapper;
	
	@Autowired
	private BeanFoodCategoryMapper foodCategoryMapper;
	
	@Autowired
	private BeanFoodstoreCategoryMapper foodstoreCategoryMapper;
	
	@Autowired
	private BeanUserFoodStoreMapper userFoodStoreMapper;

	/**   
	* @Title: foodStoreCategory    
	* @Description: 查询食材分类和食材关系(包括一级分类和食材关系、二级分类和食材关系)  
	* @param     设定文件    
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-26 下午2:58:12
	*/
	@Override
	public List<FoodStoreFirstCategory> foodStoreCategory(
			Map<String, Object> param) {
 
		return foodStoreMapper.foodStoreCategory(param);
	}
	
	/**   
	* @Title: findTowFoodStoreCategory    
	* @Description: 查询食材的两级分类关系 
	* @param     设定文件    
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-26 下午3:18:26
	*/
	@Override
	public List<FoodStoreFirstCategory> findTowFoodStoreCategory(
			Map<String, Object> param) {
		 return foodCategoryMapper.findTowFoodStoreCategory(param);
	}
	
	@Override
	public List<Map<String,Object>> findByCondition(Map<String, Object> condition){
		return foodStoreMapper.findByCondition(condition);
		
	};
	
	@Override
	public List<Map<String,Object>> findMyFoodByCondition(Map<String, Object> condition){
		return foodStoreMapper.findMyFoodByCondition(condition);
	};

	/**   
	* @Title: save    
	* @Description:  添加食材 ；食材分类关系
	* @param @param foodStore    设定文件    
	* @throws    
	*/
	@Override
	public void saveFoodStore(BeanFoodStore foodStore,Long firstCategoryId,Long secondCategoryId,Long userId) {
		String createTime = foodStore.getCreateTime();
	   //添加食材
	   foodStoreMapper.insert(foodStore);
	   
	   //添加用户和食材关系
	   BeanUserFoodStore ufs = new BeanUserFoodStore();
	   ufs.setFootStoreId(foodStore.getId());
	   ufs.setUserId(userId);
	   ufs.setCreateTime(createTime);
	   userFoodStoreMapper.insert(ufs);
	   
	   //添加食材和 分类关系
	   List<BeanFoodstoreCategory> fscList = new ArrayList<BeanFoodstoreCategory>();
	   if(!ValidateUtils.isBlank(firstCategoryId) && !ValidateUtils.isBlank(secondCategoryId)){
		   BeanFoodstoreCategory fsc = new BeanFoodstoreCategory();
		   fsc.setCategoryId(firstCategoryId);
		   fsc.setStoreId(foodStore.getId());
		   fsc.setCreateTime(createTime);
		   fscList.add(fsc);
		   
		   BeanFoodstoreCategory fsc1 = new BeanFoodstoreCategory();
		   fsc1.setCategoryId(secondCategoryId);
		   fsc1.setStoreId(foodStore.getId());
		   fsc1.setCreateTime(createTime);
		   fscList.add(fsc1);
		   foodstoreCategoryMapper.inserts(fscList);
		   
	   }else{
		   Map<String,Object> condition = new HashMap<String, Object>();
		   condition.put("name", Contents.CUSTOM_FOOD_STORE_CATEGORY);
		   //查询自定义食材
		   List<BeanFoodCategory> list =   foodCategoryMapper.findByCondition(condition);
		   if(!CollectionUtils.isEmpty(list)){

			   for(int i = 0 ;i <list.size() ; i++ ){
				   BeanFoodCategory fc = list.get(i);
				   if(fc.getId() < 0 ){
					   BeanFoodstoreCategory fsc = new BeanFoodstoreCategory();
					   fsc.setCategoryId(fc.getId());
					   fsc.setStoreId(foodStore.getId());
					   fsc.setCreateTime(createTime);
					   fscList.add(fsc);
				   }
			   }
			   foodstoreCategoryMapper.inserts(fscList);
		   } 
	   }
		
	}

	
	@Override
	public Result getFoodStoreByCategoryId(Long categoryId,
			Integer standardStatus) throws Exception {
		Result rs = new Result();
		
		List<Map<String, Object>> list = foodStoreMapper.getFoodStoreByCategoryId(categoryId,standardStatus);
		
		rs.put("foodStores", list);
		
		return rs;
	}

	@Override
	public void inputDataFromFile(Result rs) throws Exception {
		BeanFoodStore foodStore=new BeanFoodStore();
		List<BeanFoodStore> list = foodStoreMapper.findByPage(foodStore, 0, 10000);
		if(!ValidateUtils.isEmptyForCollection(list)){
			String resPath = FileUploadUtils.class.getClassLoader()
					.getResource("../../").getPath();
			for(BeanFoodStore item:list){
				String path =resPath+"upload/foodText/"+item.getRemark()+".txt" ;
				File file=new File(path);
				if(file.exists()){
					byte[] bytes=new byte[(int) file.length()];
					FileInputStream fi=new FileInputStream(file);
					fi.read(bytes);
					fi.close();
					
					String remark=new String(bytes,"utf-8");
					item.setRemark(remark);
					foodStoreMapper.update(item);
				}
			}
		}
		
	}

	/**
	 * 根据食材查询菜谱
	 */
	@Override
	public void findMenuBookByFoodStore(Long foodStoreId,Result rs) throws Exception {
		if(ValidateUtils.isBlank(foodStoreId)){
			rs.setStatus(Contents.PARAMS_ERROR);
			return ;
		}
		List<Map<String, Object>> list =foodStoreMapper.findMenuBookByFoodStore(foodStoreId);
		rs.put("menubooks", list);
	}
}
