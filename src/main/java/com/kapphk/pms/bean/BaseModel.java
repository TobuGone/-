package com.kapphk.pms.bean;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.kapphk.pms.util.Contents;


/**
 * model模版类 用于继承
 * mysql 表增加该字段sql  只需修改数据库名称与表名称  或者 after 的字段名称
 	ALTER TABLE `kapphk_qcy`.`qcy_hot` 
	ADD COLUMN `status` INT(2) DEFAULT '1' NOT NULL COMMENT '状态: 1:正常 2:已失效 -1:已删除' AFTER `id`,     
	ADD COLUMN `create_time` DATETIME NOT NULL COMMENT '创建时间' AFTER `status`,    
	ADD COLUMN `modify_time` DATETIME NULL COMMENT '最后修改时间' AFTER `create_time`;
 * @author Folo 2014-10-14
 */
public class BaseModel {
	/**
	 * create_time 创建时间
	 */
	@JSONField(format=Contents.FORMIT)
	private String createTime;
	
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 * modify_time 修改时间
	 */
	@JSONField(format=Contents.FORMIT)
	private Date modifyTime;
	
	/**
	 * status 数据状态 1:正常 2:已失效 -1:已删除
	 */
	private Integer status;

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public STATUS getEState(){
		return STATUS.getStatus(this.status);
	}
	
	public void setEState(STATUS status){
		this.setStatus(status.getVal());
	}
	
	/**
	 * 数据状态枚举
	 * 1=OK=正常
	 * 2=ERROR=已失效
	 * 3=DELETE=已删除
	 * @author Folo 2014年10月15日
	 */
	public static enum STATUS{
		OK(1),// 正常
		ERROR(2),// 已失效
		DELETE(-1);// 已删除
		
		private Integer val;
		private STATUS(Integer val){
			this.val = val;
		}
		
		public int getVal(){
			return val;
		}
		
		/**
		 * 根据数字获取状态(默认OK)
		 * @author Folo 2014年10月15日
		 */
		public static STATUS getStatus(Integer val){
			if(null == val) return STATUS.ERROR;
			STATUS s[] = STATUS.values();
			for (STATUS state : s) {
				if(val == state.getVal()) return state;
			}
			return STATUS.OK;
		}
	}
}
