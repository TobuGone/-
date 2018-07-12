package com.kapphk.pms.util;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 封装的返回结果对象
 * @author Folo 2014年10月15日
 */
public class Result {
	
	/**
	 * 状态 10001 成功
	 */
	private String status;
	/**
	 * 消息内容 
	 */
	private String msg;
	/**
	 * 返回结果数据
	 */
	private JSONObject data;
	/**
	 * 服务器时间
	 */
	@JSONField(format=Contents.FORMIT)
	private Date time = null;
	
	public Result() {
		this.status = "10001";
		this.msg = "成功";
		this.data = new JSONObject();
	}
	
	public Result(String status, String msg) {
		this.status = status;
		this.msg = msg;
	}

	public String getStatus() {
		return status;
	}

	public Result setStatus(String status) {
		this.status = status;
		if(Contents.ERROR.equals(status)){
			this.msg = "失败,未知错误";
		}else if(Contents.NOT_FOUND.equals(status)){
			this.msg = "{0},找不到该{1}！";
		}else if(Contents.PARAMS_ERROR.equals(status)){
			this.msg = "参数错误";
		}
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public Result setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public JSONObject getData() {
		return data;
	}
	
	public Result put(String key, Object value){
		data.put(key, value);
		return this;
	}

	public Date getTime() {
		return time == null ? new Date() : time;
	}

	public Result setTime(Date time) {
		this.time = time;
		return this;
	}
	
	public Result setError(MSG msg){
		this.setMsg(msg.getMsg());
		this.setStatus(msg.getStatus());
		return this;
	}
	
	/**
	 * 
	 * @author Folo 2014年10月20日
	 */
	public Result setError(MSG msg, String info){
		this.setStatus(msg.getStatus());
		if(ValueUtil.isEmpty(info)) this.setMsg(msg.getMsg());
		else this.setMsg(info);
		return this;
	}
	
	/**
	 * 动态替换提示内容
	 * @author Folo 2014年10月20日
	 */
	public Result setError(MSG msg,String... errors){
		String emg = msg.getMsg();
		for (int i = 0; i < errors.length; i++) {
			emg = emg.replace("{"+i+"}", errors[i]);
		}
		return this.setMsg(emg);
		
	}

	/**
	 * 消息枚举
	 * @author Folo 2014年10月20日
	 */
	public static enum MSG{
		/** 1000X 成功 */
		OK("10001", "成功"),
		/** 2000X 失败 */
		ERROR("20001", "失败,未知错误"),
		NOT_FOUND("20003", "{0},找不到该{1}！"),
		PARAMS_ERROR("20004", "参数错误,{0}"),
		/** 身份认证失效  */
		TOKEN_FAILURE("21001", "身份认证失效,请重新登录!"),
		/** 自定义错误 */
		CUS_ERROR("20005", "{0}");
		
		private String status = "0";
		private String msg = null;
		MSG(String status, String msg){
			this.status = status;
			this.msg = msg;
		}
		
		public String getMsg(){
			return msg;
		}
		
		public String getStatus(){
			return status;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(MSG.ERROR.status);
	}
}

