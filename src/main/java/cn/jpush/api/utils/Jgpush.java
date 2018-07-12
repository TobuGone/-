package cn.jpush.api.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.BaseResult;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Message.Builder;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

public class Jgpush {
	    //用户端
		private static final String userAppKey ="ebadef203d25d60eeba0a710";
		private static final String userMasterSecret = "d8aca7efefde487992f17298";
		
	    private static boolean  apnsProduction = true;  //是否是生产环境

	    public static void main(String[] args) throws Exception {
	    	//单推
	    	//sendMessage_simple("推送测试7yyyyyyyyyyyyyyyy",Contents.USER_PARENT+"115",1);
	    	//组推
	       //	sendMessage_group("1111", new String[]{"1","49451"});
	    	//全推
//	    	sendMessage("123");
	    	Map<String,Object> map = new HashMap<String, Object>() ;
	    	map.put("id", 61) ;
	    	sendMessage2("自定义消息",map);
		}
	    /**
		 * 单推  (含自定义消息)
		 * msg         通知内容
		 * alias       接受消息人的别名
		 * toUserType  接受人类型  1=家长端 ，2=老师端
		 * @author  
		 */
		public static boolean sendMessage_simple(String msg,String alias) throws Exception{
			JPushClient jpushClient =  null;
			
			 jpushClient = new JPushClient(userMasterSecret, userAppKey,apnsProduction, 1);
			
			PushPayload payload = buildPushObject_all_alias_alert(msg,alias,null);//单推
			PushResult result = jpushClient.sendPush(payload);
			System.out.println("单推成功："+result);
			return true ;
		}
	    
		/**
		 * 单推  (含自定义消息)
		 * msg         通知内容
		 * alias       接受消息人的别名
		 * toUserType  接受人类型  1=家长端 ，2=老师端
		 * @author  
		 */
		public static boolean sendMessage_simple(String msg,String alias,Map<String,Object> extra) throws Exception{
			JPushClient jpushClient =  null;
			jpushClient = new JPushClient(userMasterSecret, userAppKey,apnsProduction, 1);
			PushPayload payload = buildPushObject_all_alias_alert(msg,alias,extra);//单推
			PushResult result = jpushClient.sendPush(payload);
			
			System.out.println("单推成功："+result);
			return true ;
		}
		
		/**   
		* @Title: delete_alias    
		* @Description: 根据别名注销极光用户  
		* @param @param alias
		* @param @param extra
		* @author xs  
		* @date 2015-12-16 下午6:02:37
		*/
		public static boolean delete_alias(String alias) throws Exception{
			JPushClient jpushClient =  null;
			jpushClient = new JPushClient(userMasterSecret, userAppKey,apnsProduction, 1);
			
			BaseResult result = 	 jpushClient.deleteAlias(alias,null);
            return  result.isResultOK();
		}
		
		public static boolean emptyBadge(String alias,Integer toUserType)  throws Exception{
			JPushClient jpushClient =  null;
			jpushClient = new JPushClient(userMasterSecret, userAppKey,apnsProduction, 1);
			PushPayload payload =	 PushPayload.newBuilder()
 			.setPlatform(Platform.all())                   //设备类型 andorid ios等
 			.setAudience(Audience.alias(alias))            // 手机端在极光注册的别名
 			.setNotification(   
 					        Notification.ios_set_badge(0)
 					     )
 			.build();
			BaseResult result =  jpushClient.sendPush(payload);
			  return  result.isResultOK();
		}
		 
		
		
		
		/**
		 * 群推
		 * @author zoneyu 15-5-20
		 */
		public static boolean sendMessage_group(String msg ,String[] alias)throws Exception{
			JPushClient jPushClient = null;
			jPushClient = new JPushClient(userMasterSecret, userAppKey,apnsProduction, 1);
			
			PushPayload payload = buildPushObject_android_and_ios_group(msg, alias);
			PushResult result = jPushClient.sendPush(payload);
			System.out.println("群推成功："+result);
			return true;
		}
		
		
	    
		/**
		 * 全推
		 * @author zoneyu 15-5-20
		 */
		public static boolean sendMessage(String msg) {
			JPushClient jPushClient = null;
			jPushClient = new JPushClient(userMasterSecret, userAppKey,apnsProduction, 1);
			PushPayload payload = buildPushObject_android_and_ios(msg);//全推
			try {
				PushResult result = jPushClient.sendPush(payload);
				System.out.println(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
            System.out.println("推送成功");
			return true ;
		}
		
		/**
		 * 全推,带自定义消息
		 * @author zoneyu 15-5-20
		 */
		public static boolean sendMessage2(String msg,Map<String,Object> map) {
			JPushClient jPushClient = null;
			jPushClient = new JPushClient(userMasterSecret, userAppKey,apnsProduction, 1);
			PushPayload payload = buildPushObject_android_and_ios(msg,map);//全推
			try {
				PushResult result = jPushClient.sendPush(payload);
				System.out.println(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("推送成功");
			return true ;
		}
		
//////////////////////////////////////////////////////////////////////////////////////////////////
		
		/**
		 * 单推（个人）
		 */
	    public static PushPayload buildPushObject_all_alias_alert(String msg,String alias,Map<String,Object> extra) {
	    	
	    	   if(extra != null && extra.size() > 0){
	    	    	return PushPayload.newBuilder()
	    	    			.setPlatform(Platform.all())                   //设备类型 andorid ios等
	    	    			.setAudience(Audience.alias(alias))            // 手机端在极光注册的别名
	    	    			.setNotification(                              //设置通知消息体(手机通知栏会显示消息)
	    		    					       Notification.newBuilder()
	    		    					                              .addPlatformNotification(
		    		    					        		                IosNotification.newBuilder()
		    		    					        		                .setAlert(msg)
								                                            .setSound("happy.caf")
                                                                            
								                                            .build())
							                                            .addPlatformNotification(
	    		    					        		    		       AndroidNotification.newBuilder()
	    		    					        		    		      .setAlert(msg)
	    		    					        		    		      .build()
	    		    					        		    		  ).build()
	    	    					          )
	    	    	        .setMessage(                                  //自定义消息(手机通知栏不会显示)
	    				    	        builderCustomMessage(extra) 		
	    			    	           ) 
	    	    			.build();
		    	}
	    	
	    		return PushPayload.newBuilder()
    	    			.setPlatform(Platform.all())                   //设备类型 andorid ios等
    	    			.setAudience(Audience.alias(alias))            // 手机端在极光注册的别名
    	    			.setNotification(                              //设置通知消息体(手机通知栏会显示消息)
                                //设置通知消息体(手机通知栏会显示消息)
  	    		    					       Notification.newBuilder()
  	    		    					                              .addPlatformNotification(
  		    		    					        		                IosNotification.newBuilder()
  		    		    					        		                .setAlert(msg)
  								                                            .setSound("happy.caf")
  								                                            
  								                                            .build())
  							                                            .addPlatformNotification(
  	    		    					        		    		       AndroidNotification.newBuilder()
  	    		    					        		    		      .setAlert(msg)
  	    		    					        		    		      .build()
  	    		    					        		    		  ).build()
    	    					          )
 
    	    			.build();
	
	        
	    }
	    
	    
	    /**
	     * 
	     *    组推
	     */
	    public static PushPayload  buildPushObject_android_and_ios_group(String msg,String[] alias){
	    	return PushPayload.newBuilder()
	    			.setPlatform(Platform.all()) //设备类型 andorid ios等
	    			.setAudience(Audience.alias(alias)) // 手机端在极光注册的别名
	    			.setNotification(
                                            //设置通知消息体(手机通知栏会显示消息)
	    		    					       Notification.newBuilder()
	    		    					                              .addPlatformNotification(
		    		    					        		                IosNotification.newBuilder()
		    		    					        		                .setAlert(msg)
								                                            .setSound("happy.caf")
								                                            
								                                            .build())
							                                            .addPlatformNotification(
	    		    					        		    		       AndroidNotification.newBuilder()
	    		    					        		    		      .setAlert(msg)
	    		    					        		    		      .build()
	    		    					        		    		  ).build()
	    					)
	    			 .build();
	    }
	    
	    /**
	     * all
	     */
	    public static PushPayload buildPushObject_android_and_ios(String msg) {
	        return PushPayload.newBuilder()
	                .setPlatform(Platform.android_ios())
	                .setAudience(Audience.all())
	                .setNotification( //设置通知消息体(手机通知栏会显示消息)
	    		    				Notification.newBuilder()
	    		    					                    .addPlatformNotification(
		    		    					        		                IosNotification.newBuilder()
		    		    					        		                .setAlert(msg)
								                                            .setSound("happy.caf")
								                                          
								                                            .build())
							                                            .addPlatformNotification(
	    		    					        		    		       AndroidNotification.newBuilder()
	    		    					        		    		      .setAlert(msg)
	    		    					        		    		      .build()
	    		    					        		     ).build()
                                )
	                .build();
	    }
	    
	    /**
	     * 全推，带自定义消息
	     */
	    public static PushPayload buildPushObject_android_and_ios(String msg,Map<String,Object> map) {
	    	return PushPayload.newBuilder()
	    			.setPlatform(Platform.android_ios())
	    			.setAudience(Audience.all())
	    			.setNotification( //设置通知消息体(手机通知栏会显示消息)
	    					Notification.newBuilder()
	    					.addPlatformNotification(
	    							IosNotification.newBuilder()
	    							.setAlert(msg)
	    							.setSound("happy.caf")
	    							
	    							.build())
	    							.addPlatformNotification(
	    									AndroidNotification.newBuilder()
	    									.setAlert(msg)
	    									.build()
	    									).build()
	    					)
	    					.setMessage(//自定义消息(手机通知栏不会显示)
	    							builderCustomMessage(map)
	    					)
	    					.build();
		}
		
		//构建自定义消息
		public static Message builderCustomMessage(Map<String,Object> extra){
			Builder builder = Message.newBuilder();
			for(Entry<String, Object> set:extra.entrySet()){
			   builder.addExtra(set.getKey(), set.getValue().toString());
			 }
			builder.setMsgContent("消息体");
			builder.build();
			return builder.build();
		}
}
