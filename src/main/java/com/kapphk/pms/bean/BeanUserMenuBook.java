package com.kapphk.pms.bean;

/**
 * 菜谱收藏表
 * ：sys_user_menu_book
 * @author zoneyu 2016-01-26
*/
public class BeanUserMenuBook extends BaseModel {

    /**
     * 表字段：sys_user_menu_book.id 注释：
     * @author zoneyu 2016-01-26
     */
    private Long id;
    /**
     * 表字段：sys_user_menu_book.user_id 注释：收藏、上传菜谱人id
     * @author zoneyu 2016-01-26
     */
    private Long userId;
    /**
     * 表字段：sys_user_menu_book.menu_book_id 注释：菜谱id
     * @author zoneyu 2016-01-26
     */
    private Long menuBookId;
    /**
     * 表字段：sys_user_menu_book.type 注释：1=上传；2=收藏
     * @author zoneyu 2016-01-26
     */
    private String type;
    
    /**
     * Lotus 2016-4-13
     */
    private String filePath;
    
    /**
     * Lotus 2016-04-28
     */
    private String isTop;

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

    public Long getMenuBookId() {
        return menuBookId;
    }

    public void setMenuBookId(Long menuBookId) {
        this.menuBookId = menuBookId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    public String getIsTop() {
        return isTop;
    }

    public void setIsTop(String isTop) {
        this.isTop = isTop;
    }
}