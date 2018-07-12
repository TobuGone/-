package com.kapphk.pms.bean;

/**
 * 食材库-菜谱中间表
 * 主键：sys_foodstore_menubook
 * @author zoneyu 2016-01-25
*/
public class BeanFoodstorMenubook extends BaseModel {

    /**
     * 表字段：sys_foodstore_menubook.id 注释：主键id
     * @author zoneyu 2016-01-25
     */
    private Long id;
    /**
     * 表字段：sys_foodstore_menubook.store_id 注释：食材库id
     * @author zoneyu 2016-01-25
     */
    private Long storeId;
    /**
     * 表字段：sys_foodstore_menubook.menu_id 注释：菜谱id
     * @author zoneyu 2016-01-25
     */
    private Long menuId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
}