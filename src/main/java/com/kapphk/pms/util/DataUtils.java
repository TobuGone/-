package com.kapphk.pms.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * 数据转换工具类
 * @author zoneyu 14-5-30
 */
@Component
public class DataUtils {

	/**
	 * 获取邀请码
	 */
	public static String getInvitationCode(){
		char C = 'A' ;
		C=(char)(C+(int)(Math.random()*26));
		char c = 'a' ;
		c=(char)(c+(int)(Math.random()*26));
		String str = String.valueOf(C)+String.valueOf(c)+getCode()+getCode()+getCode()+getCode() ;
		return str ;
	}
	
	public static char getCode(){
		char d = '0' ;
		d=(char)(d+(int)(Math.random()*10));
		return d ;
	}
	
	/**
	 * 获取四位随机整数
	 */
	public String getRadom(){
		double d = Math.random()*10000 ;
		String str = String.valueOf(d).substring(0, 4) ;
		return str ;
	}
	
	/**
	 * 获取六位随机数
	 */
	public static String getSixRadom(){
		String code = "" ;
		for(int i = 0 ; i < 6 ; i++){
			code += getCode()+"" ;
		}
		return code ;
	}
	
	/**
	 * 获取任意位数的随机数
	 */
	public static String getRadom(int x){
		String code = "" ;
		for(int i = 0 ; i < x ; i++){
			code += getCode()+"" ;
		}
		return code ;
	}
	
	/**
	 * 将数据拼装成easyui分页格式
	 * @author zoneyu 14-6-2
	 * @param count
	 * @param list
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Result mergeData(int count,List list){
		Result rs = new Result();
		rs.put("total", count) ;
		if(list==null){
			list = new ArrayList();
		}
		rs.put("rows", list) ;
		return rs ;
	}
	
	/**
	 * 将list中的值转换成String，值以逗号隔开
	 */
	@SuppressWarnings("rawtypes")
	public static String list2String(Collection coll){
		String str = "" ;
		if(!ValidateUtils.isEmptyForCollection(coll)){
			Iterator iter = coll.iterator() ;
			while(iter.hasNext()){
				str += iter.next() +",";
			}
			str = str.substring(0, str.length() - 1) ;
		}
		return str;
	}
	
	/**
	 * 将list中的值转换成String，值以逗号隔开
	 */
	@SuppressWarnings("rawtypes")
	public static String list2String1(Collection coll){
		String str = "" ;
		if(!ValidateUtils.isEmptyForCollection(coll)){
			Iterator iter = coll.iterator() ;
			while(iter.hasNext()){
				str += "'"+iter.next() +"',";
			}
			str = str.substring(0, str.length() - 1) ;
		}
		return str;
	}

	/**
	 * 生成订单号
	 */
	public static String getOrderNo() {
		return DateUtils.getLocalDate("yyyyMMddHHmmss")+DataUtils.getRadom(12);
	}

	/**
	 * string2List
	 */
	public static List<Long> string2List(String str) {
		List<Long> list = new ArrayList<Long>() ;
		if(!ValidateUtils.isBlank(str)){
			String[] strs = str.split(",") ;
			for(String s : strs){
				list.add(Long.valueOf(s)) ;
			}
		}
		return list;
	}
	
	/**
	 * 计算经纬度差值
	 * @P
	 */
	public static Double formatDouble(Double d){
		d = (1d/111d)*d ;
		String str = String.format("%.6f", d) ;
		return Double.valueOf(str) ;
	}
	
	/**
	 * 判断是否为空，为空返回""
	 * @param obj
	 * @return
	 */
	public static Object isNull(Object obj){
		if(null == obj || "".equals(obj) || "null".equals(obj)){
			return "" ;
		}else{
			return obj ;
		}
	}
	
	
	
	/**   
	* @Title: BeanToMap    
	* @Description: 将Bean 对象转换为 Map
	* @param @param obj
	* @param @return
	* @return Map<String,Object>    返回类型    
	* @throws    
	*/
	public static Map<String, Object> BeanToMap(Object obj) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
				if(obj != null ){
					Class<?> clz = obj.getClass();
					Field[] fields = clz.getDeclaredFields();
					for(Field field:fields){
						if(!field.isAccessible()){
							field.setAccessible(true);
						}
						String fieldName= field.getName();
						//Object value = field.get(obj);
					    String methodName = fieldName.substring(0, 1).toUpperCase()+ fieldName.substring(1);
					    Method method = clz.getMethod("get" + methodName);
			            Object value    = method.invoke(obj);
					    if(ValueUtil.isNotEmpty(value)){  
					    	map.put(fieldName, value);
					    }
					};
				 }

		return map;
	}

	/**
	 * 将字符串中的数字按照升序排序
	 */
	public static String sortString(String str) {
		String string = "" ;
		if(null != str && !"".equals(str)){
			str = str.startsWith(",") ? str.substring(1, str.length()) : str ;
			str = str.endsWith(",") ? str.substring(0, str.length() - 1) : str ;
			str = str.replaceAll(",,", ",") ;
			String[] strs = str.split(",") ;
			List<Integer> list = new ArrayList<Integer>() ;
			for(int i = 0 ; i < strs.length ; i++){
				list.add(Integer.valueOf(strs[i])) ;
			}
			Collections.sort(list) ;
			for(Integer i : list){
				string += i +"," ;
			}
			string = string.substring(0,string.length()-1) ;
		}
		return string ;
	}
	
	/**
	 * 判断string1是否包含string2,包含返回true，反之返回false
	 */
	public static boolean isContains(String string1,String string2){
		String[] strs = string1.split(",") ;
		boolean b = false ;
		for(String s : strs){
			if(s.equals(string2)){
				b = true ;
				break ;
			}
		}
		return b ;
	}
	
	public static void main(String[] args) {
		System.out.println(sortString(",23,54,12,56,,98,49,")) ;
	}
	
	/**
	 *判断字符串是否为数字
	 */
	public static boolean isNumeric(String string) {
		for (int i = string.length(); --i >= 0;) {
			if (!Character.isDigit(string.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
}
