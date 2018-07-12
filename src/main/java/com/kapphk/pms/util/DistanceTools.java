package com.kapphk.pms.util;

/**
 * 根据经纬度来计算两地的距离，单位m
 * @author zoenyu 15-1-20
 */
public class DistanceTools {

	 private static final double EARTH_RADIUS = 6378137;
	 private static double rad(double d){
		 return d * Math.PI / 180.0;
	 }
	 
	 /**
	  * 根据两点间经纬度坐标（double值），计算两点间距离，单位为米
	  * @param lng1
	  * @param lat1
	  * @param lng2
	  * @param lat2
	  * @return
	  */
	 public static double GetDistance(double lng1, double lat1, double lng2, double lat2){
	    double radLat1 = rad(lat1);
	    double radLat2 = rad(lat2);
	    double a = radLat1 - radLat2;
	    double b = rad(lng1) - rad(lng2);
	    double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) + 
	 	Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
	    s = s * EARTH_RADIUS;
	    s = Math.round(s * 10000) / 10000;
	    return s;
	 }
	 
	 /**
	  * 获取两个经纬度之间的整数值（单位：km）
	  * @param lng1                 纬度1
	  * @param lat1                 经度1
	  * @param lng2                 纬度2
	  * @param lat2                 经度2
	  * @return
	  */
	 public static String getDistance(double lng1, double lat1, double lng2, double lat2){
		 double distance = GetDistance(lng1, lat1, lng2, lat2) ;
		 distance = distance/1000 ;
		 String str = String.format("%.2f", distance) ;
		 return str ;
	 }
	 
	 public static void main(String[] args){
		 String str = getDistance(23.890756,124.897654,23.890756,123.897654);
		 System.out.println("两点之间距离为: "+str+"km");
	 }
}
