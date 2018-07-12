package com.kapphk.pms.util;

import com.alibaba.fastjson.JSONObject;

public class JsonResult {
	
	private  JSONObject    json;
	public JsonResult (){
		 json = new JSONObject();
	}
	
	private  static final  String     STATUS_ERROR   = "error" ;
	private  static final  String     STATUS_SUCCESS = "success" ;
	
	public void setErrorMessage(String msg){
		json.put("message", msg);
		json.put("status", JsonResult.STATUS_ERROR);
	};
	
	public void setSuccessMessage(String msg){
		json.put("message", msg);
		json.put("status", JsonResult.STATUS_SUCCESS);
	};
	
	public void setMessage(String msg){
		json.put("message", msg);
	};
	
	public void setStatus(String status){
		json.put("status", status);
	}

}
