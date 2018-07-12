package com.kapphk.pms.bean;

import java.util.List;
import java.util.Map;

/**
 * 菜谱：sys_menu_book
 * @author zoneyu 2016-01-29
*/
public class BeanMenuBook extends BaseModel {

    /**
     * 表字段：sys_menu_book.id 注释：菜谱id
     * @author zoneyu 2016-01-29
     */
    private Long id;
    /**
     * 表字段：sys_menu_book.uid 注释：上传菜谱用户id
     * @author zoneyu 2016-01-29
     */
    private Long uid;
    /**
     * 表字段：sys_menu_book.name 注释：菜谱名称
     * @author zoneyu 2016-01-29
     */
    private String name;
    /**
     * 表字段：sys_menu_book.foods 注释：食材
     * @author zoneyu 2016-01-29
     */
    private String foods;
    /**
     * 表字段：sys_menu_book.logo_path 注释：菜谱封面图片
     * @author zoneyu 2016-01-29
     */
    private String logoPath;
    /**
     * 表字段：sys_menu_book.describes 注释：菜谱描述
     * @author zoneyu 2016-01-29
     */
    private String describes;
    /**
     * 表字段：sys_menu_book.click_number 注释：点击量
     * @author zoneyu 2016-01-29
     */
    private Integer clickNumber;
    /**
     * 表字段：sys_menu_book.recommend 注释：是否推荐（1、推荐；0、不推荐）
     * @author zoneyu 2016-01-29
     */
    private String recommend;
    /**
     * 表字段：sys_menu_book.is_advert 注释：是否设为盖头广告（1、是；0、否）
     * @author zoneyu 2016-01-29
     */
    private String isAdvert;
    /**
     * 表字段：sys_menu_book.audit_status 注释：(审核状态1、审核通过；0、审核不通过；2、待审核)
     * @author zoneyu 2016-01-29
     */
    private String auditStatus;
    /**
     * 表字段：sys_menu_book.remark 注释：备注
     * @author zoneyu 2016-01-29
     */
    private String remark;
    /**
     * 表字段：sys_menu_book.audit_time 注释：审核时间
     * @author zoneyu 2016-01-29
     */
    private String auditTime;
    /**
     * 表字段：sys_menu_book.is_new 注释：是否最新（1、最新；2、否）
     * @author zoneyu 2016-01-29
     */
    private String isNew;
    
    /**
     * 表字段：sys_menu_book.file_path 注释：文件路径
     * @author huzi 2018-06-22
     */
    private String filePath;
    
    /**
     * 表字段：sys_menu_book.over_time 注释：用于计算烹饪总时长的录波数据长度
     * @author huzi 2018-06-22
     */
    private String overTime;
    
    
    
  private List<Map<String, Object>> list;
    
    private List<BeanMenubookProcedure> procedures;
    
    

    public List<BeanMenubookProcedure> getProcedures() {
		return procedures;
	}

	public void setProcedures(List<BeanMenubookProcedure> procedures) {
		this.procedures = procedures;
	}

	public List<Map<String, Object>> getList() {
		return list;
	}

	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}
    

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFoods() {
        return foods;
    }

    public void setFoods(String foods) {
        this.foods = foods;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }

    public Integer getClickNumber() {
        return clickNumber;
    }

    public void setClickNumber(Integer clickNumber) {
        this.clickNumber = clickNumber;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getIsAdvert() {
        return isAdvert;
    }
    
    public String getFilePath(){
    	return filePath;
    }

    public void setIsAdvert(String isAdvert) {
        this.isAdvert = isAdvert;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
    }

    public String getIsNew() {
        return isNew;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }
    
    public void setFilePath(String filePath){
    	this.filePath=filePath;
    }

	public String getOverTime() {
		return overTime;
	}

	public void setOverTime(String overTime) {
		this.overTime = overTime;
	}

    
}