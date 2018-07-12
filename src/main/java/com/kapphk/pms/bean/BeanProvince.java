package com.kapphk.pms.bean;

/**
 * 省份信息
 * 主键ID：sys_province_info
 * @author zoneyu 2016-01-28
*/
public class BeanProvince extends BaseModel {

    /**
     * 表字段：sys_province_info.id 注释：主键ID
     * @author zoneyu 2016-01-28
     */
    private Long id;
    /**
     * 表字段：sys_province_info.province 注释：省份
     * @author zoneyu 2016-01-28
     */
    private String province;
    /**
     * 表字段：sys_province_info.code 注释：省份代码
     * @author zoneyu 2016-01-28
     */
    private String code;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}