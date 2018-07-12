package com.kapphk.pms.bean;


/**
 * 菜谱分类
 * ：sys_menu_category
 * @author zoneyu 2016-01-20
*/
public class BeanMenuCategory extends BaseModel {

    /**
     * 表字段：sys_menu_category.id 注释：id
     * @author zoneyu 2016-01-20
     */
    private Long id;
    /**
     * 表字段：sys_menu_category.name 注释：菜谱分类名称
     * @author zoneyu 2016-01-20
     */
    private String name;
    /**
     * 表字段：sys_menu_category.pid 注释：父id
     * @author zoneyu 2016-01-20
     */
    private Long pid;
    /**
     * 表字段：sys_menu_category.logo_path 注释：缩略图
     * @author zoneyu 2016-01-20
     */
    private String logoPath;
    /**
     * 表字段：sys_menu_category.level 注释：分类级别
     * @author zoneyu 2016-01-20
     */
    private String level;
    /**
     * 表字段：sys_menu_category.recommend 注释：是否推荐（1、推荐；0、不推荐）
     * @author zoneyu 2016-01-20
     */
    private String recommend;
    
    /**
     * c_type 注释:菜谱类别  (1:菜系 2:口味)
     */
    private String cType;
    

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

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }
    
    public String getCType(){
    	return cType;
    }
    
    public void setCType(String cType){
    	this.cType=cType;
    }
}