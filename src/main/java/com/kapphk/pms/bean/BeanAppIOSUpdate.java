package com.kapphk.pms.bean;

/**
 * Android端app版本更新
 * 表：sys_app_ios_update
 * @author huzi 2018-04-24
*/
public class BeanAppIOSUpdate extends BaseModel {

    /**
     * 表字段：id   注释：应用更新ID
     */
    private Long id;
    /**
     * 表字段：name 注释：应用名称
     */
    private String name;
    /**
     * 表字段：version_code 注释：版本号
     */
    private String versionCode;
    
    /**
     * 表字段：version_name 注释：版本名称
     */
    private String versionName;
    
    /**
     * 表字段：remark 注释：更新说明
     */
    private String remark;	
    
    /**
     * 表字段：update_personnel 注释：更新人员
     */
    private String updatePersonnel;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUpdatePersonnel() {
		return updatePersonnel;
	}

	public void setUpdatePersonnel(String updatePersonnel) {
		this.updatePersonnel = updatePersonnel;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}


    
    
}