package com.kapphk.pms.bean;

/**
 * 设备类
 * 主键：sys_equipment
 * @author zoneyu 2016-01-19
*/
public class BeanEquipment extends BaseModel {

    /**
     * 表字段：sys_equipment.id 注释：主键id
     * @author zoneyu 2016-01-19
     */
    private Long id;
    /**
     * 表字段：sys_equipment.uid 注释：用户id
     * @author zoneyu 2016-01-19
     */
    private Long uid;
    /**
     * 表字段：sys_equipment.number 注释：设备编号
     * @author zoneyu 2016-01-19
     */
    private String number;
    /**
     * 表字段：sys_equipment.voltage 注释：系统电压
     * @author zoneyu 2016-01-19
     */
    private String voltage;
    /**
     * 表字段：sys_equipment.electric 注释：系统电流
     * @author zoneyu 2016-01-19
     */
    private String electric;
    /**
     * 表字段：sys_equipment.output_power 注释：输出功率
     * @author zoneyu 2016-01-19
     */
    private String outputPower;
    /**
     * 表字段：sys_equipment.pot_temperature 注释：实际锅温
     * @author zoneyu 2016-01-19
     */
    private String potTemperature;
    /**
     * 表字段：sys_equipment.room_temperature 注释：实际室温
     * @author zoneyu 2016-01-19
     */
    private String roomTemperature;
    
    
    private String [] wifis;

    public String[] getWifis() {
		return wifis;
	}

	public void setWifis(String[] wifis) {
		this.wifis = wifis;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public String getElectric() {
        return electric;
    }

    public void setElectric(String electric) {
        this.electric = electric;
    }

    public String getOutputPower() {
        return outputPower;
    }

    public void setOutputPower(String outputPower) {
        this.outputPower = outputPower;
    }

    public String getPotTemperature() {
        return potTemperature;
    }

    public void setPotTemperature(String potTemperature) {
        this.potTemperature = potTemperature;
    }

    public String getRoomTemperature() {
        return roomTemperature;
    }

    public void setRoomTemperature(String roomTemperature) {
        this.roomTemperature = roomTemperature;
    }
}