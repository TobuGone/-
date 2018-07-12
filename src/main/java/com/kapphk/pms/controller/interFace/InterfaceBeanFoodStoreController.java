package com.kapphk.pms.controller.interFace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kapphk.pms.bean.BeanFoodStore;
import com.kapphk.pms.bean.FoodStoreFirstCategory;
import com.kapphk.pms.bean.FoodStoreSecondCategory;
import com.kapphk.pms.service.interFace.InterfaceBeanFoodStoreService;
import com.kapphk.pms.util.Contents;
import com.kapphk.pms.util.DateUtils;
import com.kapphk.pms.util.Result;
import com.kapphk.pms.util.ValidateUtils;

/**   
 * @ClassName: InterfaceBeanFoodStoreController    
 * @Description:食材管理
 * @author XS  
 * @date 2016-1-25 下午5:32:20    
 *         
 */
@RestController
@RequestMapping("/foodstore/")
public class InterfaceBeanFoodStoreController {
	
	@Autowired
	private InterfaceBeanFoodStoreService foodStoreService;
	 
	
	/**   
	* @Title: foodStoreCategory    
	* @Description: 查询食材分类和食材关系(包括一级分类和食材关系、二级分类和食材关系)  
	* @param     设定文件    
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-26 下午2:58:12
	*/
	@RequestMapping("findfoodStoreCategoryLibrary.aspf")
	public Result findfoodStoreCategoryLibrary(String level,Integer standardStatus,Long userId ,Result rs){
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			if(ValidateUtils.isBlank(userId) || ValidateUtils.isBlank(standardStatus)){
				rs.setStatus(Contents.PARAMS_ERROR);
			}
			
			param.put("userId", userId);
			param.put("level", level);
			param.put("standardStatus", standardStatus);
			List<FoodStoreFirstCategory> list = 	foodStoreService.foodStoreCategory(param);
			rs.put("foodStoreLibrarys", list != null ? list : new HashMap<Object, Object>());
		} catch (Exception e) {
			e.printStackTrace();
			rs.setStatus(Contents.ERROR);
		}
		return rs;
	}
	
	
	/**   
	* @Title: findTowFoodStoreCategory    
	* @Description: 查询食材的两级分类关系 
	* @param     设定文件    
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-26 下午3:18:26
	*/
	@RequestMapping("findTowFoodStoreCategory.aspf")
	public Result findTowFoodStoreCategory(Long categoryId, Result rs){
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("id", categoryId);
			List<FoodStoreFirstCategory> list = 	foodStoreService.findTowFoodStoreCategory(param);
			//清除空数据
			if(!CollectionUtils.isEmpty(list)){
				for(FoodStoreFirstCategory fsc:list){
					List<FoodStoreSecondCategory> fsscList = fsc.getSecondCategorieList();
					if(!CollectionUtils.isEmpty(fsscList)){
						for(int i = 0 ;i < fsscList.size() ;i++){
							FoodStoreSecondCategory fssc = fsscList.get(i);
							if(fssc.getScId() == null || fssc.getScId() == 0){
								fsscList.remove(i);
								i--;
							}
						}
					}
				}
			}
			rs.put("towFoodStoreCategorys", list != null ? list : new HashMap<Object, Object>());
		} catch (Exception e) {
			e.printStackTrace();
			rs.setStatus(Contents.ERROR);
		}
		return rs;
	}
	
	
	/**   
	* @Title: searchFoodStoreLibrary    
	* @Description: 查询食材
	* @param     设定文件    
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-26 下午3:04:07
	*/
	@RequestMapping("searchFoodStoreLibrary.aspf")
	public Result searchFoodStoreLibrary(String foodStoreName,Long userId,Result rs){
		try {
			if(ValidateUtils.isBlank(foodStoreName)){
				rs.setStatus(Contents.PARAMS_ERROR);
				rs.setMsg("请输入搜索的关键字.");
			}else{
				Map<String, Object> condition = new HashMap<String, Object>();
				//找寻标准食材库,不需要用户id
				condition.put("foodStoreName", foodStoreName);
				//condition.put("userId", userId);
				condition.put("standardStatus", 1);
				List<Map<String,Object>> list =	foodStoreService.findByCondition(condition);
				rs.put("foodStores", list != null ? list : new ArrayList<Object>());	
			}
		} catch (Exception e) {
			 e.printStackTrace();
		}
		
		return rs;
	}
	
	/**   
	* @Title: searchFoodStoreLibrary    
	* @Description: 查询食材
	* @param     设定文件    
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-26 下午3:04:07
	*/
	@RequestMapping("searchMyFoodStoreLibrary.aspf")
	public Result searchMyFoodStoreLibrary(String foodStoreName,Long userId,Result rs){
		try {
			if(ValidateUtils.isBlank(foodStoreName) || ValidateUtils.isBlank(userId)){
				rs.setStatus(Contents.PARAMS_ERROR);
				rs.setMsg("参数不能为空.");
			}else{
				Map<String, Object> condition = new HashMap<String, Object>();
				//找寻标准食材库,不需要用户id
				condition.put("foodStoreName", foodStoreName);
				condition.put("userId", userId);
				condition.put("standardStatus", 1);
				List<Map<String,Object>> list =	foodStoreService.findMyFoodByCondition(condition);
				rs.put("foodStores", list != null ? list : new ArrayList<Object>());	
			}
		} catch (Exception e) {
			 e.printStackTrace();
		}
		
		return rs;
	}
	
	/**   
	* @Title: saveFoodStore    
	* @Description:  添加食材 
	* @param     设定文件    
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-28 上午11:32:34
	*/
	@RequestMapping("saveFoodStore.aspf")
    public Result saveFoodStore(BeanFoodStore foodStore,Long firstCategoryId,Long secondCategoryId,Long userId,Result rs){
		try {
			if(ValidateUtils.isBlank(foodStore.getName()) || ValidateUtils.isBlank(userId)){
				rs.setStatus(Contents.PARAMS_ERROR);
			}
			foodStore.setCreateTime(DateUtils.getLocalDate());
			foodStore.setStatus(1);//启用
			foodStore.setStandardStatus((short)0);//非标准食材
			foodStoreService.saveFoodStore(foodStore, firstCategoryId, secondCategoryId,userId);
		} catch (Exception e) {
			e.printStackTrace();
			rs.setStatus(Contents.PARAMS_ERROR);
		}
		return rs;
	}
	
	
	
	@RequestMapping("getFoodStoreByCategoryId.aspf")
	public Result getFoodStoreByCategoryId(Long categoryId,Integer standardStatus)throws Exception{
		Result rs = new Result();
		
		rs = foodStoreService.getFoodStoreByCategoryId(categoryId,standardStatus);
		
		return rs;
	}
	
	/**
	 * 从txt读取资料到food备注中
	 * @throws Exception 
	 */
	@RequestMapping("inputDataFromFile.aspf")
	public Result inputDataFromFile(Result rs) throws Exception{
		foodStoreService.inputDataFromFile(rs);
		return rs;
	}
	
	/**
	 * 根据食材id查找菜谱
	 * Lotus 2016-04-25
	 */
	@RequestMapping("findMenuBookByFoodStore.aspf")
	public Result findMenuBookByFoodStore(Long foodStoreId,Result rs){
		try {
			foodStoreService.findMenuBookByFoodStore(foodStoreId,rs);
		} catch (Exception e) {
			e.printStackTrace();
			rs.setStatus(Contents.ERROR);
		}
		return rs;
	}
}
