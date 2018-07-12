package com.kapphk.pms.bean;

/**
 * 评论：sys_menu_book_comment
 * @author zoneyu 2016-01-26
 * 
*/
public class BeanMenuBookComment extends BaseModel {

    /**
     * 表字段：sys_menu_book_comment.id 注释：评论id
     * @author zoneyu 2016-01-26
     */
    private Long id;
    /**
     * 表字段：sys_menu_book_comment.user_id 注释：用户id
     * @author zoneyu 2016-01-26
     */
    private Long userId;
    /**
     * 表字段：sys_menu_book_comment.menu_book_id 注释：菜谱id
     * @author zoneyu 2016-01-26
     */
    private Long menuBookId;
    /**
     * 表字段：sys_menu_book_comment.content 注释：评论内容
     * @author zoneyu 2016-01-26
     */
    private String content;
    
    /**
     * 是否展示
     * 更新于2018/03/12  huzi
     */
    private Integer isShow;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    public Integer getIsShow()
    {
      return this.isShow;
    }
    
    public void setIsShow(Integer isShow)
    {
      this.isShow = isShow;
    }
}