package com.kapphk.pms.bean;

/**
 * 地区类
 * 主键ID：sys_distrct_info
 * @author zoneyu 2016-01-28
*/
public class BeanDistrct extends BaseModel {

    /**
     * 表字段：sys_distrct_info.id 注释：主键ID
     * @author zoneyu 2016-01-28
     */
    private Long id;
    /**
     * 表字段：sys_distrct_info.distrct 注释：地区名称
     * @author zoneyu 2016-01-28
     */
    private String distrct;
    /**
     * 表字段：sys_distrct_info.cid 注释：城市ID
     * @author zoneyu 2016-01-28
     */
    private Long cid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDistrct() {
        return distrct;
    }

    public void setDistrct(String distrct) {
        this.distrct = distrct;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }
}