package com.kapphk.pms.bean;

/**
 * 审核记录：sys_menu_book_audit
 * @author zoneyu 2016-04-26
*/
public class BeanMenuBookAudit extends BaseModel {

    /**
     * 表字段：sys_menu_book_audit.id 注释：审核记录id
     * @author zoneyu 2016-04-26
     */
    private Long id;
    /**
     * 表字段：sys_menu_book_audit.uid 注释：上传菜谱用户id
     * @author zoneyu 2016-04-26
     */
    private Long uid;
    /**
     * 表字段：sys_menu_book_audit.menu_id 注释：菜谱id
     * @author zoneyu 2016-04-26
     */
    private Long menuId;
    /**
     * 表字段：sys_menu_book_audit.audit_status 注释：(审核状态1、审核通过；0、审核不通过；2、待审核)
     * @author zoneyu 2016-04-26
     */
    private String auditStatus;
    /**
     * 表字段：sys_menu_book_audit.audit_remark 注释：审核信息
     * @author zoneyu 2016-04-26
     */
    private String auditRemark;

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

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditRemark() {
        return auditRemark;
    }

    public void setAuditRemark(String auditRemark) {
        this.auditRemark = auditRemark;
    }
}