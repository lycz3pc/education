package com.xhpower.wexin.utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 
* @ClassName: PublicMethod 
* @Description: 公共方法类
* @author lisf 
* @date 2017年6月15日 下午5:30:01 
*
 */
public class PublicMethod {
	
	//页码
	private static final int page = 1;
	//每页行数
	private static final int rows = 10;
	
	public static JSONObject initializationPageJson(JSONObject jsonObject){
		if(jsonObject.getInteger("page")==null){
			jsonObject.put("page", page);
		}
		if(jsonObject.getInteger("rows")==null){
			jsonObject.put("rows", rows);
		}
		return jsonObject;
	}
	
	/**
	 * 生成二维码图片
	 * @param format 文件后缀
	 * @param path	   文件路径
	 * @param link	   链接
	 * @param width  
	 * @param height 
	 * @param request
	 * @return
	 */
	public static String createQrCode(String format,String path,String link,int width,int height,HttpServletRequest request){
        //定义二维码的参数
        Map<Object, Object> map = new HashMap<Object, Object>();
        //设置编码
        map.put(EncodeHintType.CHARACTER_SET, "utf-8");
        //设置纠错等级
        map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        map.put(EncodeHintType.MARGIN, 2);

        try {
        	String realPath =  request.getSession().getServletContext()
                    .getRealPath("/") + path;
        	String fileName = "/"+System.currentTimeMillis()+"."+format;
        	
            //生成二维码
            BitMatrix bitMatrix = new MultiFormatWriter().encode(link, BarcodeFormat.QR_CODE, width, height);
            Path file = new java.io.File(realPath+fileName).toPath();
            MatrixToImageWriter.writeToPath(bitMatrix, format, file);
            
            return path+fileName;
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
	}
	
	public static String getProperties(String name,String key){
		try {
			Properties properties = PropertiesLoaderUtils.loadAllProperties(name); 
			return properties.getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static String getProperties(String key){
		try {
			Properties properties = PropertiesLoaderUtils.loadAllProperties("config.properties"); 
			return properties.getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
