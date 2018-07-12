package com.kapphk.pms.bean;

/**
 * 资源
 * ：sys_resource
 * @author zoneyu 2016-01-06
*/
public class BeanResource extends BaseModel {

    /**
     * 表字段：sys_resource.id 注释：
     * @author zoneyu 2016-01-06
     */
    private Integer id;
    /**
     * 表字段：sys_resource.resource_name 注释：源资名称
     * @author zoneyu 2016-01-06
     */
    private String resourceName;
    /**
     * 表字段：sys_resource.url 注释：源资路径
     * @author zoneyu 2016-01-06
     */
    private String url;
    /**
     * 表字段：sys_resource.parent_id 注释：父节点
     * @author zoneyu 2016-01-06
     */
    private Integer parentId;
    /**	
     * 表字段：sys_resource.type 注释：1=菜单 ；2=按钮
     * @author zoneyu 2016-01-06
     */
    private Short type;
    /**
     * 表字段：sys_resource.remark 注释：
     * @author zoneyu 2016-01-06
     */
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}