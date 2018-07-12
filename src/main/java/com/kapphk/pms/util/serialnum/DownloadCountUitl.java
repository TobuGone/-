package com.kapphk.pms.util.serialnum;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

/**
 * 用文件流实现下载次数
 * 
 * 实现： 开辟一个流文件，用来保存被下载的次数，然后读文件中value，点击一次value加1，再将此value保存到流文件中
 * 2018-01-30
 * @author 胡子
 * 
 * 【该工具类未使用】
 *
 */
public class DownloadCountUitl {
	
	 	private static int in ;
	    private static File file;

	    public static void main(String[] args) {

	    	//createNewFile("E:/test/abc.txt");
	    	///pms/src/main/webapp/upload/textfile/abc.text
	    	createNewFile("E:/workspace/pms/src/main/webapp/upload/textfile/abc.text");
	    }
	    
	    
	    /** 初始化文件中的值为0*/
	    public static void initNum(){
	        try {
	            OutputStream out = new FileOutputStream(file);
	            String str = "00";
	            out.write(str.getBytes());
	            out.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    /**创建一个新的文件*/
	    public static void createNewFile(String fileName){

	        file= new File(fileName);
	        if (!file.exists()) {
	            try {
	                file.createNewFile();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        
	        /** 先读出来*/
	        try {
	            if (file.exists()&&file.length()==0) {
	            	initNum();
	            }
	            Reader reader = new FileReader(file);
	            char[] c = new char[(int)file.length()];
	            int temp = 0;
	            int len =0;
	            while((temp=reader.read()) != -1){
	                c[len]=(char)temp;
	                len++;
	            }
	            reader.close();
	            System.out.println("初始值"+new String(c,0,len));
	            in =Integer.parseInt(new String(c,0,len));
	            in++;
	            System.out.println("下载一次："+in);
	            
	            /** 再写进去*/
	            Writer writer = new FileWriter(file);
	            writer.write(in+"");
	            writer.close();
	            System.out.println("再写进去："+in);
	            
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
    }
