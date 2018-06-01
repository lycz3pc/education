package com.xhpower.education.utils;

import java.util.Date;
import java.util.Map;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.push.PushClient;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.schedule.ScheduleClient;
import cn.jpush.api.schedule.model.SchedulePayload;
import cn.jpush.api.schedule.model.TriggerPayload;



public class PushUtils {
	private static String Master_Secret="5404c2431261f9bc38819e4c";
	private static String AppKey="5e8cb0e496402d576f137765";
	private static final String REGISTRATION_ID1 = "0900e8d85ef";
	private static final String REGISTRATION_ID2 = "0a04ad7d8b4";
	
	public  static void push_alias(String[] alias,String context) throws APIConnectionException, APIRequestException{
		 PushClient jpush = new PushClient(Master_Secret,AppKey);
		 jpush.sendPush(buildPushObject_all_alias_alert(alias,context));
		 
	}
	  public  static PushPayload buildPushObject_all_alias_alert(String[] registrationIds,String context){
		return  PushPayload.newBuilder()
          .setPlatform(Platform.android())
          .setAudience(Audience.registrationId(registrationIds))
          .setNotification(Notification.alert(context))
          .build();
		  
	  }
	  
	 public static void pushByDate(String content,Date sendtime,Platform platform) throws APIConnectionException, APIRequestException{
		  ScheduleClient client = new  ScheduleClient(Master_Secret, AppKey);
			client.createSchedule(SchedulePayload.newBuilder().setPush(PushPayload.newBuilder().setPlatform(platform).setAudience(Audience.all())
				      .setNotification(Notification.alert(content)).build()).setEnabled(true).setTrigger(TriggerPayload.newBuilder().setSingleTime(DateHelper.format(sendtime, "yyyy-MM-dd hh:mm:ss")).buildSingle()).build());
	 }
	  
	 
	 public static void pushAll(String content) throws APIConnectionException, APIRequestException{
	    PushClient jpush = new PushClient(Master_Secret,AppKey);
		jpush.sendPush(PushPayload.newBuilder().setPlatform(Platform.android()).setAudience(Audience.all())
			      .setNotification(Notification.alert(content)).build());
	 }
	 
	 public  static void pushBytagsAndalias(String[] alias,String[] tags,String context,Map<String, String> map) throws APIConnectionException, APIRequestException{
		 PushClient jpush = new PushClient(Master_Secret,AppKey);
		 jpush.sendPush(PushPayload.newBuilder().setPlatform(Platform.android()).setAudience(Audience.newBuilder()
					.addAudienceTarget(AudienceTarget.alias(alias))//设置推送别名
					.addAudienceTarget(AudienceTarget.tag(tags))//设置推送目标
					.build())
				    .setNotification(Notification
			             .newBuilder()
			             .setAlert(context)
			              .addPlatformNotification(AndroidNotification.newBuilder().addExtras(map).build()).build()).build());				 
	 }
	 public  static void pushBytags(String[] tags,String context,Map<String, String> map) throws APIConnectionException, APIRequestException{
		 PushClient jpush = new PushClient(Master_Secret,AppKey);
		 jpush.sendPush(PushPayload.newBuilder().setPlatform(Platform.android()).setAudience(Audience.newBuilder()			
					.addAudienceTarget(AudienceTarget.tag(tags))//设置推送目标
					.build())
				    .setNotification(Notification
			             .newBuilder()
			             .setAlert(context)
			              .addPlatformNotification(AndroidNotification.newBuilder().addExtras(map).build()).build()).build());				 
	 }
	 
	 public  static void jGpushByRegistrationIds(String[] registrationIds,String context,Map<String, String> map) throws APIConnectionException, APIRequestException{
		 PushClient jpush = new PushClient(Master_Secret,AppKey);
		 jpush.sendPush(PushPayload.newBuilder().setPlatform(Platform.android()).setAudience(Audience.registrationId(registrationIds))
				   .setNotification(Notification.alert(context)).build());			 
	 }
}
