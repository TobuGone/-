package com.kapphk.pms.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
  
/******************************************************************************* 
 * 缩略图类（通用） 本java类能将jpg、bmp、png、gif图片文件，进行等比或非等比的大小转换。 具体使用方法 
 * compressPic(大图片路径,生成小图片路径,大图片文件名,生成小图片文名,生成小图片宽度,生成小图片高度,是否等比缩放(默认为true)) 
 */  
@SuppressWarnings("restriction") //huzi
public class ZipUtils {
	/** 
	 *  缩略图实现，将图片(jpg、bmp、png、gif等等)真实的变成想要的大小 
	 */  
	public ZipUtils() { // 初始化变量  
		inputDir = "";   
		outputDir = "";   
		inputFileName = "";   
		outputFileName = "";   
		outputWidth = 100;   
		outputHeight = 100;   
	}   
     private File file = null; // 文件对象   
     private String inputDir; // 输入图路径  
     private String outputDir; // 输出图路径  
     private String inputFileName; // 输入图文件名  
     private String outputFileName; // 输出图文件名  
     private int outputWidth = 100; // 默认输出图片宽  
     private int outputHeight = 100; // 默认输出图片高  
     private boolean proportion = true; // 是否等比缩放标记(默认为等比缩放)  
     public void setInputDir(String inputDir) {   
         this.inputDir = inputDir;   
     }   
     public void setOutputDir(String outputDir) {   
         this.outputDir = outputDir;   
     }   
     public void setInputFileName(String inputFileName) {   
         this.inputFileName = inputFileName;  
     }   
     public void setOutputFileName(String outputFileName) {   
         this.outputFileName = outputFileName;   
     }   
     public void setOutputWidth(int outputWidth) {  
         this.outputWidth = outputWidth;   
     }   
     public void setOutputHeight(int outputHeight) {   
         this.outputHeight = outputHeight;   
     }   
     public void setWidthAndHeight(int width, int height) {   
         this.outputWidth = width;  
         this.outputHeight = height;   
     }   
       
     /*  
      * 获得图片大小  
      * 传入参数 String path ：图片路径  
      */   
     public long getPicSize(String path) {   
         file = new File(path);   
         return file.length();   
     }  
       
     // 图片处理   
     public String compressPic() {   
         try {   
             //获得源文件   
             file = new File(inputDir + inputFileName);   
             if (!file.exists()) {   
                 return "";   
             }   
             Image img = ImageIO.read(file);   
             // 判断图片格式是否正确   
             if (img.getWidth(null) == -1) {  
                 System.out.println(" can't read,retry!" + "<BR>");   
                 return "no";   
             } else {   
                 int newWidth; int newHeight;   
                 // 判断是否是等比缩放   
                 if (this.proportion == true) {   
                     // 为等比缩放计算输出的图片宽度及高度   
                     double rate1 = ((double) img.getWidth(null)) / (double) outputWidth + 0.1;   
                     double rate2 = ((double) img.getHeight(null)) / (double) outputHeight + 0.1;   
                     // 根据缩放比率大的进行缩放控制   
                     double rate = rate1 > rate2 ? rate1 : rate2;   
                     newWidth = (int) (((double) img.getWidth(null)) / rate);   
                     newHeight = (int) (((double) img.getHeight(null)) / rate);   
                 } else {   
                     newWidth = outputWidth; // 输出的图片宽度   
                     newHeight = outputHeight; // 输出的图片高度   
                 }   
                BufferedImage tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);   
                  
                /* 
                 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 
                 * 优先级比速度高 生成的图片质量比较好 但速度慢 
                 */   
                tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);  
                FileOutputStream out = new FileOutputStream(outputDir + outputFileName);  
                // JPEGImageEncoder可适用于其他图片类型的转换   
                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);   
                encoder.encode(tag);   
                out.close();   
             }   
         } catch (IOException ex) {   
             ex.printStackTrace();   
         }   
         return "ok";   
    }   
    public String compressPic (String inputDir, String outputDir, String inputFileName, String outputFileName) {   
        // 输入图路径   
        this.inputDir = inputDir;   
        // 输出图路径   
        this.outputDir = outputDir;   
        // 输入图文件名   
        this.inputFileName = inputFileName;   
        // 输出图文件名  
        this.outputFileName = outputFileName;   
        return compressPic();   
    }   
    
    /**
     * 
     * @param inputDir                输入图路径   
     * @param outputDir               输出图路径   
     * @param inputFileName           输入图文件名   
     * @param outputFileName          输出图文件名   
     * @param width                   设置图片长
     * @param height                  设置图片宽 
     * @param gp                      是否是等比缩放 标记 
     * @return
     */
    public String compressPic(String inputDir, String outputDir, String inputFileName, String outputFileName, int width, int height, boolean gp) {   
        // 输入图路径   
        this.inputDir = inputDir;   
        // 输出图路径   
        this.outputDir = outputDir;   
        // 输入图文件名   
        this.inputFileName = inputFileName;   
        // 输出图文件名   
        this.outputFileName = outputFileName;   
        // 设置图片长宽  
        setWidthAndHeight(width, height);   
        // 是否是等比缩放 标记   
        this.proportion = gp;
        return compressPic();
    }
    
    public static void main(String[] arg) {
    	ZipUtils mypic = new ZipUtils();
        System.out.println("输入的图片大小：" + mypic.getPicSize("e:\\1.jpg")/1024 + "KB");
        int count = 0; // 记录全部图片压缩所用时间  
        for (int i = 0; i < 100; i++) {   
            int start = (int) System.currentTimeMillis();   // 开始时间   
            mypic.compressPic("e:\\", "e:\\test\\", "1.jpg", "r1"+i+".jpg", 120, 120, true);   
            int end = (int) System.currentTimeMillis(); // 结束时间   
            int re = end-start; // 但图片生成处理时间   
            count += re; System.out.println("第" + (i+1) + "张图片压缩处理使用了: " + re + "毫秒");   
            System.out.println("输出的图片大小：" + mypic.getPicSize("e:\\test\\r1"+i+".jpg")/1024 + "KB");   
        }  
        System.out.println("总共用了：" + count + "毫秒");   
    } 
    

 
//"******************************************************************* 解压zip 2018-06-07 huzi **************************************************************************** 
    static Pattern pattern = Pattern.compile("[0-9A-Za-z_\\.\u4e00-\u9fa5]+$");
    /**  
     * 解压zip包  
     * @param zipFilePath zip文件的全路径  
     * @param unzipFilePath 解压后的文件保存的路径  
     * @param includeZipFileName 解压后的文件保存的路径是否包含压缩文件的文件名。true-包含；false-不包含  
     */    
	@SuppressWarnings({ "unchecked" })
	public static void unzip(String zipFilePath, String unzipFilePath, boolean includeZipFileName) throws Exception {
		if (StringUtils.isEmpty(zipFilePath) || StringUtils.isEmpty(unzipFilePath)) {
			throw new Exception("参数为空");
		}
		File zipFile = new File(zipFilePath);
		// 如果解压后的文件保存路径包含压缩文件的文件名，则追加该文件名到解压路径
		if (includeZipFileName) {
			String fileName = zipFile.getName();
			if (StringUtils.isNotEmpty(fileName)) {
				fileName = fileName.substring(0, fileName.lastIndexOf("."));
			}
			unzipFilePath = unzipFilePath + File.separator + fileName;
		}
		// 创建解压缩文件保存的路径
		File unzipFileDir = new File(unzipFilePath);
		if (!unzipFileDir.exists() || !unzipFileDir.isDirectory()) {
			unzipFileDir.mkdirs();
		}

		// 开始解压
		ZipEntry entry = null;
		String entryFilePath = null, entryDirPath = null;
		File entryFile = null, entryDir = null;
		int index = 0, count = 0, bufferSize = 1024;
		byte[] buffer = new byte[bufferSize];
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		
		ZipFile zip = new ZipFile(zipFile,Charset.forName("GBK"));
		Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) zip.entries();
		try {
			// 循环对压缩包里的每一个文件进行解压  遍历每一个文件  每次都要关闭写入流 和写出流  
			while (entries.hasMoreElements()) {
			
				entry = entries.nextElement();
				
				String s = entry.getName();
				if(pattern.matcher(new String(s.getBytes("gbk"))).matches()){
					 entryFilePath = unzipFilePath + File.separator + new String(s.getBytes("gbk"));
						System.out.println("解压文件：" +  entryFilePath + " 成功。");
				}else if(pattern.matcher(new String(s.getBytes("utf-8"))).matches()){
					entryFilePath = unzipFilePath + File.separator + s;
					System.out.println("解压文件：" +  entryFilePath + " 成功。");
				}
				//文件名都是中文组成的么 不是  数字英文下划线都有
				
				
//				System.out.println(new String(sByte_u8));
//				System.out.println(pattern.matcher(new String(sByte_u8)).matches());
//				System.out.println(new String(sByte_gbk));
//				System.out.println(pattern.matcher(new String(sByte_gbk)).matches());
				
				// 构建压缩包中一个文件解压后保存的文件全路径
//				if (System.getProperty("file.encoding").equals("UTF-8")) {
//					
//					byte[] sByte_u8 = s.getBytes("UTF-8");
//					entryFilePath = unzipFilePath + File.separator + new String(sByte_u8);
//					System.out.println("解压文件：" +  entryFilePath + " 成功。");
//				}
//				else if(System.getProperty("file.encoding").equals("GBK")){
//					
//					byte[] sByte_gbk = s.getBytes("GBK");
//					entryFilePath = unzipFilePath + File.separator + new String(sByte_gbk);
//					System.out.println("解压文件：" +  entryFilePath + " 成功。");
//				}
				
				
				// 构建解压后保存的文件夹路径
				index = entryFilePath.lastIndexOf(File.separator);
				if (index != -1) {
					entryDirPath = entryFilePath.substring(0, index);
				} else {
					entryDirPath = "";
				}
				
				
				entryDir = new File(entryDirPath);
				// 如果文件夹路径不存在，则创建文件夹
				if (!entryDir.exists() || !entryDir.isDirectory()) {
					entryDir.mkdirs();
				}

				// 创建解压文件
				entryFile = new File(entryFilePath);
				if (entryFile.exists()) {
					// 删除已存在的目标文件
					entryFile.delete();
				}

				// 写入文件
				bos = new BufferedOutputStream(new FileOutputStream(entryFile));
				bis = new BufferedInputStream(zip.getInputStream(entry));
				while ((count = bis.read(buffer, 0, bufferSize)) != -1) {
					bos.write(buffer, 0, count);
				}
				bos.flush();
				bos.close();
			}
		}catch (IOException e)  
        {  
            throw new RuntimeException(e);  
        }  
        finally  
        {  
        	zip.close();//如果不关闭zip这个文件变量 那就相当于 zipFile是被打开状态 所以不能被删除 
			zipFile.delete();//执行删除操作
        } 
	}
	
}
    
    
