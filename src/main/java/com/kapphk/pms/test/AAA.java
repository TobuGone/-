package com.kapphk.pms.test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.junit.Test;
/**
 * 菜谱脏数据定时清理   测试类
 * @author 胡子
 * since 2018年5月18日
 */
public class AAA {
	
	public static void main(String[] args) {
//		CopyOnWriteArrayList<String> al = new CopyOnWriteArrayList<>();
//		//ArrayList<String> al = new ArrayList<>();
//		al.add("2");
//		al.add("3");
//		al.add("4");
//		al.add("5");
//		// java.util.ConcurrentModificationException这个就叫做并发修改异常
//		//第24行的 for 会进入到 list 内部的遍历器里边
//		for (String string : al) {
//			if("2".equals(string)){
//				al.remove(string);
//				//break;
//			}
//		}
//		
//			//正确的做法是
////		Iterator<String> iterator = al.iterator();
////		while(iterator.hasNext()){
////			String s = iterator.next();
////			if("2".equals(s)){
////				iterator.remove();
////			}
////		}
//		System.out.println(al.size());
//		System.out.println(al.toString());
		timer4();
	}

	/**
	 * #查询出来所有的文件名了 
	 * SELECT group_concat(substring_index(smb.file_path,'/',-1))  FROM sys_menu_book smb where smb.file_path like '%/20180326/%';
	 * 2018032618235377141951.pms,2018032618250045859802.pms,2018032618284719318118.pms,2018032620473980975745.pms,2018032623484416291337.pms,2018032623511990477803.pms
	 * @param args
	 */
//	@Test
//	public static void main(String[] args) {
	public static void abc() {
		
		//case 1 : 数据库有XX日文件 路径，那么服务器上必然有文件对应 : 检查文件是否存在，不存在就抛出异常（算是监控功能）。
		//case 2 : 数据库没有XX日文件 路径 服务器上有XX日的文件 所以必然是脏数据 ： 直接把该文件夹 及 子文件全部删除，不做任何比对。 
		//case 3 : 数据库没有XX日文件 路径 服务器上也没有XX日的文件 继续下一次循环  ： continue；
		Calendar instance = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		int dayCount = 1000;//今天以前向前减1000天
		File dirFile = null;
		String currentPath;
		// = "D:\\Tomcat\\apache-tomcat-7.0.82-windows-x64\\apache-tomcat-7.0.82\\webapps\\pms\\upload\\menupms\\20180424";
//		String filesNameStr = "";
		for(int i = 0; i < dayCount; i++){
			//正常情况是查询出来的  我晓得
			instance.add(Calendar.DATE,-1);
			//currentPath =  sdf.format(instance.getTime());
			currentPath = "D:\\Tomcat\\apache-tomcat-7.0.82-windows-x64\\apache-tomcat-7.0.82\\webapps\\pms\\upload\\menupms\\" + sdf.format(instance.getTime());
		//	System.out.println(currentPath);
			//根据当前路径 或者说 这个日期 去做查询 ,2018051616253816170738.pms,2018051616255252818616.pms,2018051616260321659743.pms
//			filesNameStr = "2018032618235377141951.pms,2018032618250045859802.pms,2018032618284719318118.pms,2018032620473980975745.pms,2018032623484416291337.pms,2018032623511990477803.pms";
			List<Object> list = new ArrayList<Object>();
			list.add("2018042417005881589579.pms");
			list.add("2018042417012681690401.pms");
//			list.add("2018032618284719318118.pms");
//			list.add("2018032620473980975745.pms");
//			list.add("2018032623484416291337.pms");
//			list.add("2018032623511990477803.pms");
			if(null == list || list.isEmpty()){
				continue;
			}
			//System.out.println(sdf.format(instance.getTime()));
			dirFile = new File(currentPath);
			//当前文件存在 并且是目录
			if(dirFile.exists() && dirFile.isDirectory()){
				File[] listFiles = dirFile.listFiles();
				//这里会有经典的并发删除异常：即在你循环的适合，你删除了集合里边的元素导致的 所以要用迭代器 ，当前的数组对象是Array 所以应该没问题，如果是List 一定不能用循环删除
//				try {
//					for (File file : listFiles) {
//						if (list.indexOf(file.getName()) == -1){
//						    file.delete();
//						    System.out.println("删除成功~");
//						  }
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
				//数组  就不存在 remove 方法  所以不会出现并发异常操作
				for (int j=0; j<listFiles.length;j++){
					  File file = listFiles[j];
					  if (list.indexOf(file.getName()) == -1){
					    file.delete();
					    System.out.println("删除成功~");
					  }
				}
			}
		}
	}
	
	
	// 安排指定的任务task在指定的时间firstTime开始进行重复的固定速率period执行．  
	@Test
    public static void timer4() {  
        Calendar calendar = Calendar.getInstance();  
        calendar.set(Calendar.HOUR_OF_DAY, 18); // 控制时  
        calendar.set(Calendar.MINUTE, 4);       // 控制分  
        calendar.set(Calendar.SECOND, 0);       // 控制秒  
  
        Date time = calendar.getTime();         // 得出执行任务的时间,此处为今天的18：00：00  
  
        Timer timer = new Timer();  
        timer.scheduleAtFixedRate(new TimerTask() {  
            public void run() {  
                System.out.println("-------设定要指定任务--------");
                abc();
            }  
        }, time, 1000 * 60);// 这里设定将延时每天固定执行  
    }  

}
