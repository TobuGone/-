package com.kapphk.pms.bean;

/**
 * 食材库-食材分类中间表
 * 主键：sys_foodstore_category
 * @author zoneyu 2016-01-25
*/
public class BeanFoodstoreCategory extends BaseModel {

    /**
     * 表字段：sys_foodstore_category.id 注释：主键id
     * @author zoneyu 2016-01-25
     */
    private Long id;
    /**
     * 表字段：sys_foodstore_category.store_id 注释：食材库id
     * @author zoneyu 2016-01-25
     */
    private Long storeId;
    /**
     * 表字段：sys_foodstore_category.category_id 注释：食材分类id
     * @author zoneyu 2016-01-25
     */
    private Long categoryId;

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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}