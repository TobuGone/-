package com.kapphk.pms.bean;

/**
 * 系统记录
 * ：sys_system_log
 * @author zoneyu 2016-01-06
*/
public class BeanSystemLog extends BaseModel {

    /**
     * 表字段：sys_system_log.id 注释：
     * @author zoneyu 2016-01-06
     */
    private Long id;
    /**
     * 表字段：sys_system_log.uid 注释：
     * @author zoneyu 2016-01-06
     */
    private Long uid;
    /**
     * 表字段：sys_system_log.ip_address 注释：
     * @author zoneyu 2016-01-06
     */
    private String ipAddress;
    /**
     * 表字段：sys_system_log.operation_type 注释：
     * @author zoneyu 2016-01-06
     */
    private String operationType;
    /**
     * 表字段：sys_system_log.event_name 注释：
     * @author zoneyu 2016-01-06
     */
    private String eventName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}