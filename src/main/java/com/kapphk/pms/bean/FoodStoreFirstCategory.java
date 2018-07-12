package com.kapphk.pms.bean;

import java.util.List;

/**   
 * @ClassName: FoodCategory    
 * @Description: 一级食材分类  
 * @author XS  
 * @date 2016-1-25 下午4:28:45    
 *         
 */
public class FoodStoreFirstCategory {
	
	private Integer fcId;
	private String fcName;
	private List<FoodStore> foodStoreList;
	private List<FoodStoreSecondCategory> secondCategorieList;

	public Integer getFcId() {
		return fcId;
	}
	public void setFcId(Integer fcId) {
		this.fcId = fcId;
	}
	public String getFcName() {
		return fcName;
	}
	public void setFcName(String fcName) {
		this.fcName = fcName;
	}
	public List<FoodStore> getFoodStoreList() {
		return foodStoreList;
	}
	public void setFoodStoreList(List<FoodStore> foodStoreList) {
		this.foodStoreList = foodStoreList;
	}
	public List<FoodStoreSecondCategory> getSecondCategorieList() {
		return secondCategorieList;
	}
	public void setSecondCategorieList(
			List<FoodStoreSecondCategory> secondCategorieList) {
		this.secondCategorieList = secondCategorieList;
	}
}
