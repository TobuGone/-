package com.kapphk.pms.util ;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * 通过地址查询经纬度
 */
public class GetLatAndLngByBaidu {
	
	/**
	 * 通过地址调用百度地图获取返回值(Json)
	 * @param addr 查询的地址
	 * @return
	 * @throws Exception 
	 */
	public static String getCoordinate(String addr) throws UnsupportedEncodingException, Exception{
		String address = null;
		StringBuffer buffer = new StringBuffer() ;
		if(ValidateUtils.isBlank(addr)){
			addr = "广东省广州市天河区" ;
		}
		address = java.net.URLEncoder.encode(addr, "UTF-8");
		String key = "0BYhFDWPp9nGoLGVnGGo026f";
		String url = String.format("http://api.map.baidu.com/geocoder?address=%s&output=json&key=%s",address, key);
		URL myURL = null;
		URLConnection httpsConn = null;
		myURL = new URL(url);
		httpsConn = (URLConnection) myURL.openConnection();// 不使用代理
		if (httpsConn != null) {
			InputStreamReader insr = new InputStreamReader(
					httpsConn.getInputStream(), "UTF-8");
			BufferedReader br = new BufferedReader(insr);
			String data = null;
			while ((data = br.readLine()) != null) {
				//System.out.println(data);
				buffer.append(data) ;
			}
		}
		return buffer.toString();
	}
	
	
	/**
	 * 解析json字符串获得经纬度
	 * notice:本方法是写死的，不能通用
	 * @param jsonData
	 * @return
	 */
	public static Map<Object,Object> parseJson(String jsonData){
		JSONObject rootJson = JSONObject.parseObject(jsonData) ;
		Object status = rootJson.get("status") ;
		JSONObject obj = rootJson.getJSONObject("result") ;
		Map<Object,Object> dataMap = new HashMap<Object, Object>() ;
		Map<String,Object> map = new HashMap<String, Object>() ;
		if(!ValidateUtils.isBlank(status) && "ok".equals(String.valueOf(status).toLowerCase())){
			JSONObject location = obj.getJSONObject("location") ;
			Object lng = location.get("lng") ;
			Object lat = location.get("lat") ;
			map.put("lng", lng) ;
			map.put("lat", lat) ;
			dataMap.put("status", "success") ;
			dataMap.put("location", map) ;
		}else{
			dataMap.put("status", "fail") ;
			dataMap.put("location", map) ;
		}
		return dataMap ;
	}
	
	/**
	 * 通过地址获取经纬度
	 * @param addr
	 * @return
	 * @throws Exception 
	 */
	public static Map<Object, Object> getLocation(String addr) {
		Map<Object, Object> map = null ;
		try {
			String jsonData = getCoordinate(addr) ;
			map = parseJson(jsonData);
		} catch (Exception e) {
			//出现异常，默认地址
			map = getLocation("广东省清远市");
		}
		return map ;
	}

	public static void main(String[] args) throws Exception {
		Map<Object, Object> map = getLocation("广东省广州市天河区天河区棠东东路234号");
		System.out.println(map.toString());
	}
}
