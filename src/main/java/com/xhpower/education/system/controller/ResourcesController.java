package com.xhpower.education.system.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhpower.education.system.entity.Resources;
import com.xhpower.education.system.manager.ResourcesManager;
import com.xhpower.education.utils.R;

/**
 * 
 * @ClassName: ResourcesController
 * @Description: 上传资源控制器
 * @author Lian Youjie
 * @date 2017年8月31日 下午11:19:11
 *
 */
@Controller
@RequestMapping("/resources")
public class ResourcesController {

    @Autowired
    private ResourcesManager resourcesManager;

    /**
     * 
     * @Title: selectById
     * @Description: 根据id查找文件信息
     * @param resources
     * @author Lian Youjie
     * @return R 返回类型
     */
    @ResponseBody
    @RequestMapping("/selectById")
    public R selectById(@RequestBody Resources resources) {
        return R.success().put("resource", resourcesManager.selectById(resources.getId()));
    }

    /**
     * 
     * @Title: download
     * @Description: 文件下载，根据id下载文件
     * @param request
     * @param id
     * @author Lian Youjie
     * @return ResponseEntity<byte[]> 返回类型
     */
    @RequestMapping(value = "/{id}", produces = { "application/msword", "application/x-ppt", "application/x-xls" })
    public ResponseEntity<byte[]> download(HttpServletRequest request, @PathVariable Integer id) {
        Resources resources = resourcesManager.selectById(id);
        HttpHeaders headers = new HttpHeaders();
        try {
            // File file = new File(serverPath + resources.getPath());
            String fileName = new String(resources.getName().getBytes("UTF-8"), "ISO-8859-1");
            headers.setContentDispositionFormData("attachment", fileName);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            URL url = new URL(resources.getPath());    
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();    
            // 设置超时间为3秒  
            conn.setConnectTimeout(3*1000);  
            // 防止屏蔽程序抓取而返回403错误  
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
            // 得到输入流  
            InputStream inputStream = conn.getInputStream();
            byte[] buffer = new byte[1024];    
            int len = 0;    
            ByteArrayOutputStream bos = new ByteArrayOutputStream();    
            while((len = inputStream.read(buffer)) != -1) {    
                bos.write(buffer, 0, len);    
            }    
            bos.close();
            return new ResponseEntity<byte[]>(bos.toByteArray(), headers, HttpStatus.OK);
        } catch (NullPointerException e) {
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            return new ResponseEntity<byte[]>(headers, HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            return new ResponseEntity<byte[]>(headers, HttpStatus.GONE);
        }
    }

}
