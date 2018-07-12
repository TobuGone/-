package com.kapphk.pms.bean;

/**
 * 用户-食材
 * ：sys_user_food_store
 * @author zoneyu 2016-01-25
*/
public class BeanUserFoodStore extends BaseModel {

    /**
     * 表字段：sys_user_food_store.id 注释：
     * @author zoneyu 2016-01-25
     */
    private Long id;
    /**
     * 表字段：sys_user_food_store.user_id 注释：用户id
     * @author zoneyu 2016-01-25
     */
    private Long userId;
    /**
     * 表字段：sys_user_food_store.foot_store_id 注释：食材表id
     * @author zoneyu 2016-01-25
     */
    private Long footStoreId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFootStoreId() {
        return footStoreId;
    }

    public void setFootStoreId(Long footStoreId) {
        this.footStoreId = footStoreId;
    }
}