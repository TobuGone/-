package com.kapphk.pms.bean;

/**
 * 菜谱-菜谱分类中间表
 * ：sys_menubook_category
 * @author zoneyu 2016-01-22
*/
public class BeanMenubookCategory extends BaseModel {

    /**
     * 表字段：sys_menubook_category.id 注释：
     * @author zoneyu 2016-01-22
     */
    private Long id;
    /**
     * 表字段：sys_menubook_category.menu_id 注释：菜谱id
     * @author zoneyu 2016-01-22
     */
    private Long menuId;
    /**
     * 表字段：sys_menubook_category.category_id 注释：菜谱分类id
     * @author zoneyu 2016-01-22
     */
    private Long categoryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}