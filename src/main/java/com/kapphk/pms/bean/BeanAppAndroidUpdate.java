package com.kapphk.pms.bean;

/**
 * Android端app版本更新
 * 表：sys_app_android_update
 * @author huzi 2018-01-25
*/
public class BeanAppAndroidUpdate extends BaseModel {

    /**
     * 表字段：id   注释：应用更新ID
     */
    private Long id;
    /**
     * 表字段：name 注释：应用名称
     */
    private String name;
    /**
     * 表字段：path 注释：文件路径
     */
    private String path;
    
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
     * 表字段：download_count 注释：下载次数
     */
    private int downloadCount;
    
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}


	public int getDownloadCount() {
		return downloadCount;
	}

	public void setDownloadCount(int downloadCount) {
		this.downloadCount = downloadCount;
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