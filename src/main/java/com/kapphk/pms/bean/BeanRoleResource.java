package com.kapphk.pms.bean;

/**
 * 角色-资源
 * ：sys_role_resource
 * @author zoneyu 2016-01-06
*/
public class BeanRoleResource extends BaseModel {

    /**
     * 表字段：sys_role_resource.id 注释：
     * @author zoneyu 2016-01-06
     */
    private Integer id;
    /**
     * 表字段：sys_role_resource.resource_id 注释：源资ID
     * @author zoneyu 2016-01-06
     */
    private Integer resourceId;
    /**
     * 表字段：sys_role_resource.role_id 注释：色角ID
     * @author zoneyu 2016-01-06
     */
    private Integer roleId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}