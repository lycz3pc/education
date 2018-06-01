package com.xhpower.education.utils;

import com.alibaba.fastjson.JSON;
import com.xhpower.education.common.request.SmsSendRequest;
import com.xhpower.education.common.response.SmsVariableResponse;

public class MsgSend {
	public static final String charset = "utf-8";
	public static final String url = PropertyUtil.getPropertiesValue("sms.url");
	// 用户平台API账号(非登录账号,示例:N1234567)
	public static String account = PropertyUtil.getPropertiesValue("sms.account");
	// 用户平台API密码(非登录密码)
	public static String pswd = PropertyUtil.getPropertiesValue("sms.pswd");

	/**
	 * 
	* @Title: sendMsg 
	* @Description: 发送短信
	* @param phoneOrParam 多个号码已逗号分隔 
	* @param msg 短信内容。长度不能超过536个字符
	* @param needstatus 是否需要状态报告（默认false）
	* @return 
	* @author xiong li
	 */
	public static SmsVariableResponse sendMsg(String phoneOrParam, String msg, String report) {
		
		SmsSendRequest smsSingleRequest = new SmsSendRequest(account, pswd, msg, phoneOrParam , report);
		String requestJson = JSON.toJSONString(smsSingleRequest);
		
		String response = ChuangLanSmsUtil.sendSmsByPost(url, requestJson);
		
		SmsVariableResponse smsSingleResponse = JSON.parseObject(response, SmsVariableResponse.class);
		
		return smsSingleResponse;
	}

}
