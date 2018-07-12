package com.kapphk.pms.bean;

/**
 * 第三方自动生成：sys_user_third_party
 * @author zoneyu 2016-04-11
*/
public class BeanUserThirdParty extends BaseModel {

    /**
     * 表字段：sys_user_third_party.id 注释：第三方自动生成id
     * @author zoneyu 2016-04-11
     */
    private Long id;
    /**
     * 表字段：sys_user_third_party.user_id 注释：用户id
     * @author zoneyu 2016-04-11
     */
    private Long userId;
    /**
     * 表字段：sys_user_third_party.uid 注释：第三方UID
     * @author zoneyu 2016-04-11
     */
    private String uid;

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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}