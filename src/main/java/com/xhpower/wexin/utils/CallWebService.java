package com.xhpower.wexin.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.alibaba.fastjson.JSONObject;
import com.xhpower.education.utils.PropertyUtil;

public class CallWebService {
	 public static String httpPost(String urlStr,String content){  
	        try {  
	            URL url = new URL(urlStr);  
	            URLConnection con = url.openConnection();  
	            con.setDoOutput(true);  
	            con.setRequestProperty("method", "POST");
	            con.setRequestProperty("Accept-Charset", "UTF-8");
	            con.setRequestProperty("Content-Type", "application/json");  
	            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());  
	            
	            out.write(content);  
	            out.flush();  
	            out.close();  
	            BufferedReader br = new BufferedReader(new InputStreamReader(con  
	                    .getInputStream()));  
	            String line = "";  
	            StringBuffer result=new StringBuffer();
	            for (line = br.readLine(); line != null; line = br.readLine()) {  
	                result.append(line);
	            }  
	            return result.toString();
	        }catch (MalformedURLException e) {  
	            e.printStackTrace();  
	        }catch (IOException e) {  
	            e.printStackTrace();  
	        }
	        return "";
	 }   
	 
	 public static String httpGet(String url2){
			try{
				URL url = new URL(url2);
				URLConnection urlConnection = url.openConnection();
				urlConnection.setDoOutput(true);
				urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
				urlConnection.setRequestProperty("method", "GET");
				urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
				OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream(),"UTF-8");				
				System.out.println(out.getEncoding());
				out.write("");
				out.flush();
				out.close();
				
				InputStream inputStream = urlConnection.getInputStream();
				String encoding = urlConnection.getContentEncoding();
				String body = IOUtils.toString(inputStream, encoding);
				
				return body;
			}catch(IOException e){
				e.printStackTrace();
				return  null;
			}
		}
	 
	 public static void main(String[] args) throws IOException {
		 Properties properties = PropertiesLoaderUtils.loadAllProperties("config.properties"); 
		 
		 //获取token
		 String token_json = CallWebService.httpGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+properties.getProperty("APP_ID")+"&secret="+properties.getProperty("APP_SECRET_ID"));
		 if(StringUtils.isNotEmpty(token_json)){
			 JSONObject jsonObject = JSONObject.parseObject(token_json);
			 
			 String token = "";
			 if(StringUtils.isNotEmpty(jsonObject.getString("access_token"))){
				 token = jsonObject.getString("access_token");
				 String delete_result = CallWebService.httpGet("https://api.weixin.qq.com/cgi-bin/menu/delete?access_token="+token);
				 if(StringUtils.isNotEmpty(delete_result)){
					 jsonObject = JSONObject.parseObject(delete_result);
					 if("ok".equals(jsonObject.getString("errmsg"))){
						 
						 CallWebService.httpPost("https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+token, "");
					 }
				 }
			 }
		 }
	 }
}
