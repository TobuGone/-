package com.kapphk.pms.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * byte数组与文件读写
 * 
 * 将一个文件内的所有内容读取到byte数组，也可以把一个byte[]的内容写入到文件中。
 * 
 * 2018-01-30
 * @author 胡子
 *
 */
public class ByteRWUitl {
	    
	/**
	 * 获取文件内容
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
    public static byte[] getContent(String filePath) throws IOException {  
        File file = new File(filePath);  
  
        long fileSize = file.length();  
        if (fileSize > Integer.MAX_VALUE) {  
            System.out.println("file too big...");  
            return null;  
        }  
  
        FileInputStream fi = new FileInputStream(file);  
        byte[] buffer = new byte[(int) fileSize];  
  
        int offset = 0;  
        int numRead = 0;  
  
        while (offset < buffer.length  
        && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {  
            offset += numRead;  
        }  
  
        // 确保所有数据均被读取  
        if (offset != buffer.length) {  
            throw new IOException("Could not completely read file " + file.getName());  
        }  
  
        fi.close();  
        return buffer;  
    }  
	    
	    
	    
	    
	    
	    
	    
	    
    }
