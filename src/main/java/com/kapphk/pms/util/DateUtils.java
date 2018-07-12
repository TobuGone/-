package com.kapphk.pms.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 日期工具类
 * @author zoneyu tang 14-5-29
 */
public class DateUtils {

	/**
	 * 私有构造函数
	 */
	private DateUtils(){} ;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	
	/**
	 * 获取当月的第一天
	 * @author huangzh 2015-09-17
	 * @return
	 */
	public static String getFirstDay(){
		Calendar c = Calendar.getInstance();   
		
        c.add(Calendar.MONTH, 0);
        
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
        
        String first = sdf.format(c.getTime());
        
        return first;
	}
	
	/**
	 * 获取当月的最后一天
	 * @author huangzh 2015-09-17
	 * @return
	 */
	public static String getLastDay(){
		Calendar ca = Calendar.getInstance();   
		
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH)); 
        
        String last = sdf.format(ca.getTime());
        
        return last;
	}
	
	
	/**
	 * 获取当前系统时间
	 * @author zoneyu tang 14-5-29
	 * @param pattern   日期格式
	 */
	public static String getLocalDate(String pattern){
		SimpleDateFormat sf = new SimpleDateFormat(pattern) ;
		return sf.format(new Date()) ;
	}
	
	/**
	 * 将日期格式转换为字符串
	 * @param pattern
	 * @param date
	 * @return
	 */
	public static String formartDate(String pattern,Date date){
		SimpleDateFormat sf = new SimpleDateFormat(pattern) ;
		String dateStr = sf.format(date) ;
		return dateStr ;
	}
	
	/**
	 * 将字符串转换为日期格式
	 * @param pattern
	 * @param date
	 * @return
	 * @throws Exception 
	 */
	public static Date parseDate(String pattern,String date) throws Exception{
		SimpleDateFormat sf = new SimpleDateFormat(pattern) ;
		Date dateTime = sf.parse(date) ;
		return dateTime ;
	}
	
	/**
	 * 获取7位随机数
	 * @return
	 */
	public static String getRadom(){
		long i = System.nanoTime() ;
		String str = String.valueOf(i) ;
		str = str.substring(7, 11) ;
		return str ;
	}
	
	/**
	 * 获取当前系统时间跟date之前的间隔时间（单位为秒）
	 * @param date   日期时间
	 * @throws Exception 
	 */
	public static long getTime(String date) throws Exception{
		Date nativeDate = parseDate("yyyy-MM-dd HH:mm:ss", date) ;
		//当前传入的时间
		long start = nativeDate.getTime() ;
		long local = new Date().getTime() ;
		long i = ((local - start)/1000);
		return i ;
	}
	
	/**
	 * 获取当前时间格式为(yyyy-MM-dd HH:mm:ss)
	 * @return
	 */
	public static String getLocalDate(){
		return formartDate("yyyy-MM-dd HH:mm:ss", new Date()) ;
	}
	
	/**
	 * 计算从当前日期开始，time月后的日期
	 * @param time   月
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
	
	/**
	 * 获取前一天
	 * @param time 
	 * @return
	 */
	public static String getYestoday(){
		Calendar c = Calendar.getInstance() ;
		c.add(Calendar.DATE, -1) ;
		Date d = c.getTime() ;
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd") ;
		String date = sf.format(d) ;
		return date ;
	}
	
	/**
	 * 获取当前日期radom天后的日期,包括今天
	 * @param radom    天数
	 * @return
	 */
	public static String getNextDay(int radom,String regex){
		Calendar c = Calendar.getInstance() ;
		c.add(Calendar.DATE, radom-1) ;
		Date d = c.getTime() ;
		SimpleDateFormat sf = new SimpleDateFormat(regex) ;
		String date = sf.format(d) ;
		return date ;
	}
	
	/**
	 * 获取一当前日期开始radom天后的日期
	 * @param radom  天数
	 * @return
	 */
	public static String getRamdomDay(int radom,String regex){
		Calendar c = Calendar.getInstance() ;
		c.add(Calendar.DATE, radom) ;
		Date d = c.getTime() ;
		SimpleDateFormat sf = new SimpleDateFormat(regex) ;
		String date = sf.format(d) ;
		return date ;
	}
	
	/**
	 * 比较startDate跟endDate两个日期的大小,如果结束时间小于开始时间，返回false,反之返回true
	 * @param startDate   开始时间
	 * @param endDate     结束时间
	 * @return
	 * @throws Exception 
	 */
	public static boolean compareDate(String startDate,String endDate,String regex) throws Exception{
		SimpleDateFormat sf = new SimpleDateFormat(regex) ;
		Long start = sf.parse(startDate).getTime() ;
		long end = sf.parse(endDate).getTime() ;
		if(end <= start){
			return false ;
		}else{
			return true ;
		}
	}
	
	/**
	 * 获取开始时间到结束时间的中间段数值（0-24分96段，每十五分钟为一段，值从0开始，即00：15位0）
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static String getTimePointArea(String startTime,String endTime){
		Map<String, Integer> map = initMap() ;
		Integer start = map.get(startTime) ;
		Integer end = map.get(endTime) ;
		String time = "" ;
		if(start+1 == end){
			time = end+"" ;
		}else{
			//是否跨天
			if(start < end){//没有跨天
				for(int i = start+1 ; i <= end ; i++){
					time = time + i +"," ;
				}
			}else{//跨天
				for(int i = start+1 ; i <= 47 ; i++){
					time = time + i +"," ;
				}
				for(int i = 0 ; i <= end ; i++){
					time = time + i +"," ;
				}
			}
		}
		if(time.endsWith(","))time = time.substring(0, time.length() - 1) ;
		return time;
	}
	
	/**
	 * 将00:00-23:45   分48段，每30分钟为一段，值从0开始
	 */
	public static Map<String,Integer> initMap(){
		String[] minutes = {"00","30"} ;
		Map<String,Integer> map = new LinkedHashMap<String, Integer>() ;
		int k = 0 ;
		for(int i = 0 ; i < 24 ; i++){//时
			if(i == 0){
				for(int j = 1 ; j < minutes.length ; j++){
					String key = i +":"+minutes[j] ;
					if(key.length() < 5)key = "0"+key ;
					map.put(key, k) ;
					k++ ;
				}
			}else{
				for(int j = 0 ; j < minutes.length ; j++){
					String key = i +":"+minutes[j] ;
					if(key.length() < 5)key = "0"+key ;
					map.put(key, k) ;
					k++ ;
				}
			}
		}
		map.put("00:00", 47) ;
		return map ;
	}
	
	
	
	public static String getTimeAdd(String time) throws ParseException{
		
		int min = Integer.parseInt(time.substring(time.length()-2));
		
		if(0< min && min < 30){
			int addmin = 30 - min;
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			
	    	Date startdate =sdf.parse(time);

	    	Calendar calendar = Calendar.getInstance();

	    	calendar.setTime(startdate);
	    	
	    	calendar.add(Calendar.MINUTE, addmin);
	    	
	    	time = sdf.format(calendar.getTime());
			
		}else if(min > 30 && min <= 59){
			int addmin = 60 - min;
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			
	    	Date startdate =sdf.parse(time);

	    	Calendar calendar = Calendar.getInstance();

	    	calendar.setTime(startdate);
	    	
	    	calendar.add(Calendar.MINUTE, addmin);
	    	
	    	time = sdf.format(calendar.getTime());
	    	
		}
		
		return time;
	}
	
	
	public static void main(String[] args) throws ParseException {
		System.out.println(getFirstDay());
		System.out.println(getLastDay());
	}
}
