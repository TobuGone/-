package com.kapphk.pms.util;

/**
 * @author 胡子
 * since 2018年4月27日
 */
public class ChineseJudgeUtils {
	/**
	 * 判断字符是否是中文
     * @param  str 字符串
     * @return 是否是乱码
     */
	 public static boolean isMessyCode(String str) {
	        if (str.getBytes().length == str.length()) {
	            return false;
	        } 
	        return true;    
	    }
}
