package com.kapphk.pms.bean;

/**
 * 用户-角色
 * ：sys_user_role
 * @author zoneyu 2016-01-06
*/
public class BeanUserRole extends BaseModel {

    /**
     * 表字段：sys_user_role.id 注释：
     * @author zoneyu 2016-01-06
     */
    private Long id;
    /**
     * 表字段：sys_user_role.user_id 注释：用户ID
     * @author zoneyu 2016-01-06
     */
    private Long userId;
    /**
     * 表字段：sys_user_role.role_id 注释：角色ID
     * @author zoneyu 2016-01-06
     */
    private Long roleId;
    /**
     * 表字段：sys_user_role.oneself 注释：0:被赋予的角色；1:自己创建的角色
     * @author zoneyu 2016-01-06
     */
    private Short oneself;

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

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Short getOneself() {
        return oneself;
    }

    public void setOneself(Short oneself) {
        this.oneself = oneself;
    }
}