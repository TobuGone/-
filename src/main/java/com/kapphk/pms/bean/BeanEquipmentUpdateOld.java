package com.kapphk.pms.bean;

/**
 * 设备更新ID：sys_equipment_update
 * @author zoneyu 2016-04-27
*/
public class BeanEquipmentUpdateOld extends BaseModel {

    /**
     * 表字段：sys_equipment_update.id 注释：设备更新ID
     * @author zoneyu 2016-04-27
     */
    private Long id;
    /**
     * 表字段：sys_equipment_update.name 注释：名称
     * @author zoneyu 2016-04-27
     */
    private String name;
    /**
     * 表字段：sys_equipment_update.path 注释：上传文件路径
     * @author zoneyu 2016-04-27
     */
    private String path;

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
}
