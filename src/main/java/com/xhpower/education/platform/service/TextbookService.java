package com.xhpower.education.platform.service;

import com.baomidou.mybatisplus.service.IService;
import com.xhpower.education.platform.entity.TextbookEntity;
import com.xhpower.education.utils.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * 教材
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-08-24 14:02:20
 */
public interface TextbookService extends IService<TextbookEntity>{

    public Object index(int page , int rows,TextbookEntity textbookEntity);
    public R add(HttpServletRequest request, TextbookEntity textbookEntity , MultipartFile file);
    public R update(HttpServletRequest request, TextbookEntity textbookEntity , MultipartFile file);
    public R delete(Integer id,Integer state);
    public R textbook(Integer id);
}
