package com.kapphk.pms.bean;

/**
 * 食材分类：sys_food_category
 * @author zoneyu 2016-01-23
*/
public class BeanFoodCategory extends BaseModel {

    /**
     * 表字段：sys_food_category.id 注释：分类id
     * @author zoneyu 2016-01-23
     */
    private Long id;
    /**
     * 表字段：sys_food_category.name 注释：食材分类名称
     * @author zoneyu 2016-01-23
     */
    private String name;
    /**
     * 表字段：sys_food_category.pid 注释：父id
     * @author zoneyu 2016-01-23
     */
    private Long pid;
    /**
     * 表字段：sys_food_category.level 注释：分类级别
     * @author zoneyu 2016-01-23
     */
    private String level;
    /**
     * 表字段：sys_food_category.logo_path 注释：缩略图
     * @author zoneyu 2016-01-23
     */
    private String logoPath;

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

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }
}