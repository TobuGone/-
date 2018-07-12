package com.kapphk.pms.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;


public class getIP {
   public static void main(String[] args) throws UnknownHostException {
	  System.out.println(getSystemLocalIp()); 
   }

   private static final Logger logger = Logger.getLogger(getIP.class);
   
   //获得系统属性集  
   public static Properties props=System.getProperties();
   //操作系统名称
   public static String OS_NAME=getPropertery("os.name");
   //行分页符
   public static String OS_LINE_SEPARATOR=getPropertery("line.separator");
   //文件分隔符号
   public static String OS_FILE_SEPARATOR=getPropertery("file.separator");
   
  
   public static String getSystemLocalIp() throws UnknownHostException{
       InetAddress inet=null;
       String osname=getSystemOSName();
       try {
           //针对window系统
           if(osname.contains("Windows")){
                   inet=getWinLocalIp();
           //针对linux系统
           }else if(osname.contains("Linux")){
                   inet=getUnixLocalIp();
           }
           if(null==inet){
               throw new UnknownHostException("主机的ip地址未知");
           }
       }catch (SocketException e) {
           logger.error("获取本机ip错误"+e.getMessage());
           throw new UnknownHostException("获取本机ip错误"+e.getMessage());
       }
       return inet.getHostAddress();
   }
  
   public static String getSystemOSName() {
        //获得系统属性集  
       Properties props=System.getProperties();
       //操作系统名称
       String osname=props.getProperty("os.name");  
       if(logger.isDebugEnabled()){
           logger.info("the ftp client system os Name "+osname);
       }
       return osname;
   }
  
   public static String getPropertery(String propertyName){
       return props.getProperty(propertyName);
   }
   
   
  
   private static InetAddress getWinLocalIp() throws UnknownHostException{
       InetAddress inet = InetAddress.getLocalHost();     
       System.out.println("本机的ip=" + inet.getHostAddress()); 
        return inet;
   }
  
   private static InetAddress getUnixLocalIp() throws SocketException{
           Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
           InetAddress ip = null; 
           while(netInterfaces.hasMoreElements())     
           {     
               NetworkInterface ni= (NetworkInterface)netInterfaces.nextElement();     
               ip=(InetAddress) ni.getInetAddresses().nextElement();     
               if( !ip.isSiteLocalAddress()     
                       && !ip.isLoopbackAddress()     
                       && ip.getHostAddress().indexOf(":")==-1)     
               {     
                   return ip;
               }     
               else    
               {     
                   ip=null;     
               }     
           }   
       return null;
   }

   
   
   public static String getIpAddr(HttpServletRequest request) {
		
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		
		return ip;
	}
   
   //获取服务器地址
   public static String getServerName(HttpServletRequest request){
	   
	   return request.getServerName();
   }
   
} 
