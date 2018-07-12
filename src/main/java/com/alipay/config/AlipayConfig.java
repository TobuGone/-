package com.alipay.config;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	
 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {
	
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner = "2088121875778938";
	
	// 收款支付宝账号
	public static String seller_email = "2129934269@qq.com";
	// 商户的私钥
	public static String key = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMRVMh32cK7AqWJehq8L/IsHhB5gpDA6WLsteW5G3rC3OTOhR9TD0/iM2e3cQ151j0abyTvW0XPYAM/WlLcGHT9km8DDGbDXrHTxxVAinGF88LcGllPUzNuBywlFPjVqvQb27EE9VERqv7kMc2/A88zS6s3q092furxLEBaJziuBAgMBAAECgYA7AqhnnRTNUZcXu/WbU2z6lK2/O6jd3guhZs+it6ZjUw7cV9NxPdLOV/eAqL2O/KKtxFE9rRmTSg8kaQiV4j6Tsk/rQPFc1sQ7qnMi7jtANwwXkxtk6IuL/3hl2eMBEKVJVKaDDomg+A7NsUhZ5OdhPpeBp+Uv6A3UTrDJnk9caQJBAOqD1/YgwFSkEOsUQzBE2VXpGsM7qc1hM1FiHiACIcBPT2U36pSTDrDloZSXQ5rxUKSurVWuJoZFz+Ma+50zWRMCQQDWUdoug6Nj9TPhEe7AaHJ4icXPO65Ri5GWohW3qkxwGMzHpBIubNg8LmN4F+n0jjFZPiAfXUosvyCXnQniiG+bAkEA3RMfAkqLpP7paAK4+AbfbyqJbSSQhSktbHn3dissUBM0AHI+9ILRJZfDw8T5GVaFtElq48uBS7ECMdCdA+uE+wJBALMSy1HILfJsQ//QAwSDgPoUa9J+1GVQT8JkBe9jfODA6AH7pjFiQr7uJ7CFkrcDRFqD3UU3pq2CuRHVZhgA1fcCQGm8jxAfFiT6ISn2flwbRaJAp3mGxhYbHvpXpfCIoTrzCfHbkDzYAaDejje/qQTZCrZAB/o4hSx1iN43BaR7uoQ=";

	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	

	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "D:\\";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";
	
	// 签名方式 不需修改
	public static String sign_type = "MD5";

}
