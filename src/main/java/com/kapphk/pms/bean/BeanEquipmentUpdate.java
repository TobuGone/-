package com.kapphk.pms.bean;

/**
 * PMS固件设备更新
 * ID：sys_equipment_update
 * @author huzi 2018-05-15
*/
public class BeanEquipmentUpdate extends BaseModel {

    /**
     * 表字段：sys_equipment_update.id 注释：设备更新ID
     * @author huzi 2018-05-15
     */
    private Long id;
    /**
     * 表字段：sys_equipment_update.name 注释：固件名称
     * @author huzi 2018-05-15
     */
    private String name;
    
    /**
     * 表字段：sys_equipment_update.hardware_version 注释：硬件型号
     * @author huzi 2018-05-15
     */
    private String hardwareVersion;

    /**
     * 表字段：sys_equipment_update.software_version 注释：软件型号
     * @author huzi 2018-05-15
     */
    private String softwareVersion;
    
    /**
     * 表字段：sys_equipment_update.version_code 注释：版本号
     * @author huzi 2018-05-15
     */
    private String versionCode;
    
    /**
     * 表字段：sys_equipment_update.path 注释：文件路径
     * @author huzi 2018-05-15
     */
    private String path;
    
    /**
     * 表字段：sys_equipment_update.remark 注释：更新说明
     * @author huzi 2018-05-15
     */
    private String remark;
    
    /**
     * 表字段：sys_equipment_update.update_personnel 注释：更新人员
     * @author huzi 2018-05-15
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

	public String getHardwareVersion() {
		return hardwareVersion;
	}

	public void setHardwareVersion(String hardwareVersion) {
		this.hardwareVersion = hardwareVersion;
	}

	public String getSoftwareVersion() {
		return softwareVersion;
	}

	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
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
    
    
}