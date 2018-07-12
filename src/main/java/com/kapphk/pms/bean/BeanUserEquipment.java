package com.kapphk.pms.bean;

/**
 * 用户设备
 * ：sys_user_equipment
 * @author zoneyu 2016-01-25
*/
public class BeanUserEquipment extends BaseModel {

    /**
     * 表字段：sys_user_equipment.id 注释：
     * @author zoneyu 2016-01-25
     */
    private Long id;
    /**
     * 表字段：sys_user_equipment.user_id 注释：用户id
     * @author zoneyu 2016-01-25
     */
    private Long userId;
    /**
     * 表字段：sys_user_equipment.equipment_id 注释：设备id
     * @author zoneyu 2016-01-25
     */
    private Long equipmentId;
    /**
     * 表字段：sys_user_equipment.wifi_link 注释：wifi链接
     * @author zoneyu 2016-01-25
     */
    private String wifiLink;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getWifiLink() {
        return wifiLink;
    }

    public void setWifiLink(String wifiLink) {
        this.wifiLink = wifiLink;
    }
}