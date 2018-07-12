package com.kapphk.pms.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.kapphk.pms.service.interFace.InterfaceBeanMenuBookService;
import com.kapphk.pms.util.FoodPreprocessUtils;


@RestController
public class ByteTest {
	@Autowired
	private InterfaceBeanMenuBookService menuBookService;
	
//	@Test
//    public void test() throws Exception{
//    	
//    	/**
//    	 * 下载菜谱文件
//    	 * Lotus 2016-04-13
//    	 * @throws Exception 
//    	 */
//    	BeanMenuBook menuBook = new BeanMenuBook();
//    	menuBook.setFilePath("E:/workspace/pms/src/main/webapp/upload/pmsTest/黑椒牛肉.pms");
//    	Result rs = new Result();
//    	menuBookService.downloadMenuBookFile(menuBook,rs);
//    	
//    	
//    }
	
	
	public static void main(String[] args) {
		FoodPreprocessUtils.bytesContent("E:/workspace/pms/src/main/webapp/upload/pmsTest/番茄炒蛋.pms");
	}
	
    /**
     * 截取byte里面数据
     * @param src		原数组
     * @param srcPos	开始截取的起始位置
     * @param dest		目标数组
     * @param destPos	目标数组的开始起始位置
     * @param length	要copy的数组的长度
     */
	public static void arraycopy(Object src, int srcPos, Object dest, int destPos, int length){
		System.arraycopy(src, srcPos, dest, destPos, length);
	}
	
	
	
}