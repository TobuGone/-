package com.kapphk.pms.bean;

/**
 * 步骤：sys_menubook_procedure
 * @author zoneyu 2016-01-22
*/
public class BeanMenubookProcedure extends BaseModel {

    /**
     * 表字段：sys_menubook_procedure.id 注释：步骤id
     * @author zoneyu 2016-01-22
     */
    private Long id;
    /**
     * 表字段：sys_menubook_procedure.menu_id 注释：菜谱id
     * @author zoneyu 2016-01-22
     */
    private Long menuId;
    /**
     * 表字段：sys_menubook_procedure.minute 注释：分
     * @author zoneyu 2016-01-22
     */
    private String minute;
    /**
     * 表字段：sys_menubook_procedure.second 注释：秒
     * @author zoneyu 2016-01-22
     */
    private String second;
    /**
     * 表字段：sys_menubook_procedure.describes 注释：步骤描述
     * @author zoneyu 2016-01-22
     */
    private String describes;
    /**
     * 表字段：sys_menubook_procedure.foods 注释：每个步骤的食材
     * @author zoneyu 2016-01-22
     */
    private String foods;
    
    
    private BeanMenuBook menuBook;
    
    

    public BeanMenuBook getMenuBook() {
		return menuBook;
	}

	public void setMenuBook(BeanMenuBook menuBook) {
		this.menuBook = menuBook;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }

    public String getFoods() {
        return foods;
    }

    public void setFoods(String foods) {
        this.foods = foods;
    }
}