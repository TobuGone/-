package com.kapphk.pms.bean;

import java.util.List;

/**   
 * @ClassName: FoodStoreSecondCategory    
 * @Description:  二级食材分类对象   
 * @author XS  
 * @date 2016-1-26 下午2:36:25    
 *         
 */
public class FoodStoreSecondCategory {
	
	private  Integer scId;
	private  String scName; 
	private  String scLogoPath;
	private List<FoodStore> foodStoreList;
	public Integer getScId() {
		return scId;
	}
	public void setScId(Integer scId) {
		this.scId = scId;
	}
	public String getScName() {
		return scName;
	}
	public void setScName(String scName) {
		this.scName = scName;
	}
	public List<FoodStore> getFoodStoreList() {
		return foodStoreList;
	}
	public void setFoodStoreList(List<FoodStore> foodStoreList) {
		this.foodStoreList = foodStoreList;
	}
	public String getScLogoPath() {
		return scLogoPath;
	}
	public void setScLogoPath(String scLogoPath) {
		this.scLogoPath = scLogoPath;
	}


}
