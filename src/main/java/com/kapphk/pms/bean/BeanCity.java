package com.kapphk.pms.bean;

/**
 *  城市类
 * ：sys_city_info
 * @author zoneyu 2016-01-28
*/
public class BeanCity extends BaseModel {

    /**
     * 表字段：sys_city_info.id 注释：
     * @author zoneyu 2016-01-28
     */
    private Long id;
    /**
     * 表字段：sys_city_info.city 注释：
     * @author zoneyu 2016-01-28
     */
    private String city;
    /**
     * 表字段：sys_city_info.pid 注释：
     * @author zoneyu 2016-01-28
     */
    private Long pid;
    /**
     * 表字段：sys_city_info.type 注释：
     * @author zoneyu 2016-01-28
     */
    private String type;
    /**
     * 表字段：sys_city_info.code 注释：
     * @author zoneyu 2016-01-28
     */
    private String code;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}