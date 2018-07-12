package pms;

import java.io.File;
import java.io.IOException;

/**
 * @author  huzi
 * @date    2018年6月27日	
 */
public class Test1 {
	public static void main(String[] args) throws IOException {
		File f = new File("C:\\Users\\Administrator\\Desktop\\保持不动等待1分钟.mp3");
		System.out.println(f.getName());
		System.out.println(new String(f.getName().getBytes("gbk")));
		System.out.println(new String(f.getName().getBytes("utf-8")));
		System.out.println(new String(f.getName().getBytes("iso-8859-1"),"utf-8"));
		System.out.println(new String(f.getName().getBytes("utf-8"),"iso-8859-1"));
	}
}
