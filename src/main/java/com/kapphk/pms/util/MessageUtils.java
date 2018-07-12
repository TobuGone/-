package com.kapphk.pms.util;

import java.io.IOException;
import java.net.URLEncoder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * 发送短信接口
 * @author zoneyu 14-6-19
 */
public class MessageUtils {
	
	private static String company = "PMS" ;
	private static String key = "3d75e808b1014854921d404e6144251c" ;
	
	public static String sendMessage(String phone,String value) throws Exception{
		System.out.println("验证码："+value);
		String values = URLEncoder.encode(("#code#=" + value + "&#company#=" + company),"UTF-8");
		String urlstr = "http://apis.haoservice.com/sms/send"
				      + "?mobile=" + phone 
				      + "&tpl_id=2&tpl_value=" + values
				      + "&key=" + key ;
		String res = null;
		HttpClient client = new HttpClient(new MultiThreadedHttpConnectionManager());	
		HttpMethod httpmethod = new GetMethod(urlstr);
		try {
			int statusCode = client.executeMethod(httpmethod);
			if (statusCode == HttpStatus.SC_OK) {
				res = httpmethod.getResponseBodyAsString();
			}
		} catch (HttpException e) { 
		} catch (IOException e) { 
		} finally {
			httpmethod.releaseConnection();
		}
		System.out.println(res);
		return res;
	
	}
	
	/**
	 * 通过get的方式发送信息给信息服务器
	 * @param urlstr
	 * @return
	 */
	public static String doPostRequest(String urlstr) {
		String res = null;
		HttpClient client = new HttpClient(new MultiThreadedHttpConnectionManager());

		HttpMethod httpmethod = new PostMethod(urlstr);
		try {
			int statusCode = client.executeMethod(httpmethod);
			if (statusCode == HttpStatus.SC_OK) {
				res = httpmethod.getResponseBodyAsString();
			}
		} catch (HttpException e) { 
		} catch (IOException e) { 
		} finally {
			httpmethod.releaseConnection();
		}
		return res;
	} 
	
	public static void main(String[] args) throws Exception {
		sendMessage("13247551948","1234") ;
	}
}
