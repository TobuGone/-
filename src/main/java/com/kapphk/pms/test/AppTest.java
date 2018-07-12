package com.kapphk.pms.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.junit.Test;

/**
 * 测试类
 * @author 胡子
 *
 */
//@Service
public class AppTest {
	
//	@Autowired
//	private InterfaceBeanAppUserService appUserService;

	
	
	/**
	 * 下载文件
	 * @throws Exception 
	 */
	@Test
	public void test() throws Exception{
		File file = new File("E:/工作/工作1/临时文件/爆炒猪肝2.pms");//源文件路径
		FileInputStream fi = new FileInputStream(file);//源文件路径  下面呢些代码还有用不 解析代码....其实我不知道他解析的目的是干嘛  担心改了后  会引发其它问题
		FileOutputStream fo = new FileOutputStream("E:/工作/工作1/临时文件/爆炒猪肝2——测试.pms");//外部路径 这个没写
		byte[] bytes = new byte[(int) file.length()];
		fi.read(bytes);
		bytes = new byte[]{};
		fo.write(bytes);//这样  就是把服务器源文件读了然后写出去了是的
		fi.close();
		fo.close();
	}
}
