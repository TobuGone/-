package com.kapphk.pms.bean;

/**
 * 连接指向类
 * 主键：sys_connect_guide
 * @author zoneyu 2016-01-13
*/
public class BeanConnectGuide extends BaseModel {

    /**
     * 表字段：sys_connect_guide.id 注释：主键id
     * @author zoneyu 2016-01-13
     */
    private Long id;
    /**
     * 表字段：sys_connect_guide.logo_path 注释：图片路径
     * @author zoneyu 2016-01-13
     */
    private String logoPath;
    /**
     * 表字段：sys_connect_guide.content 注释：内容
     * @author zoneyu 2016-01-13
     */
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}