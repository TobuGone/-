package com.kapphk.pms.util;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 此工具类为校验工具类,校验字符串、数组、集合是否为空
 * @author zoneyu  14-5-27
 */
public class ValidateUtils {

	/**
	 * 私有化构造函数,不允许创建实体
	 */
	private ValidateUtils(){} ;
	
	/**
	 * 校验数组是否为空，为空为true，反之为false 
	 * @author zoneyu  14-5-27
	 */
	public static boolean isEmpty(Object... args){
		if(args != null && args.length > 0){
			return false ;
		}
		return true ;
	}
	
	/**
	 * 校验集合是否为空，为空为true，反之为false 
	 * @author zoneyu  14-5-27
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmptyForCollection(Collection list){
		if(list != null && list.size() > 0){
			return false ;
		}
		return true ;
	}
	
	/**
	 * 校验集合是否为空，为空为true，反之为false 
	 * @author zoneyu  14-5-27
	 */
	public static boolean isEmptyForMap(Map<Object,Object> map){
		if(map != null && map.size() > 0){
			return false ;
		}
		return true ;
	}
	
	/**
	 * 判断数组是否为空，为空为TRUE，反之为FALSE
	 * @param obj
	 * @return
	 */
	public static boolean isempty(Object[] obj){
		if(obj != null && obj.length > 0){
			return false ;
		}else{
			return true ;
		}
	}
	
	/**
	 * 校验字符串是否为空，为空为true，反之为false 
	 * @author zoneyu  14-5-27
	 */
	public static boolean isBlank(Object str){
		if(str != null && !"".equals(str) && !"null".equals(str) && !"(null)".equals(str)){
			return false ;
		}
		return true ;
	}
	
	/**
	 * 判断是否为固定电话，是为true,反之为false
	 * @author zoneyu  14-8-12
	 */
	public static boolean isTelephone(String phone){
		String regex = "0\\d{2}-\\d{8}|0\\d{2}-\\d{7}|0\\d{3}-\\d{7}|0\\d{3}-\\d{8}" ;
		Pattern p = Pattern.compile(regex) ;
		if(!isBlank(phone)){
			Matcher m = p.matcher(phone) ;
			return m.matches() ;
		}else{
			return false ;
		}
	}
	
	/**
	 * 判断是否为手机号，是为true,反之为false
	 * @author zoneyu  14-8-12
	 */
	public static boolean isMobilePhone(String phone){
		String regex = "1[3|4|5|7|8]\\d{9}" ;
		Pattern p = Pattern.compile(regex) ;
		if(!isBlank(phone)){
			Matcher m = p.matcher(phone) ;
			return m.matches() ;
		}else{
			return false ;
		}
	}
	
	/**
	 * 判断是否为邮箱，是为true,反之为false
	 * @author zoneyu  14-8-12
	 */
	public static boolean isEmail(String email){
		String regex = "(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w{2,3}){1,3})" ;
		Pattern p = Pattern.compile(regex) ;
		if(!isBlank(email)){
			Matcher m = p.matcher(email) ;
			return m.matches() ;
		}else{
			return false ;
		}
	}
	
	
	/**
	 * 判断是否为数字，是为true,反之为false
	 * @author Lotus  16-04-06
	 */
	public static boolean isNumeric(String str){  
		  for (int i = str.length();--i>=0;){    
		   if (!Character.isDigit(str.charAt(i))){  
		    return false;  
		   }  
		  }  
		  return true;  
	}
	
	public static void main(String[] args) {
		System.out.println(isEmail("a@qq.com.cn"));
	}
}
