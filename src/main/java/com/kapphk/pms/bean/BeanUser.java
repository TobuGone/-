package com.kapphk.pms.bean;

/**
 * 用户：sys_user
 * @author zoneyu 2016-01-28
*/
public class BeanUser extends BaseModel {

    /**
     * 表字段：sys_user.id 注释：用户id
     * @author zoneyu 2016-01-28
     */
    private Long id;
    /**
     * 表字段：sys_user.user_name 注释：用户账号（手机号码）
     * @author zoneyu 2016-01-28
     */
    private String userName;
    /**
     * 表字段：sys_user.password 注释：密码
     * @author zoneyu 2016-01-28
     */
    private String password;
    /**
     * 表字段：sys_user.nick_name 注释：用户昵称
     * @author zoneyu 2016-01-28
     */
    private String nickName;
    /**
     * 表字段：sys_user.logo_path 注释：用户头像路径
     * @author zoneyu 2016-01-28
     */
    private String logoPath;
    /**
     * 表字段：sys_user.contacter 注释：联系人
     * @author zoneyu 2016-01-28
     */
    private String contacter;
    /**
     * 表字段：sys_user.mobile_phone 注释：手机号
     * @author zoneyu 2016-01-28
     */
    private String mobilePhone;
    /**
     * 表字段：sys_user.contacter_provice 注释：联系人所在省份id
     * @author zoneyu 2016-01-28
     */
    private Long contacterProvice;
    /**
     * 表字段：sys_user.contacter_city 注释：联系人所在城市id
     * @author zoneyu 2016-01-28
     */
    private Long contacterCity;
    /**
     * 表字段：sys_user.contacter_district 注释：联系人所在区id
     * @author zoneyu 2016-01-28
     */
    private Long contacterDistrict;
    /**
     * 表字段：sys_user.contacter_address 注释：联系人详细地址
     * @author zoneyu 2016-01-28
     */
    private String contacterAddress;
    /**
     * 表字段：sys_user.province 注释：居住省ID
     * @author zoneyu 2016-01-28
     */
    private Long province;
    /**
     * 表字段：sys_user.city 注释：居住市ID
     * @author zoneyu 2016-01-28
     */
    private Long city;
    /**
     * 表字段：sys_user.district 注释：居住区ID
     * @author zoneyu 2016-01-28
     */
    private Long district;
    /**
     * 表字段：sys_user.address 注释：居住详细地址
     * @author zoneyu 2016-01-28
     */
    private String address;
    /**
     * 表字段：sys_user.sex 注释：性别（1、男；2、女;  3:保密）
     * @author zoneyu 2016-01-28
     */
    private String sex;
    /**
     * 表字段：sys_user.email 注释：邮箱
     * @author zoneyu 2016-01-28
     */
    private String email;
    /**
     * 表字段：sys_user.autograph 注释：签名
     * @author zoneyu 2016-01-28
     */
    private String autograph;
    /**
     * 表字段：sys_user.birth_province 注释：出生省id
     * @author zoneyu 2016-01-28
     */
    private Long birthProvince;
    /**
     * 表字段：sys_user.birth_city 注释：出生市id
     * @author zoneyu 2016-01-28
     */
    private Long birthCity;
    /**
     * 表字段：sys_user.cuisine_menu_category_ids 注释：用户菜系 (菜谱类型id) 多个菜系用逗号分隔
     * @author zoneyu 2016-01-28
     */
    private String cuisineMenuCategoryIds;
    /**
     * 表字段：sys_user.flavor_menu_category_ids 注释：用户口味 (菜谱类型id) 多个口味用逗号分隔
     * @author zoneyu 2016-01-28
     */
    private String flavorMenuCategoryIds;
    /**
     * 表字段：sys_user.last_time 注释：最后登录时间
     * @author huzi 2018-06-21
     */
    private String lastTime;
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getContacter() {
        return contacter;
    }

    public void setContacter(String contacter) {
        this.contacter = contacter;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public Long getContacterProvice() {
        return contacterProvice;
    }

    public void setContacterProvice(Long contacterProvice) {
        this.contacterProvice = contacterProvice;
    }

    public Long getContacterCity() {
        return contacterCity;
    }

    public void setContacterCity(Long contacterCity) {
        this.contacterCity = contacterCity;
    }

    public Long getContacterDistrict() {
        return contacterDistrict;
    }

    public void setContacterDistrict(Long contacterDistrict) {
        this.contacterDistrict = contacterDistrict;
    }

    public String getContacterAddress() {
        return contacterAddress;
    }

    public void setContacterAddress(String contacterAddress) {
        this.contacterAddress = contacterAddress;
    }

    public Long getProvince() {
        return province;
    }

    public void setProvince(Long province) {
        this.province = province;
    }

    public Long getCity() {
        return city;
    }

    public void setCity(Long city) {
        this.city = city;
    }

    public Long getDistrict() {
        return district;
    }

    public void setDistrict(Long district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAutograph() {
        return autograph;
    }

    public void setAutograph(String autograph) {
        this.autograph = autograph;
    }

    public Long getBirthProvince() {
        return birthProvince;
    }

    public void setBirthProvince(Long birthProvince) {
        this.birthProvince = birthProvince;
    }

    public Long getBirthCity() {
        return birthCity;
    }

    public void setBirthCity(Long birthCity) {
        this.birthCity = birthCity;
    }

    public String getCuisineMenuCategoryIds() {
        return cuisineMenuCategoryIds;
    }

    public void setCuisineMenuCategoryIds(String cuisineMenuCategoryIds) {
        this.cuisineMenuCategoryIds = cuisineMenuCategoryIds;
    }

    public String getFlavorMenuCategoryIds() {
        return flavorMenuCategoryIds;
    }

    public void setFlavorMenuCategoryIds(String flavorMenuCategoryIds) {
        this.flavorMenuCategoryIds = flavorMenuCategoryIds;
    }

	public String getLastTime() {
		return lastTime;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}


}