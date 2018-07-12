package com.kapphk.pms.bean;

import java.util.List;

/**   
 * @ClassName: MenuFirstCategory    
 * @Description: 菜谱一级分类
 * @author XS  
 * @date 2016-1-26 下午3:49:16    
 *         
 */
public class MenuFirstCategory {
	private Integer mfcId;
	private String mfcName;
	private String mfcLogoPath;

	private List<MenuSecondCategory> secondCategorieList;
	public Integer getMfcId() {
		return mfcId;
	}
	public void setMfcId(Integer mfcId) {
		this.mfcId = mfcId;
	}
	public String getMfcName() {
		return mfcName;
	}
	public void setMfcName(String mfcName) {
		this.mfcName = mfcName;
	}
	public List<MenuSecondCategory> getSecondCategorieList() {
		return secondCategorieList;
	}
	public void setSecondCategorieList(List<MenuSecondCategory> secondCategorieList) {
		this.secondCategorieList = secondCategorieList;
	}
	
	public String getMfcLogoPath() {
		return mfcLogoPath;
	}
	public void setMfcLogoPath(String mfcLogoPath) {
		this.mfcLogoPath = mfcLogoPath;
	}
}
