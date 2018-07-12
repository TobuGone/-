package com.kapphk.pms.bean;

/**
 * 消息发布
 * 主键：sys_message_push
 * @author zoneyu 2016-01-13
*/
public class BeanMessagePush extends BaseModel {

    /**
     * 表字段：sys_message_push.id 注释：主键id
     * @author zoneyu 2016-01-13
     */
    private Long id;
    /**
     * 表字段：sys_message_push.title 注释：标题
     * @author zoneyu 2016-01-13
     */
    private String title;
    /**
     * 表字段：sys_message_push.content 注释：内容
     * @author zoneyu 2016-01-13
     */
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}