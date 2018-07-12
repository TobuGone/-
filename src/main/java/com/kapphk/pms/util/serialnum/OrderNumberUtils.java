package com.kapphk.pms.util.serialnum;




  
public class OrderNumberUtils {  
	 
	    public static String getOrderNumber() {
	    	
	    	SerialNumber serial = new FileEveryDaySerialNumber(5, "EveryDaySerialNumber"); 
	    	
            return serial.getSerialNumber();  
	    }
	    
	    public static void main(String[] args) {
	    	System.err.println(getOrderNumber());
		}
}
