package com.kapphk.pms.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
  
/** 
 * Dom4j读写xml 
 * @author whwang 
 */  
public class XMLRegionUtils {  
	
	public static void main(String[] args) {
		//read();
		
		getXmlPath();
	}
    
    public static List<Map<String, Object>> read() {  
    	List<Map<String, Object>> provincelist = getProvince("provinces.xml");
    	
    	List<Map<String, Object>> citylist = getCity("cities.xml");
    	
    	List<Map<String, Object>> districtlist = getDistrict("districts.xml");
    	
    	
    	for(Map<String, Object> promap:provincelist){
    		
    		List<Map<String, Object>> clist = new ArrayList<Map<String,Object>>();
    		
    		for(Map<String, Object> citymap:citylist){
    			
    			List<Map<String, Object>> dlist = new ArrayList<Map<String,Object>>();
    			
    			if(promap.get("provinceId").equals(citymap.get("provinceId"))){
    				
    				Map<String, Object> cmap = new HashMap<String, Object>();
    				
    				cmap.put("cityId", citymap.get("cityId"));
    				cmap.put("cityName", citymap.get("cityName"));
    				
    				
    				for(Map<String, Object> districmap:districtlist){
    					
    					if(districmap.get("cityId").equals(citymap.get("cityId"))){
    						Map<String, Object> dmap = new HashMap<String, Object>();
    						
    						dmap.put("districtId", districmap.get("districtId"));
    						dmap.put("districtName", districmap.get("districtName"));
    						
    						dlist.add(dmap);
    					}
    				}
    				
    				cmap.put("districts", dlist);
    				
    				clist.add(cmap);
    			}
    			
    		}
    		promap.put("cities", clist);
    	}
   
    	
    	return provincelist;
    	
    }  
    
    
    
    public static List<Map<String, Object>> getProvince(String xmlName){
    	
    	List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
    	
    	 try {  
             SAXReader reader = new SAXReader();  
             
             File f = new File(XMLRegionUtils.class.getClassLoader().getResource("../../").getPath()+"WEB-INF/"+xmlName);
             
             InputStream in = new FileInputStream(f);
             
             Document doc = reader.read(in);  
             Element root = doc.getRootElement();
             List<Element> childElements = root.elements();
             
             for (Element child : childElements) {
            	 
            	 Map<String, Object> map = new HashMap<String, Object>();
             	 
             	 map.put("provinceId", child.attributeValue("ID"));
             	 map.put("provinceName", child.attributeValue("ProvinceName"));
             	 list.add(map);
             }
             
         } catch (Exception e) {  
             e.printStackTrace();  
         }
		return list;
    }
    

    public static List<Map<String, Object>> getCity(String xmlName){
    	
    	List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
    	
    	 try {  
             SAXReader reader = new SAXReader();  
             File f = new File(XMLRegionUtils.class.getClassLoader().getResource("../../").getPath()+"WEB-INF/"+xmlName);
             
             InputStream in = new FileInputStream(f);  
             
             Document doc = reader.read(in);  
             Element root = doc.getRootElement();
             List<Element> childElements = root.elements();
             
             for (Element child : childElements) {
            	 
            	 Map<String, Object> map = new HashMap<String, Object>();
             	 
             	 map.put("cityId", child.attributeValue("ID"));
             	 map.put("cityName", child.attributeValue("CityName"));
             	 map.put("provinceId", child.attributeValue("PID"));
             	 list.add(map);
             }
             
         } catch (Exception e) {  
             e.printStackTrace();  
         }
		return list;
    }

    public static List<Map<String, Object>> getDistrict(String xmlName){
	
	List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
	
	 try {  
         SAXReader reader = new SAXReader();  
         File f = new File(XMLRegionUtils.class.getClassLoader().getResource("../../").getPath()+"WEB-INF/"+xmlName);
         
         InputStream in = new FileInputStream(f);  

         Document doc = reader.read(in);  
         Element root = doc.getRootElement();
         List<Element> childElements = root.elements();
         
         for (Element child : childElements) {
        	 
        	 Map<String, Object> map = new HashMap<String, Object>();
         	 
         	 map.put("districtId", child.attributeValue("ID"));
         	 map.put("districtName", child.attributeValue("DistrictName"));
         	 map.put("cityId", child.attributeValue("CID"));
         	 list.add(map);
         }
         
     } catch (Exception e) {  
         e.printStackTrace();  
     }
	return list;
   }
    
    public static String getXmlPath()  
    {  
        String path=Thread.currentThread().getContextClassLoader().getResource("").toString();  
        path=path.replace('/', '\\'); // 将/换成\  
        path=path.replace("file:", ""); //去掉file:  
        path=path.replace("classes\\", ""); //去掉class\  
        path=path.substring(1); //去掉第一个\,如 \D:\JavaWeb...  
        path+="cities.xml";  
        System.out.println(path);  
        return path;  
    } 
    
}  

