package com.kapphk.pms.util;

/**
 * 配置的系统全局变量
 * @author Folo 2014年10月15日
 */
public class Contents {
	/****************************时间*************************/
	/** 默认时间格式化 "yyyy-MM-dd HH:mm:ss" */
	public static final String FORMIT = "yyyy-MM-dd HH:mm:ss";// 全局的时间格式
	/**  默认日期格式化 "yyyy-MM-dd" */
	public static final String FORMITD = "yyyy-MM-dd";// 全局的日期格式
	
	/** 
	* @Fields  自定义食材分类
	*/ 
	public static final String  CUSTOM_FOOD_STORE_CATEGORY = "自定义食材"; 
	
	
	/****************************图片*************************/
	/** 图片文件存储目录 */
	public static final String FILEPARH = "/usr/local/uploadFiles/qcy/";// 文件存储目录
	public static final String FILEPARH_CODE = "/usr/local/uploadFiles/qcy/code/";// 文件存储目录
	public static final String FILEPARH_IMG = "/usr/local/uploadFiles/qcy/image/";// 图片存储目录
	public static final int IMG_WIDTH = 200;// 小图默认宽
	public static final int IMG_HEIGHT = 200;// 小图默认高
	/****************************测试*************************/
//	public static final String TEST_MOBILE_PATH = "http://localhost/kapphk-qcy/m/";//测试手机端接口url
	public static final String TEST_WEB_PATH = "http://localhost/kapphk-qcy/";//测试手机端接口url
	
	public static final String TEST_MOBILE_PATH = "http://112.124.19.183/kapphk-qcy/m/";//测试手机端接口url
	// desc key
	public static final String DESCKEY = "2014Kapphk20140723222912QiHuiWangLuoDescKey0123456789";
	
/**************************************接口分页查询每页数量********************************************/
	public static Integer rows = 10;
	
/****************************************状态码*************************************/	
	//返回成功
	public final static String OK = "10001";
	//Exception
	public final static String ERROR = "10002";
	//无数据返回
	public final static String NOT_FOUND = "10003";
	//参数错误
	public final static String PARAMS_ERROR= "10004";
	//验证失败
	public final static String TOKEN_FAILURE = "10005";
	//值为空
	public final static String VALUE_EMPTY = "10006";
	//已存在
	public final static String EXIST = "10007";
	//不存在
	public final static String NOT_EXIST = "10008";
	public final static String STOP = "10009";
	//自定义
	public final static String CUS_ERROR = "10010";
	
}
