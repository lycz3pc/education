package com.xhpower.education.api.core;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xhpower.plugins.api.validate.core.VerifyException;
import com.xhpower.plugins.common.security.Cryptogram;
import com.xhpower.plugins.common.security.MD5;
import com.xhpower.plugins.common.utils.StringHelper;


/** 
 * @ClassName: ApiBaseController 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author lisf 
 * @date 2016年9月10日 下午3:17:17 
 *  
 */
@Controller
public class ApiBaseController {
	private static final Logger log = LoggerFactory.getLogger(ApiBaseController.class);
	
	public static final String API_KEY_CLIENT = "96A659D3035B51B1B66DF3139F1AEC33F6651334F1E65177";
	
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
    public Object exp(Exception e,HttpServletRequest request,HttpServletResponse  response) {  
        Class<?> c = e.getClass();
    	log.info("API error", e);
		if (c.equals(APIException.class)) {
			APIException api = (APIException)e;
			return error(api.getCode(), e.getMessage());
		}
		else if (c.equals(VerifyException.class)) 
			return error(APICode.ERROR_SYS_1002, e.getMessage());
		else if (c.equals(UnsupportedEncodingException.class))
			return error(APICode.ERROR_SYS_1002);
		else if (c.equals(JSONException.class))
			return error(APICode.ERROR_SYS_1002);
		else if (c.equals(IOException.class))
			 return error(APICode.ERROR_SYS_1006);
		else
			return error(0);
	
		
		
    }  
	
	
	/**
	 * 返回对象成json数据
	 * 
	 * @param object
	 */
	protected String returnJson(Object object,HttpServletResponse response) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(response.getWriter(), object);
		} catch (Exception e) {
			log.debug("", e);
		}
		return null;
	}
	
	/**
	 * 验证
	 * 
	 * @return
	 * @throws APIException 
	 */
	protected boolean validation(HttpServletRequest request) throws APIException {
		Enumeration en = request.getParameterNames();
		Map<String, Object>  params = new HashMap<String, Object>();
		while (en.hasMoreElements()) {
			String paramName = (String) en.nextElement();
			System.out.println(paramName + " = " + request.getParameter(paramName));
			log.info(paramName + " = " + request.getParameter(paramName));
			params.put(paramName, request.getParameter(paramName));
		}
		String sign = String.valueOf(params.get("sign"));
		if (StringHelper.isStringEmpty(sign))
			throw new APIException(1004);
		if (!sign.equalsIgnoreCase(MD5.encrypt(String.valueOf(params.get("version")) + String.valueOf(params.get("ot")) + String.valueOf(params.get("data")) + API_KEY_CLIENT))) 
			throw new APIException(1004);
//		if (System.currentTimeMillis() - ot > 3 * 60 * 1000) 
//			throw new APIException(1005);
		return true;
	}
	/**
	 * json字符串转成需要的对象
	 * 
	 * @param <T>
	 * @param class1
	 * @return
	 * @throws APIException
	 */
	protected <T> T changeData(Class<T> class1,HttpServletRequest request) throws APIException {
		try {
			String info = decryptData(request);
			log.info("************info************:"+info);
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			//设置时间格式
			mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));           
			
			return mapper.readValue(info, class1);
		} catch (Exception e) {
			log.debug("changeData", e);
			throw new APIException(1002, e.getMessage(), e).setLevel(4);
		}
	}
	
	/**
	 * 解密Data数据
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 */
	protected String decryptData(HttpServletRequest request) throws UnsupportedEncodingException, Exception {
		return URLDecoder.decode(Cryptogram.decryptByKey(request.getParameter("data"), API_KEY_CLIENT), "utf-8");
	}
	
	
	
	
	/**
	 * 异常
	 * @param e
	 * @return
	 */
	protected APIBaseResponse error(Exception e) {
		Class<?> c = e.getClass();
		if (c.equals(APIException.class)) {
			APIException api = (APIException)e;
			error(api.getCode(), e.getMessage());
		}
		else if (c.equals(VerifyException.class)) 
			return error(APICode.ERROR_SYS_1002, e.getMessage());
		else if (c.equals(UnsupportedEncodingException.class))
			return error(APICode.ERROR_SYS_1002);
		else if (c.equals(JSONException.class))
			return error(APICode.ERROR_SYS_1002);
		else if (c.equals(IOException.class))
			return error(APICode.ERROR_SYS_1006);
		else
			return error(0);
		log.info("API error", e);
		return null;
	}
	/**
	 * 异常
	 * @return
	 */
	protected APIBaseResponse error() {
		return new APIBaseResponse(APICode.ERROR, APICode.ERROR);
	}
	/**
	 * 异常
	 * @param code
	 * @return
	 */
	protected APIBaseResponse error(int code) {
		return new APIBaseResponse(APICode.ERROR, code);
	}
	
	/**
	 * 异常
	 * @param code
	 * @param message
	 * @return
	 */
	protected APIBaseResponse error(int code, String message) {
		return new APIBaseResponse(APICode.ERROR, code, message);
	}
	/**
	 * 成功
	 * @return
	 */
	protected APIBaseResponse success() {
		return new APIBaseResponse(APICode.SUCCESS, APICode.SUCCESS);
	}
	/**
	 * 添加用户消息
	 * 
	 * @return
	 */
	protected APIBaseResponse success(String message) {
		APIBaseResponse response = new APIBaseResponse(APICode.SUCCESS, APICode.SUCCESS, message);
		return response;
	}
	
	public static void main(String[] args) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phone", "13112344321");
		map.put("password", "13112344321");
		String s = Cryptogram.encryptByKey(URLEncoder.encode(JSON.toJSONString(map), "UTF-8"),API_KEY_CLIENT);
		System.out.println(s);
	}
	
/*	public  <T> T formatJson(Class<T> class1,Class<T> class2) throws UnsupportedEncodingException, Exception{
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		//设置时间格式
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));           		
		//return mapper.readValue(class1, class1);
	}
	*/
}
