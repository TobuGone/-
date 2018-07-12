package com.kapphk.pms.bean;

/**
 * 角色
 * ：sys_role
 * @author zoneyu 2016-01-06
*/
public class BeanRole extends BaseModel {

    /**
     * 表字段：sys_role.id 注释：
     * @author zoneyu 2016-01-06
     */
    private Long id;
    /**
     * 表字段：sys_role.role_name 注释：角色名称
     * @author zoneyu 2016-01-06
     */
    private String roleName;
    /**
     * 表字段：sys_role.role_value 注释：标明角色对象 （ROLE_SUPER_ADMIN=超级管理员）
     * @author zoneyu 2016-01-06
     */
    private String roleValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleValue() {
        return roleValue;
    }

    public void setRoleValue(String roleValue) {
        this.roleValue = roleValue;
    }
}