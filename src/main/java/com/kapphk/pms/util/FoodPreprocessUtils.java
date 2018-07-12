package com.kapphk.pms.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.kapphk.pms.test.ByteRWUitl;

/**
 * 食材预处理  工具类
 * 
 * 用于测试
 * 
 * 2018-02-02
 * @author 胡子
 *
 */
public class FoodPreprocessUtils {
	
	   /**
	    * 用于将菜谱文件内容读取出并解析成相应的string或者int类型数据
	    * @param filePath	要解析的目标文件
	    */
	   public static void bytesContent(String filePath) {
			try {	
				byte[] srcBytes = ByteRWUitl.getContent(filePath);						//读取菜谱文件内容
				byte[] destBytes = ByteUtils.subBytes(srcBytes,0,srcBytes.length);		//在byte数组中截取指定长度数组
				
				int number =  ByteUtils.byte2Int(srcBytes,40,4);						//菜谱版本号
				int start = ByteUtils.byte2Int(srcBytes,44,4);							//44（偏移位）：食材预处理起始地址从第44位开始---文件头里查看（文件头用于定位要截取的目标数组的起始位置）
				int count = ByteUtils.byte2Int(srcBytes,44+4,4);
				int a = count/60;														//食材个数(一个预处理食材占60byte)
				
				System.out.println("菜谱版本号："+number);
				System.out.println("文件总长度="+srcBytes.length);
				System.out.println("预处理开始位置="+start);
				System.out.println("预处理长度="+count);		
				System.out.println("预处理食材个数="+a);	
				
				for (int i = 0; i < a; i++) {
					int num = ByteUtils.byte2Int(destBytes,start+(i*60),4); 			//byte转int
					String name = ByteUtils.byte2String(destBytes,start+4+(i*60),12); 	//byte转string
					int uid = ByteUtils.byte2Int(destBytes,start+4+12+(i*60),4);   			
					int weight = ByteUtils.byte2Int(destBytes,start+4+12+4+(i*60),2);   			
					String p = ByteUtils.byte2String(destBytes,start+4+12+4+4+(i*60),36);   
					System.out.println("第"+(i+1)+"个食材序号="+num+",名称="+name+",uid="+uid+",重量="+weight+",预处理="+p);
				}
	    
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
