package com.kapphk.pms.bean;

/**
 * 用户-菜谱分类
 * 主键：sys_user_menu_category
 * @author zoneyu 2016-01-19
*/
public class BeanUserMenuCategory extends BaseModel {

    /**
     * 表字段：sys_user_menu_category.id 注释：主键id
     * @author zoneyu 2016-01-19
     */
    private Long id;
    /**
     * 表字段：sys_user_menu_category.uid 注释：用户id（手机用户）
     * @author zoneyu 2016-01-19
     */
    private Long uid;
    /**
     * 表字段：sys_user_menu_category.category_id 注释：菜谱分类id
     * @author zoneyu 2016-01-19
     */
    private Long categoryId;

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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}