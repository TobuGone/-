package com.kapphk.pms.util;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSONArray;

/**
 * 取值工具类
 * @author Folo
 *
 */
public class ValueUtil {
	/**
	 * 判断单个对象或多个是否存在空值数据<br/>
	 * 目前识别 <br/>
	 * {@link String} 识别空字符""<br/>
	 * {@link Integer} 识别<1的数据<br/>
	 * {@link Double} 识别<0的数据<br/>
	 * {@link JSONArray} 识别size<1的数据<br/>
	 * {@link CommonsMultipartFile} 识别文件内容为空的数据<br/>
	 * @param obj 要判断的对象
	 * @return 空:true，否则 ：false.
	 */
	public static boolean isEmpty(Object... obj) {
		if (null == obj) return true;
		for (Object o : obj) {
			if (null == o) return true;
			if (o instanceof String && String.valueOf(o).trim().length() < 1) return true;
			if (o instanceof Integer && ((Integer) o) < 1) return true;
			if (o instanceof Double && ((Double) o) < 0) return true;
			if (o instanceof JSONArray && ((JSONArray) o).size() < 1) return true;
			if (o instanceof CommonsMultipartFile && ((CommonsMultipartFile) o).getSize() < 1) return true;
		}
		return false;
		
	}
	
	/**
	 * 校验参数灿都是否 >= min 并且 <= max<br/>
	 * 目前识别 <br/>
	 * {@link String} 校验字符串长度 length >= min && length <= max<br/>
	 * @author Folo 2014年11月14日
	 * @return 通过校验:true 不通过:false
	 */
	public static boolean lengthCheck(Object o, int min, int max){
		if (o instanceof String && !ValueUtil.isEmpty(o) 
				&& String.valueOf(o).trim().length() >= min
				&& String.valueOf(o).trim().length() <= max)
			return true;
		return false;
	}
	
	/**
	 * 判定字符串取值范围：val是否在vals的范围
	 * @param val 要判断的数据
	 * @param vals 取值范围
	 * @return <code>true</code>:在<code>false</code>:不在
	 */
	public static boolean inValues(String val, String... vals){
		for (String string : vals) {
			if(!isEmpty(string,val) && val.equals(string)) return true;
		}
		return false;
	}
	
	/**
	 * 判断double取值范围:d是否在ds的范围
	 * @param d 要判断的数据
	 * @param ds 取值范围
	 * @return <code>true</code>:在<code>false</code>:不在
	 */
	public static boolean inNumber(double d, double... ds){
		for (double e : ds) {
			if(!isEmpty(e,d) && e == d) return true;
		}
		return false;
	}
	
	/**
	 * String转int
	 * @param str 要转换的数据
	 * @return 默认返回-999
	 */
	public static int toInteger(String str){
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			return -999;
		}
	}
	
	/**
	 * String转Long
	 * @param str 要转换的数据
	 * @return 默认返回-999
	 */
	public static long toLong(String str){
		try {
			return Long.parseLong(str);
		} catch (Exception e) {
			return -999;
		}
	}
	
	/**
	 * String转为double型
	 * @param str 要转换的数据
	 * @return 默认返回-999d
	 */
	public static double toDouble(String str){
		try {
			return Double.parseDouble(str);
		} catch (Exception e) {
			return -999d;
		}
	}
	
	public static  String toString(Object obj){
		return obj != null?obj.toString().trim():"";
	}
	
	
	public static boolean isEmpty(Object obj){
		return obj == null || "".equals(obj.toString().trim());
	} 
	
	public static boolean isNotEmpty(Object obj){
		return obj != null && !"".equals(obj.toString().trim());
	} 
	
}
