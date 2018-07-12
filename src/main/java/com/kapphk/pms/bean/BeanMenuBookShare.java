package com.kapphk.pms.bean;

/**
 * 菜谱分享：sys_menu_book_share 

 * @author huzi 2018-05-18
*/
public class BeanMenuBookShare extends BaseModel {

    /**
     * 表字段：sys_menu_book_share.id 注释：菜谱id
     * @author huzi 2018-05-18
     */
    private Long id;
        
    /**
     * 表字段：sys_menu_book_share.uid 注释：上传菜谱用户id
     * @author huzi 2018-05-18
     */
    private Long uid;
    /**
     * 表字段：sys_menu_book_share.name 注释：菜谱名称
     * @author huzi 2018-05-18
     */
    private String name;
    /**
     * 表字段：sys_menu_book_share.foods 注释：食材
     * @author huzi 2018-05-18
     */
    private String foods;
    /**
     * 表字段：sys_menu_book_share.logo_path 注释：菜谱封面图片
     * @author huzi 2018-05-18
     */
    private String logoPath;
    
    /**
     * 表字段：sys_menu_book_share.file_path 注释：文件路径
     */
    private String filePath;
    
    /**
     * 表字段：sys_menu_book_share.describes 注释：菜谱描述
     * @author huzi 2018-05-18
     */
    private String describes;
    
    
    
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
    
    public String getFilePath(){
    	return filePath;
    }
    
    public void setFilePath(String filePath){
    	this.filePath=filePath;
    }

}