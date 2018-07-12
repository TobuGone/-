package com.kapphk.pms.util;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kapphk.pms.bean.BeanMenubookProcedure;
/**
 * 菜谱解析  工具类
 * 
 * 修改于 2018-02/26
 * @author 胡子
 * 
 */
public class PmsFile
{
	String TAG="PmsFile";
    public  final int offsetLuboStart = 0;     // 文件头 录波数据起始地址	4字节（低字节在前，高字节在后，下同）
    public  final int offsetLuboLenth = 4;     // 录波数据大小N			4字节
    public  final int offsetTimesStart = 8;    // 时间节点事件起始地址		4字节	
    public  final int offsetTimesLenth = 12;   // 时间节点事件数据大小		4字节
    public  final int offsetJPGStart = 16;     // 图片起始地址			4字节
    public  final int offsetJPGLenth = 20;     // 图片数据大小M			4字节
    public  final int offsetCaiStart = 24;     // 菜谱描述起始地址			4字节
    public  final int offsetCaiLenth = 28;     // 菜谱描述数据大小K		4字节
    public  final int offsetID = 32;           // 菜谱ID号				4字节
    public  final int offsetPMSID = 36;        // 炉子ID					4字节
    public  final int offsetVersionID = 40;    // 菜谱版本号   				4字节		huzi
    public  final int offsetYvStart = 44;      // 食材预处理起始地址    		4字节		huzi
    public  final int offsetYvLenth = 48;      // 食材预处理大小   			4字节		huzi
    public  final int offsetRes1 = 52;         // 保留					4字节
    public  final int offsetRes2 = 56;         // 保留					4字节
    
    //录波数据  N字节
    //时间节点 （180*n）字节
    //图片数据  M字节
    //菜谱描述  K字节  
    //食材预处理数据  （60*n）字节      huzi
    public byte[] PMS_Data;//原始解析数据
    private int LuboStart;
    private int LuboLenth;
    private int TimeStart;
    private int TimeLenth;
    private int JPGStart;
    private int JPGLenth;
    private int CaiStart;
    private int CaiLenth;
    private int	YvStart;
    private int YvLenth;
    
    
    public int ID;			//菜谱ID
    public int PMSID;		//炉子ID
    public int VersionID;	//菜谱版本号   huzi
    
    public int[] Res = new int[2];//保留
    
    public byte[] bufLubo;	//录波数组
    public byte[] bufTime;	//时间节点数组
    public byte[] bufJPG;	//图像数据
    public byte[] bufCai;	//菜谱描述数据
    public byte[] bufYv;	//食材预处理数据        huzi
    
    public String text;		//菜谱描述
    public List<TimeNode> listTimeNode;//时间节点容器
    
    /**
     * 声明食材预处理 成员
     * 2018-02-27  huzi
     */
    public int[] foodNum;		//食材序号
    public String[] foodName;	//食材名称
    public int[] foodUID;		//食材UID
    public int[] foodWgt;		//食材重量
    public String[] foodYvDescribe;//食材预处理描述
    
    
    private int luboLenth;      //存放录波数据长度
    public int getLuboLenth() {
    	luboLenth = ReadReg(offsetLuboLenth);
		return luboLenth;
	}
	
	

    public PmsFile(byte[] bufData)	
    {
        this.PMS_Data = bufData;
        JX();
    }

    public PmsFile()
    {
    	listTimeNode=new ArrayList<PmsFile.TimeNode>();
    }


    public void SetText(String text)
    {
        this.text = text;
        try {
			UpdateCaiBuf();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    }


    /**
     * 将Image写入到图像数组
     */
    public void UpdateJPGBuf(){
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bufJPG= baos.toByteArray();
    }

    /**
     * 将描述写入菜谱描述缓冲数组
     * @throws UnsupportedEncodingException
     */
    public void UpdateCaiBuf() throws UnsupportedEncodingException{
    	bufCai=text.getBytes("gb2312");
    }

    /**
     * 将时间节点写入到缓冲数组（用于文件下载）
     */
    public void UpdateTimesBuf(){
        //当时间节点发生改变时，修改文件有，修改存储数组
        TimeLenth = (int)(listTimeNode.size() * 180);//更新时间节点长度

        byte[] bufT = new byte[TimeLenth];
 
        int i=0;
        for (TimeNode timeNode : listTimeNode)
          {
        	 timeNode.UpdateBuf();//更新40字节数组
        	 for (int j = 0; j < 180; j++) {
        		 bufT[i*180+j]=timeNode.bufTime[j];
			}
        	 i++;
          }
        bufTime = bufT;//更新存储数组
    }

    
    /**
     * 将食材预处理写入缓冲数组    huzi
     */
    public void updateYvBuf(){
    	
    	int length = ByteUtils.byte2Int(PMS_Data,44+4,4); //食材预处理长度
    	YvLenth = (int)(length * 60);					  
    	byte[] bufYvs = new byte[YvLenth];
    	bufYv = bufYvs;
    }
    
    
    public void InserTimeNode(int index, TimeNode timeNode)
    {
        //在bufTime的指定位置插入一个时间节点
        listTimeNode.add(index, timeNode);
        UpdateTimesBuf();
    }


    public void InserTimeNode(TimeNode timeNode)
    {
        //在bufTime的指定位置插入一个时间节点
        //根据新节点的时间，自动判别插入位置
        int index = 0;
        while (index < listTimeNode.size() && timeNode.Times > listTimeNode.get(index).Times)
        {
            index++;
        }
        listTimeNode.add(index, timeNode);
        UpdateTimesBuf();
    }

    public void DeletTimeNode(int index)
    {
        listTimeNode.remove(index);
        UpdateTimesBuf();
    }


    public void setTipsTimeNode(int index, String tips,String[] foodNames,int[]  foodWgts,int time)
    {
		TimeNode timeNode=new TimeNode();
		timeNode.FoodNames=foodNames;
		timeNode.FoodWgts=foodWgts;
		timeNode.Times=time;
		timeNode.Tips=tips;
       listTimeNode.add(timeNode);
        UpdateTimesBuf();//更新整个时间数组
    }

    
    /**
     * int[]转byte[]
     * @param value
     * @return
     */
    private static byte[] ConvertToBytes(int value)
    {
    	
        //将无符号32位数据转换成4字节数组
    	byte[] abyte = new byte[4];  
        // "&" 与（AND），对两个整型操作数中对应位执行布尔代数，两个位都为1时输出1，否则0。  
        abyte[0] = (byte) (0xff & value);  
        // ">>"右移位，若为正数则高位补0，若为负数则高位补1  
        abyte[1] = (byte) ((0xff00 & value) >> 8);  
        abyte[2] = (byte) ((0xff0000 & value) >> 16);  
        abyte[3] = (byte) ((0xff000000 & value) >> 24);  
        return abyte;  
    }

    /**
     * 将4字节数组转换成32位无符号数据
     * byte[]转int[]
     * @param bufT
     * @param offset
     * @return
     */
    private static int ConvertToint(byte[] bufT,int offset)
    {
        int value=0;;
        value = (int) ((bufT[offset] & 0xFF)   
              | ((bufT[offset+1] & 0xFF)<<8)   
              | ((bufT[offset+2] & 0xFF)<<16)   
              | ((bufT[offset+3] & 0xFF)<<24));  
        return value;
    }
    
    /**
     * 将指定位置的2字节数组转换成int数据  （重量）
     * byte[]转int[]
     * @param bufT
     * @param offset  重量偏移：40 + 20 + 24 * i   序号偏移：	40 + 20 + 24 + 2 * i
     * @return
     */
    private static int ConvertToint2(byte[] bufT,int offset)
    {
        int value=0;;
        value = (int) ((bufT[offset] & 0xFF)   
              | ((bufT[offset+1] & 0xFF)<<8));  
        return value;
    }


    /**
     * 从指定位置截取int数据
     * @param offset
     * @return
     */
    int ReadReg(int offset)
    {
        int value;
        value = (int) ((PMS_Data[offset] & 0xFF)   
                | ((PMS_Data[offset+1] & 0xFF)<<8)   
                | ((PMS_Data[offset+2] & 0xFF)<<16)   
                | ((PMS_Data[offset+3] & 0xFF)<<24));  
        return value;
    }



    
    /**
     * 根据偏移量读取数据值
     */
    public void JX()
    {
        LuboStart = ReadReg(offsetLuboStart);
        LuboLenth = ReadReg(offsetLuboLenth);
        TimeStart = ReadReg(offsetTimesStart);
        TimeLenth = ReadReg(offsetTimesLenth);
        JPGStart = ReadReg(offsetJPGStart);
        JPGLenth = ReadReg(offsetJPGLenth);
        CaiStart = ReadReg(offsetCaiStart);
        CaiLenth = ReadReg(offsetCaiLenth);
        YvStart = ReadReg(offsetYvStart);	   //huzi
        YvLenth = ReadReg(offsetYvLenth);	   //huzi
        ID = ReadReg(offsetID);
        PMSID= ReadReg(offsetPMSID);
        VersionID = ReadReg(offsetVersionID);  //huzi
        Res[0] = ReadReg(offsetRes1);
        Res[1] = ReadReg(offsetRes2);
       

        try
        { 
            //分离录波数组
            bufLubo = new byte[LuboLenth];
            for (int i = 0; i < LuboLenth; i++)
            {
                bufLubo[i] = PMS_Data[i + LuboStart];
            }
            //分离时间节点数组
            bufTime = new byte[TimeLenth];
            for (int i = 0; i < TimeLenth; i++)
            {
                bufTime[i] = PMS_Data[i + TimeStart];
            }
            //将时间节点数据存储到listTimeNode
            listTimeNode = new ArrayList<TimeNode>();
            for (int i = 0; i < bufTime.length / 180; i++)
            {
                TimeNode tn = new TimeNode(bufTime, 180 * i);
                listTimeNode.add(tn);
            }

            if (JPGLenth > 0)
            {
                //分离图像数组
                bufJPG = new byte[JPGLenth];
                for (int i = 0; i < JPGLenth; i++)
                {
                    bufJPG[i] = PMS_Data[i + JPGStart];
                }
            }
            
            if (CaiLenth > 0)
            {
                //分离菜谱数组
                bufCai = new byte[CaiLenth];
                for (int i = 0; i < CaiLenth; i++)
                {
                    bufCai[i] = PMS_Data[i + CaiStart];
                }
                text=new String(bufCai, "gb2312");
            }
            
            if (YvLenth > 0)
            {
            	//分离预处理数组       huzi
            	bufYv = new byte[YvLenth];
            	for (int i = 0; i < YvLenth; i++) {
            		bufYv[i] = PMS_Data[i + YvStart];
				}
            	foodPreprocess();
            	System.out.println("成功了~");
            }
        }
        catch(Exception ex)
        {	
        	ex.printStackTrace();
            return;
        }
    }

    /**
     * 食材预处理解析
     * 2018-02-27 huzi
     * 
     */
    public void foodPreprocess(){
    	int count = YvLenth/60;  	//食材个数(一个预处理食材占60byte)
	
		foodNum = new int[count];
		foodName = new String[count];
		foodUID = new int[count];
		foodWgt = new int[count];
		foodYvDescribe = new String[count];
		
		int start = ByteUtils.byte2Int(PMS_Data,44,4); //44（偏移位）：食材预处理起始地址从第44位开始---文件头里查看（文件头用于定位要截取的目标数组的起始位置）
			
		
		for (int i = 0; i < count; i++) {
			foodNum[i] = ByteUtils.byte2Int(PMS_Data,start+(i*60),4); 		   //byte转int
			foodName[i] = ByteUtils.byte2String(PMS_Data,start+4+(i*60),12);   //byte转string
			foodUID[i] = ByteUtils.byte2Int(PMS_Data,start+4+12+(i*60),4);
			foodWgt[i] = ByteUtils.byte2Int(PMS_Data,start+4+12+4+(i*60),2);   //2018-03-26  重量所占长度从4byte改为2byte
			foodYvDescribe[i] = ByteUtils.byte2String(PMS_Data,start+4+12+4+4+(i*60),36);
			//System.out.println("食材序号="+foodNum[i]+",食材名称="+foodName[i]+",食材UID="+foodUID[i]+",食材重量="+foodWgt[i]+",预处理="+foodYvDescribe[i]);
		}
    }
    
    

    /**
     * 添加菜谱数据( 用于下载菜谱文件)
     */
    public void SavaPMSData()
    {

        if (bufTime != null)
            TimeLenth = (int)bufTime.length;
        if (bufLubo != null)
            LuboLenth= (int)bufLubo.length;
        if (bufJPG != null)
            JPGLenth = (int)bufJPG.length;
        if (bufCai != null)
            CaiLenth = (int)bufCai.length;
        if (bufYv != null)	//huzi
        	YvLenth = (int)bufYv.length;
        int fileLenth = (int)(60 + LuboLenth + TimeLenth + JPGLenth + CaiLenth + YvLenth);//数据总长度
        byte[] bufFile = new byte[fileLenth];//定义一个数组
        LuboStart = 60; 					 //录波数据起始位置（60为文件头长度）
        TimeStart = LuboStart + LuboLenth;   //时间节点事件起始位置
        JPGStart = TimeStart + TimeLenth;	 //图片数据起始位置
        CaiStart = JPGStart + JPGLenth;		 //菜谱描述起始位置
        YvStart = CaiStart + CaiLenth;		 //食材预处理起始位置
        
        System.arraycopy(ConvertToBytes(LuboStart), 0, bufFile, offsetLuboStart, 4);
        System.arraycopy(ConvertToBytes(LuboLenth), 0, bufFile, offsetLuboLenth, 4);
        System.arraycopy(ConvertToBytes(TimeStart), 0, bufFile, offsetTimesStart, 4);
        System.arraycopy(ConvertToBytes(TimeLenth), 0, bufFile, offsetTimesLenth, 4);
        System.arraycopy(ConvertToBytes(JPGStart), 0, bufFile, offsetJPGStart, 4);
        System.arraycopy(ConvertToBytes(JPGLenth), 0, bufFile, offsetJPGLenth, 4);
        System.arraycopy(ConvertToBytes(CaiStart), 0, bufFile, offsetCaiStart, 4);
        System.arraycopy(ConvertToBytes(CaiLenth), 0, bufFile, offsetCaiLenth, 4);
        System.arraycopy(ConvertToBytes(YvStart), 0, bufFile, offsetYvStart, 4);		//huzi
        System.arraycopy(ConvertToBytes(YvLenth), 0, bufFile, offsetYvLenth, 4);		//huzi
        System.arraycopy(ConvertToBytes(ID), 0, bufFile,offsetID, 4);
        System.arraycopy(ConvertToBytes(PMSID), 0, bufFile, offsetPMSID, 4);
        System.arraycopy(ConvertToBytes(VersionID), 0, bufFile, offsetVersionID, 4);    //huzi
        System.arraycopy(ConvertToBytes(Res[0]), 0, bufFile, offsetRes1, 4);
        System.arraycopy(ConvertToBytes(Res[1]), 0, bufFile, offsetRes2, 4);

        if (bufLubo!=null)
            System.arraycopy(bufLubo, 0, bufFile, LuboStart, LuboLenth);
        if (bufTime != null)
            System.arraycopy(bufTime, 0, bufFile,TimeStart, TimeLenth);
        if (bufJPG != null)
            System.arraycopy(bufJPG, 0, bufFile, JPGStart, JPGLenth);
        if (bufCai != null)
            System.arraycopy(bufCai, 0, bufFile, CaiStart, CaiLenth);
        if (bufYv != null)
            System.arraycopy(bufYv, 0, bufFile, YvStart, YvLenth);                     //huzi

        PMS_Data = bufFile; //原始解析数组	
    }
    

    /**
     *时间节点 
     *
     */
    public static class TimeNode
    {
    // 时间节点1	2字节
    // 总重量变化  	2字节（重量增加或减少，大于10000则是减小）
    //事件提示汉字1	36字节用于PMS液晶显示文字提示）
    //食材一 ID	4字节
    //食材一名称	16字节
    //食材一 重量	4字节
    //。。。。。。	。。。。。。
    //食材五 ID	4字节
    //食材五 名称	16字节
    //食材五 重量	4字节
    //保留字段1	4字节
    //保留字段2	4字节
    //保留字段3	4字节
    //保留字段4	4字节
    //保留字段5	4字节

        public  byte[] bufTime = new byte[180];

        public int Times = 0;//单位秒
        public int WetsTemp = 0;//重量变化
        public String Tips = "";//文字提示，最大36字节空间，18汉字

        public int[] FoodIDs = new int[5];//食材ID
        public String[] FoodNames = new String[5];//食材名称
        public int[] FoodWgts = new int[5];//食材重量

        public int[] Res = new int[5];	//？
        //使用数组构造时间节点,并且解析
        public TimeNode(byte[] bufTime, int index)
        {
            for (int i = 0; i < 180; i++)
            {	
                this.bufTime[i] = bufTime[i + index];
            }
            UpdateString();
        }
        //使用数组构造时间节点
        public TimeNode(byte[] bufTime)
        {
            this.bufTime = bufTime;
            UpdateString();
        }

        //使用参数构造时间节点
        public TimeNode()
        {
            ;
        }
        /**
         * 从菜谱数据解析参数
         */
        public void UpdateString()
        {
            Times=((bufTime[0] & 0xFF)   
                    | ((bufTime[1] & 0xFF)<<8));
            WetsTemp = ((bufTime[2] & 0xFF)   
                    | ((bufTime[3] & 0xFF)<<8));
            try
            {
                Tips=new String(bufTime, 4, 36, "gb2312");
//                Tips = Tips.Remove(Tips.indexOf("\0"));//删除多余的填充0
            	Tips=Tips.replace("\0", "");
            }
            catch(Exception exception)
            {
                exception.printStackTrace();
            }

            for (int i = 0; i < 5; i++)
            {
                FoodIDs[i] = ConvertToint(bufTime, 40 + 24 * i);

                if (FoodNames != null)
                {
                    try
                    { 
                    	 FoodNames[i] =new String(bufTime, 40 + 4 + 24 * i, 16, "gb2312");
                    	 FoodNames[i]	=FoodNames[i].replace("\0", "");
                    }
                    catch(Exception exception)
                    {
                    	 exception.printStackTrace();;
                    }
                }
                FoodWgts[i] =ConvertToint2(bufTime, 40 + 20 + 24 * i);
            }

            for (int i = 0; i < 5; i++)
            {
                Res[i]= ConvertToint(bufTime, 160 + 4 * i);
            }

        }

        //从参数组成菜谱数组
        public void UpdateBuf()
        {
//            Array.Clear(bufTime, 0, bufTime.length);//清空数组
            bufTime=new byte[bufTime.length];
            //写时间点
            bufTime[0] = (byte)(Times  & 0xFF);
//            bufTime[1] = (byte)((Times  & 0xFF)<<8);
            bufTime[1] = (byte) ((Times )>> 8);
            
            //写重量变化数据
            bufTime[2] = (byte)(WetsTemp & 0xFF);
            bufTime[3] = (byte)((WetsTemp & 0xFF)<<8);

            //写描述字符
            
            byte[] bufTips=null;
			try {
				bufTips = Tips.getBytes("gb2312");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
            for (int i = 0; i < (bufTips.length > 36 ? 36 : bufTips.length); i++)  //最大长度是36字节
            {
                bufTime[4 + i] = bufTips[i];
            }
            //写食材库
            for (int i = 0; i < 5; i++)
            {
                System.arraycopy(ConvertToBytes(FoodIDs[i]), 0, bufTime, 40 + 24 * i, 4);

                if (FoodNames[i] != null)
                {
                	 byte[] bufName=null;
					try {
						bufName = FoodNames[i].getBytes("gb2312");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
                    System.arraycopy(bufName, 0, bufTime, 40 + 4 + 24 * i, bufName.length > 16 ? 16 : bufName.length);
                }//FiXME

                System.arraycopy(ConvertToBytes(FoodWgts[i]), 0, bufTime, 40 + 20 + 24 * i, 2);	//huzi
            }
            //写保留数据
            for (int i = 0; i < 5; i++)
            {
                System.arraycopy(ConvertToBytes(Res[i]), 0, bufTime, 160 + 4 * i, 4);
            }

        }
    }

    
    /**
     * 测试
     * @author 胡子
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
    	
// 	    public int[] foodNum;		//食材序号
// 	    public String[] foodName;	//食材名称
// 	    public int[] foodUID;		//食材UID
// 	    public int[] foodWgt;		//食材重量
// 	    public String[] foodYvDescribe;//食材预处理描述
		
// 		File file = new File("http://localhost:8080/pms/upload/menupms/20160428/2016042815543750377208.pms");
    	File file = new File("E:/workspace/pms/src/main/webapp/upload/pmsTest/黑椒牛肉.pms");
 		PmsFile pmsFile;
 		if(file.exists()){
 			FileInputStream fin = new FileInputStream(file);
 	 		
 	 	    byte[] bytes = new byte[(int) file.length()];
 	 	   
 	 	    fin.read(bytes);
 	 	   
 	 	    fin.close();
 	 	    
 	 	    pmsFile = new PmsFile(bytes);
 		}else{
 			pmsFile=new PmsFile();
 		}
 		
 		
 	  // System.out.println("菜谱ID="+pmsFile.ID+",食材序号="+pmsFile.foodNum+",食材名称="+pmsFile.foodName+",食材UID="+pmsFile.foodUID+",食材重量="+pmsFile.foodWgt+",预处理描述="+pmsFile.foodYvDescribe);	
// 	   pmsFile.bufCai="测试效果".getBytes("gb2312");
//// 	   System.out.println("菜谱数据描述："+new String(pmsFile.bufCai));
// 	   System.out.println("菜谱数据描述："+new String(pmsFile.bufCai,"gb2312"));
// 	   pmsFile.bufCai ="测试菜谱描述明细".getBytes("gb2312");
//// 	   System.out.println("菜谱数据描述："+new String(pmsFile.bufCai,"gb2312"));
// 	   System.out.println("菜谱数据描述："+pmsFile.text);
// 	   pmsFile.SavaPMSData();
// 	 
// 	   FileOutputStream fo = new FileOutputStream("F:/11.jpg");
// 	   fo.write(pmsFile.bufJPG);
// 	   fo.close();
// 	   
// 	   File filejpg=new File("F:/222.jpg");
// 	   FileInputStream fijpg = new FileInputStream(filejpg);
// 	   byte[] bytejpg=new byte[(int) filejpg.length()];
// 	   fijpg.read(bytejpg);
// 	   fijpg.close();
// 	   
// 	   pmsFile.bufJPG=bytejpg;
// 	   pmsFile.SavaPMSData();
// 	   
// 	   FileOutputStream finew=new FileOutputStream("E:/workspace/pms/src/main/webapp/upload/pmsTest/黑椒牛肉.pms");
// 	   finew.write(pmsFile.PMS_Data);
// 	   finew.close();
// 	    
 	    
// 	   List<TimeNode> listTimeNode = pmsFile.listTimeNode;
 	   
// 	   
//		//菜谱步骤（原始代码）
//		Map<String, Integer> map = new HashMap<String, Integer>();
//		Map<String, Integer> mapId = new HashMap<String, Integer>();
//		for (TimeNode timeNode : pmsFile.listTimeNode) {
//			BeanMenubookProcedure item = new BeanMenubookProcedure();
//			item.setMinute(String.valueOf(timeNode.Times / 60));//分
//			item.setSecond(String.valueOf(timeNode.Times % 60));//秒
//			item.setDescribes(timeNode.Tips);//文字提示
//			item.setCreateTime(DateUtils
//					.getLocalDate("yyyy-MM-dd HH:mm:ss"));//创建日期
//			String foods = "";
//			
//			for (int i = 0; i < timeNode.FoodNames.length; i++) {
//				if (ValidateUtils.isBlank(timeNode.FoodNames[i])) {
//					continue;
//				} else {
//					mapId.put(timeNode.FoodNames[i],timeNode.FoodIDs[i]);
//					if (ValidateUtils.isBlank(map.get(timeNode.FoodNames[i]))) {
//						map.put(timeNode.FoodNames[i],timeNode.FoodWgts[i]);
//					} else {
//						map.put(timeNode.FoodNames[i],map.get(timeNode.FoodNames[i]) + timeNode.FoodWgts[i]);
//					}
//					String food = (String) (ValidateUtils.isBlank(timeNode.FoodIDs[i]) ? 0 : timeNode.FoodIDs[i] + "|"
//									+ timeNode.FoodNames[i] + "|"
//									+ timeNode.FoodWgts[i] + ";");
//					foods += food;
//				}
//			}
//			 System.out.println(foods);	
//		}
 	   
// 	   
 		//步骤
		Map<String, Object> map1 = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		Map<String, Integer> mapId = new HashMap<String, Integer>();
		for (TimeNode timeNode : pmsFile.listTimeNode) {
			BeanMenubookProcedure item = new BeanMenubookProcedure();
			item.setMinute(String.valueOf(timeNode.Times / 60));//分
			item.setSecond(String.valueOf(timeNode.Times % 60));//秒
			item.setDescribes(timeNode.Tips);//文字提示
			item.setCreateTime(DateUtils.getLocalDate("yyyy-MM-dd HH:mm:ss"));//创建日期
			String foods = "";
			
			//遍历步骤表食材名称
			for (int i = 0; i < timeNode.FoodNames.length; i++) {
	    		if (ValidateUtils.isBlank(timeNode.FoodNames[i])) {
					continue;
				} 
	    		else {
	    			mapId.put(timeNode.FoodNames[i],timeNode.FoodIDs[i]);
	    			
					for (int j = 0; j < pmsFile.foodName.length; j++) {
						if(timeNode.FoodNames[i].equals(pmsFile.foodName[j])){//如果预处理食材名称跟步骤表食材名称相同，则在步骤表食材数据后面拼接上相应的食材预处理描述
							if (ValidateUtils.isBlank(map1.get(timeNode.FoodNames[i]))) {//食材名不为空时
								map1.put(timeNode.FoodNames[i],timeNode.FoodWgts[i]);
								map2.put(timeNode.FoodNames[i],pmsFile.foodYvDescribe[j]);
							} else {
								map1.put(timeNode.FoodNames[i],map1.get(timeNode.FoodNames[i]) +""+timeNode.FoodWgts[i]);
								map2.put(timeNode.FoodNames[i],map2.get(timeNode.FoodNames[i]) +""+pmsFile.foodYvDescribe[j]);
							}
							
							String food = (String) (ValidateUtils.isBlank(timeNode.FoodIDs[i]) ? 0 : timeNode.FoodIDs[i] + "|"
									+ timeNode.FoodNames[i] + "|"
									+ timeNode.FoodWgts[i]  + "|"
									+ pmsFile.foodYvDescribe[j] + ";");
							foods += food;
							break;
						}
					}	
				}
	    	}
				 System.out.println(foods);	 
			}
		
		//将食材数据添加到菜谱表 
		String foods = "";
		for (String key1 : map1.keySet()) {//遍历的不是值   而是key  key1和key2相同   所以没区别
			String food1 = (String) (key1 + ":" + map1.get(key1) + "g:"+map2.get(key1)+";");//key1=食材名称   map1.get(key1)=食材重量  map2.get(key1)=食材预处理描述
			foods += food1;
		}

 	   System.out.println(foods);
// 	   
// 	   String foodsAll = "";
// 	   
// 	   for(String foodsName:set){
// 		   
// 		   int weight = 0;
// 		   
// 		   for(TimeNode node:listTimeNode){
// 	 		   
// 	 		   for(int i = 0;i<node.FoodNames.length;i++){
// 	 			   
// 	 			   if(node.FoodNames[i] == null || node.FoodNames[i].equals("")){
// 	 				   continue;
// 	 			   }
// 	 			  
// 	 			  if(node.FoodNames[i].equals(foodsName)){
// 	 				weight += node.FoodWgts[i];
// 	 			  }
// 	 		   }
// 	 		  
// 	 	   }
// 		  
// 		  foodsAll += foodsName+""+weight+"g;";
// 	   }
// 	   
// 	   System.out.println(foodsAll);
// 	   
// 	/*   InputStream in = new ByteArrayInputStream(pmsFile.bufJPG);
//       
//       String imgPath = "C:/Users/kapphk/Desktop/pms";
//       
//       String imgName = "11111111111111aa.jpg";
//
//       File file1 =new File(imgPath,imgName);//可以是任何图片格式.jpg,.png等
//       
//       FileOutputStream fos=new FileOutputStream(file1);
//          
//       byte[] b = new byte[1024];
//       
//       int nRead = 0;
//       
//       while ((nRead = in.read(b)) != -1) {
//           fos.write(b, 0, nRead);
//       }
//       
//       fos.flush();
//       
//       fos.close();
//       
//       in.close();*/
// 	    
// 	   
    }

 }


