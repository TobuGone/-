package com.kapphk.pms.bean;

/**
 * 求助信息
 * 主键：public_help
 * @author zoneyu 2016-01-12
*/
public class BeanPublicHelp extends BaseModel {

    /**
     * 表字段：public_help.id 注释：主键id
     * @author zoneyu 2016-01-12
     */
    private Long id;
    /**
     * 表字段：public_help.title 注释：标题
     * @author zoneyu 2016-01-12
     */
    private String title;
    /**
     * 表字段：public_help.memo 注释：内容
     * @author zoneyu 2016-01-12
     */
    private String memo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}