package com.kapphk.pms.bean;

/**   
 * @ClassName: MenuSecondCategory    
 * @Description: 菜谱二级分类    
 * @author XS  
 * @date 2016-1-26 下午3:49:47    
 *         
 */
public class MenuSecondCategory {
	private Integer mscId;
	private String mscName;
	private String mscLogoPath;
	public Integer getMscId() {
		return mscId;
	}
	public void setMscId(Integer mscId) {
		this.mscId = mscId;
	}
	public String getMscName() {
		return mscName;
	}
	public void setMscName(String mscName) {
		this.mscName = mscName;
	}
	public String getMscLogoPath() {
		return mscLogoPath;
	}
	public void setMscLogoPath(String mscLogoPath) {
		this.mscLogoPath = mscLogoPath;
	}
}
