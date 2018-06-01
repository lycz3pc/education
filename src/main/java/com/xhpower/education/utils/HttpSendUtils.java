package com.xhpower.education.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class HttpSendUtils {

	private static final Logger log = LoggerFactory.getLogger("base");
	
	public static void main(String[] args) {
	}
	/**
	 * 换行
	 * 
	 */
	public static final void print() {
		System.out.println();
 	}
	/**
	 * 打印控制台
	 * 
	 * @param obj
	 */
 	public static final void print(Object obj) {
 		log.info(obj.toString());
 	}
 	/**
 	 * 转JSON打印并输出
 	 * 
 	 * @param str
 	 * @return
 	 */
 	public static final JSONObject json(String str) {
 		try {
 			JSONObject json = JSONObject.parseObject(str);
 			print("\n" + json.toJSONString());
 			return json;
 		} catch (Exception e) {
 			throw new RuntimeException(e);
 		}
 	}
 	
 	private static final String getUrl(Map<String, Object> params, String charset) throws UnsupportedEncodingException {
 		StringBuffer str = new StringBuffer();
 		Set<String> set = params.keySet();
 		boolean flag = false;
 		for (String key : set) {
 			if (flag)
 				str.append('&');
 			String value = params.get(key).toString();
 			str.append(key).append("=");
 			if (null == charset) 
 				str.append(value);
 			else
 				str.append(URLEncoder.encode(value, "utf-8"));
 			log.info("参数: " + key + "\t值: " + value);
 			flag = true;
		}
 		return str.toString();
 	}
 	
 	public static final String sendHTTPURL(String url) {
 		return sendHTTPURL(url, null);
 	}
 	
 	public static String sendHTTPURL(String url, String charset) {
 		return sendHTTP(url, "GET", charset, null, null);
 	}
 	public static String sendPOSTHTTPURL(String url, String charset) {
 		return sendHTTP(url, "POST", charset, null, null);
 	}
 	
 	public static final String sendHTTP(String url, 
 			String method, 
 			String charset,
 			Map<String, String> headers,
 			Map<String, Object> params,
 			Part... part) {
 		long start = System.currentTimeMillis();
 		try {
 			url = url.trim();
 			int w = url.indexOf("?");
 			String p = "";
 			if (url.indexOf("?") > -1) {
 				p = url.substring(w + 1);
 				url = url.substring(0, w);
 			}
 			log.info("链接: " + url);
 			
 			if (null == params)
 				params = new HashMap<String, Object>();
 			
 			String[] kvs = p.split("&");
 			for (String kv : kvs) {
 				int d = kv.indexOf("=");
 				if (d > 0) {
 					String k = kv.substring(0, d);
 					String v = kv.substring(d+1);
 					params.put(k, v);
 				}
			}
 			
 			String query = getUrl(params, charset);
 			log.info("URL参数: " + query);
 			log.info("URL: " + url + '?' + query);
 			
 			HttpClient client = new HttpClient();
 			HttpMethod method2;
 			if ("get".equals(method.toLowerCase())) {
 				log.info("模式: GET");
 				method2 = new GetMethod(url);
 			}
 			else {
 				log.info("模式: POST");
 				method2 = new PostMethod(url);
 			}
 			
 			if (null == charset)
 				charset = method2.getParams().getContentCharset();
 			log.info("字符集: " + charset);
 			method2.getParams().setContentCharset(charset);
 			
 			method2.setQueryString(query);
 			
 			if (null != headers) {
 				Set<String> kv2 = headers.keySet();
 				for (String key : kv2) {
 					String value = headers.get(key);
 					log.info("Header KEY: " + key + " VALUE: " + value);
 					method2.addRequestHeader(key, value);
				}
 			}
 			
 			if (null != part) {
 				PostMethod postMethod = (PostMethod)method2;
 				for (Part pa : part) {
 					log.info("ADD Part: " + pa.toString());
				}
 				
 				MultipartRequestEntity mrp= new MultipartRequestEntity(part, postMethod.getParams());
 				postMethod.setRequestEntity(mrp);
 			}
 			
 			log.info("发送请求...");
 			client.executeMethod(method2);
 			
 			log.info("状态: " + method2.getStatusCode());
 			log.info("状态消息: " + method2.getStatusText());
 			
 			Header[] headers2 = method2.getResponseHeaders();
 			for (Header header : headers2) {
 				log.info("Response Header KEY: " + header.getName() + " VALUE: " + header.getValue());
			}
 			String out = method2.getResponseBodyAsString();
 			
 			log.info("返回值长度:" + out.length());
 			log.info(out);
 			return out;
		} catch (Exception e) {
			log.error("", e);
			throw new RuntimeException(e);
		} finally {
			log.info("用时(ms): " + (System.currentTimeMillis() - start));
		}
 	}
}
