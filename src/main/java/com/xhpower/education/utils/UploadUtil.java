package com.xhpower.education.utils;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.xhpower.education.common.response.UploadFileRspVO;


/**
 * 
* @ClassName: UploadUtil 
* @Description: TODO(文件上传的公共类) 
* @author lisf 
* @date 2016年10月12日 上午9:12:39 
*
 */
public class UploadUtil {
	
	/**
	 * 
	* @Title: uploadFile 
	* @Description: 批量上传文件 ,返回文件路径集合
	* @param @param filePath
	* @param @param files
	* @param @return    设定文件
	* @author root 
	* @return List<String>    返回类型 
	* @throws
	 */
	public static List<String> uploadFile(String filePath,HttpServletRequest request,MultipartFile files[]){
		List<String> pathList = new ArrayList<String>();
		if (files.length!=0) {
            try {
                 for (MultipartFile file : files) {
                	 //上传文件名称
                    /* String fileName=file.getOriginalFilename();
                     String type = FileHelper.getSuffix(fileName);

                     //生成新的文件名
                     fileName=System.currentTimeMillis()+"."+type;

                     //创建存储目录
                     File saveDir=new File(realPath,fileName.toString());
                    
                     if (!saveDir.getParentFile().exists()){
                     	saveDir.getParentFile().mkdirs();
                     } 
                     // 转存文件
                    file.transferTo(saveDir);
                    
                    String path = filePath+File.separator+fileName;
                    
                    pathList.add(path.replaceAll("\\\\", "/"));*/
                   // pathList.add(filePath+File.separator+fileName);
                	 
                	//上传至云服务
                 	UploadFileRspVO rspVO = uploadFileToCloud(file);
                 	pathList.add( rspVO.getQcloud_file_wwwurl());
				}
                 return pathList;

               
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
	}
	
	
	/**
	 * 
	* @Title: uploadFile 
	* @Description: 批量上传文件 ,返回文件路径集合
	* @param @param filePath
	* @param @param files
	* @param @return    设定文件
	* @author root 
	* @return List<String>    返回类型 
	* @throws
	 */
	/*public static List<String> uploadFile2(String filePath,HttpServletRequest request,MultipartFile files[]){
		List<String> pathList = new ArrayList<String>();
		if (files.length!=0) {
            try {
                 // 保存的文件路径
                 String realPath =  "E:/software/apache-tomcat-7.0.67/solar-admin/webapps/ROOT"  + filePath;
                 for (MultipartFile file : files) {
                	 //上传文件名称
                     String fileName=file.getOriginalFilename();
                     String type = FileHelper.getSuffix(fileName);

                     //生成新的文件名
                     fileName=System.currentTimeMillis()+"."+type;

                     //创建存储目录
                     File saveDir=new File(realPath,fileName.toString());
                    
                     if (!saveDir.getParentFile().exists()){
                     	saveDir.getParentFile().mkdirs();
                     } 
                     // 转存文件
                    file.transferTo(saveDir);
                    
                    String path = filePath+File.separator+fileName;
                    
                    pathList.add(path.replaceAll("\\\\", "/"));
                   // pathList.add(filePath+File.separator+fileName);
				}
                 return pathList;

               
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
	}*/
	
	/**
	 * 
	* @Title: uploadFile 
	* @Description: 上传单个文件 ,返回文件的路径
	* @param filePath   文件路径
	* @param file
	* @return    设定文件
	* @author lisf 
	* @return String    返回类型 
	* @throws
	 */
	public static String uploadFile(String filePath,HttpServletRequest request,MultipartFile file){
		if (!file.isEmpty()) {
            try {
                 // 保存的文件路径
                 /*String realPath =  request.getSession().getServletContext()
                         .getRealPath("/") + filePath;
                	 //上传文件名称
                     String fileName=file.getOriginalFilename();
                     String type = FileHelper.getSuffix(fileName);

                     //生成新的文件名
                     fileName=System.currentTimeMillis()+"."+type;

                     //创建存储目录
                     File saveDir=new File(realPath,fileName.toString());
                    
                     if (!saveDir.getParentFile().exists()){
                     	saveDir.getParentFile().mkdirs();
                     } 
                     // 转存文件
                    file.transferTo(saveDir);
                    
                    String path = filePath+File.separator+fileName;
                    
                    return path.replaceAll("\\\\", "/");*/
            	
            	//上传至云服务
            	UploadFileRspVO rspVO = uploadFileToCloud(file);
            	return rspVO.getQcloud_file_wwwurl();

               
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
	}
	/**
	 * 
	* @Title: saveFile 
	* @Description: TODO(文件的上传) 
	* @param @param url
	* @param @param request
	* @param @param file
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String saveFile(String url,HttpServletRequest request,MultipartFile file){
		if (!file.isEmpty()) {
            try {
            	url = url +File.separator+DateHelper.format(new Date(), "yyyyMMdd");
                // 保存的文件路径
                String filePath = request.getSession().getServletContext()
                    .getRealPath("/") + url;
                //上传文件名称
                String fileName=file.getOriginalFilename();

                //转义拆分重命名文件
                String[] strArr=fileName.split("\\.");

                //生成UID随机数
                UUID uuid = UUID.randomUUID();
                String string = uuid.toString();
                String strName=string.replace("-", "");

                //生成新的文件名
                fileName=strName+"."+strArr[strArr.length-1];

                //创建存储目录
                File saveDir1=new File(filePath,fileName.toString());
               
                if (!saveDir1.getParentFile().exists()){
                	saveDir1.getParentFile().mkdirs();
                } 
                // 转存文件
               file.transferTo(saveDir1);
                return fileName.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
	}
	/**
	 * 
	* @Title: deleteFile 
	* @Description: TODO(删除记录时删除相对应的文件，传入项目下的路径，请求Request,以及要删除的文件名称) 
	* @param @param fileName
	* @param @param request
	* @param @param url    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public static void deleteFile(String fileName,HttpServletRequest request,String url){
		//项目路径
		String filePath = request.getSession().getServletContext().getRealPath("/") + url;
        //上传文件名称
		File file=new File(filePath,fileName);
		
		if (!file.exists()){
        	System.out.println("文件不存在！不执行删除操作");
        }else{
        	//删除文件
        	file.delete();
        } 
	}
	/**
	 * 
	* @Title: updateFile 
	* @Description: TODO(修改文件，传入上传的文件，要修改的文件名称以及项目下的地址) 
	* @param @param fileName
	* @param @param request
	* @param @param url
	* @param @param file    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public static String updateFile(String oldfileName,HttpServletRequest request,String url,MultipartFile file){
		//上传文件
		String saveFile = UploadUtil.saveFile(url, request, file);
		//删除文件
		UploadUtil.deleteFile(oldfileName, request, url);
		return saveFile;
	}
	
	/**
	 * 
	* @Title: downloadMedia 
	* @Description: TODO(下载微信端的图片) 
	* @param @param mediaId
	* @param @param request
	* @param @param savePath
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	 public static String downloadMedia(HttpServletRequest request,InputStream inputStream,String url) {
		 String filePath = request.getSession().getServletContext()
                 .getRealPath("/") + url;

		//生成UID随机数
         UUID uuid = UUID.randomUUID();
         String string = uuid.toString();
         String strName=string.replace("-", "");
         String imgName=strName+".jpg";
         
         File saveDir1=new File(filePath,imgName);
         if (!saveDir1.getParentFile().exists()){
         	saveDir1.getParentFile().mkdirs();
         } 
         try {
         FileOutputStream fos = new FileOutputStream(saveDir1);
         byte[] buf = new byte[1024];  
         int size = 0;  
         while ((size = inputStream.read(buf)) != -1)
					
         fos.write(buf, 0, size);				
         fos.close();  
         inputStream.close();
         } catch (IOException e) {
				e.printStackTrace();
		}  
		 return imgName;
	 }
	 
	 /**
	  * 
	 * @Title: uploadFileToCloud 
	 * @Description: 上传文件
	 * @param file
	 * @return
	 * @throws IOException 
	 * @author xiong li
	  */
	 public static UploadFileRspVO uploadFileToCloud(MultipartFile file) throws IOException{
		 //上传的文件名
		 String fileName = file.getOriginalFilename();
		 String type = FileHelper.getSuffix(fileName);
         //生成新的文件名
         fileName=System.currentTimeMillis()+"."+type;
		 String url = PropertyUtil.getPropertiesValue("plat.properties", "PLAT_SERVER_PATH")+"/v1/common/common_filepre_upload?file_name="+fileName;
		 InputStream fileInpuStream = file.getInputStream();
		 return UploadUtil.uploadFileToCloud(url, fileInpuStream, null);
	 }
	 
	 
	 /**
	  * 
	 * @Title: uploadFileToCloud 
	 * @Description: 上传到云
	 * @param url
	 * @param fileInpuStream
	 * @param X_Uid
	 * @return
	 * @throws IOException 
	 * @author xiong li
	  */
	 public static UploadFileRspVO uploadFileToCloud(String url, InputStream fileInpuStream, String X_Uid) throws IOException {
			 
		UploadFileRspVO rspVO = null;
		URL Url = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) Url.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setUseCaches(false);
		conn.setRequestProperty("Connection", "Keep-Alive");
		conn.setRequestProperty("Charset", "UTF-8");
		conn.setRequestProperty("Content-Type", "application/octet-stream;");
		conn.setRequestProperty("X-Uid", X_Uid);
		conn.setInstanceFollowRedirects(true);
		conn.connect();
		DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
		int bufferSize = 1024;
		byte[] buffer = new byte[bufferSize];
		int length = -1;
		while ((length = fileInpuStream.read(buffer)) != -1) {
			dos.write(buffer, 0, length);
		}
		dos.flush();
		if (fileInpuStream != null)
			fileInpuStream.close();
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
		StringBuffer json = new StringBuffer();
		String line = null;
		while ((line = reader.readLine()) != null) {
			json.append(line);
		}
		System.out.println(json.toString());	
		rspVO = JSONObject.parseObject(json.toString(), UploadFileRspVO.class);
		//rspVO = JsonTransfer.toBean(json.toString(), UploadFileRspVO.class);
		return rspVO;
	}
}
