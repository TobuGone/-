package com.kapphk.pms.bean;

/**
 * 食材类
 * 主键：sys_food_store
 * @author zoneyu 2016-01-27
*/
public class BeanFoodStore extends BaseModel {

    /**
     * 表字段：sys_food_store.id 注释：id主键
     * @author zoneyu 2016-01-27
     */
    private Long id;
    /**
     * 表字段：sys_food_store.name 注释：食材学名
     * @author zoneyu 2016-01-27
     */
    private String name;
    /**
     * 表字段：sys_food_store.alias 注释：食材别名
     * @author zoneyu 2016-01-27
     */
    private String alias;
    /**
     * 表字段：sys_food_store.standard_status 注释：是否是标准食材 0=不是；1=是
     * @author zoneyu 2016-01-27
     */
    private Short standardStatus;
    /**
     * 表字段：sys_food_store.product_place 注释：产地
     * @author zoneyu 2016-01-27
     */
    private String productPlace;
    /**
     * 表字段：sys_food_store.selling_place 注释：热销地
     * @author zoneyu 2016-01-27
     */
    private String sellingPlace;
    /**
     * 表字段：sys_food_store.use_frequence 注释：使用频率（%）
     * @author zoneyu 2016-01-27
     */
    private String useFrequence;
    /**
     * 表字段：sys_food_store.dosage 注释：建议用量（g）
     * @author zoneyu 2016-01-27
     */
    private String dosage;
    /**
     * 表字段：sys_food_store.logo_path 注释：图片路径
     * @author zoneyu 2016-01-27
     */
    private String logoPath;
    /**
     * 表字段：sys_food_store.remark 注释：备注
     * @author zoneyu 2016-01-27
     */
    private String remark;
    
    /**
     * 表字段：sys_food_store.uid 注释：备注
     * @author zoneyu 2016-01-27
     */
    private String uid;
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Short getStandardStatus() {
        return standardStatus;
    }

    public void setStandardStatus(Short standardStatus) {
        this.standardStatus = standardStatus;
    }

    public String getProductPlace() {
        return productPlace;
    }

    public void setProductPlace(String productPlace) {
        this.productPlace = productPlace;
    }

    public String getSellingPlace() {
        return sellingPlace;
    }

    public void setSellingPlace(String sellingPlace) {
        this.sellingPlace = sellingPlace;
    }

    public String getUseFrequence() {
        return useFrequence;
    }

    public void setUseFrequence(String useFrequence) {
        this.useFrequence = useFrequence;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
    
    
}