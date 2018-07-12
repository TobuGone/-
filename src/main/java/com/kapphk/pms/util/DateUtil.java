package com.kapphk.pms.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间处理工具类
 * @author Folo
 */
public class DateUtil {
	
	/**
	 * 字符串转换为date格式（默认格式化{@link Contents#FORMIT}）
	 * @param date 字符串时间
	 * @return {@link Date} 默认返回当前时间
	 */ 
	public static Date getDateTime(String date) {
		return getDateTime(null, date);
	}

	/**
	 * 从时间转换为字符串（默认格式化{@link Contents#FORMIT}）
	 * @param date 默认当前时间
	 * @return {@link String}
	 */
	public static String getNowDateTimeStr(Date date) {
		if(null == date) date = new Date();
		return getDateTimeStr(null, date);
	}

	/**
	 * 获取当前时间字符串（默认格式化{@link Contents#FORMITD}）
	 * @return {@link String}
	 */
	public static String getDate() {
		return getDateTimeStr(Contents.FORMITD, new Date());
	}

	/**
	 * 从日期转换为字符串
	 * @param format 格式化字符(默认为{@link Contents#FORMITD})
	 * @param inDate 要转换的时间, <strong>如果 inDate==null 返回 ""</strong>
	 * @return {@link String}
	 */
	public static String getDateTimeStr(String format, Date inDate) {
		format = (ValueUtil.isEmpty(format) ? Contents.FORMIT : format);
		if (inDate == null) return "";
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String dateString = formatter.format(inDate);
		return dateString;
	}

	/**
	 *  从字符串转成日期
	 * @param format(默认为{@link Contents#FORMIT})
	 * @param format 格式化字符(默认为{@link Contents#FORMIT})
	 * @param dataStr 字符串时间
	 * @return {@link Date}
	 */
	public static Date getDateTime(String format, String dataStr) {
		format = (ValueUtil.isEmpty(format) ? Contents.FORMIT : format);
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		try {
			return formatter.parse(dataStr);
		} catch (ParseException e) {
			return new Date();
		}
	}
	
	/**
	 * 计算从当前日期开始，time月 
	 * @param time
	 * @return
	 */
	public static String getRadomDate(int time){
		Calendar c = Calendar.getInstance() ;
		c.add(Calendar.MONTH, time) ;
		Date d = c.getTime() ;
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd") ;
		String date = sf.format(d) ;
		return date ;
	}
}
