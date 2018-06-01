package com.xhpower.education.platform.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xhpower.education.platform.dao.TextbookEntityMapper;
import com.xhpower.education.platform.entity.TextbookEntity;
import com.xhpower.education.platform.service.TextbookService;
import com.xhpower.education.system.entity.Resources;
import com.xhpower.education.system.manager.ResourcesManager;
import com.xhpower.education.utils.R;
import com.xhpower.education.utils.UploadUtil;


@Service("textbookService")
@Transactional
public class TextbookServiceImpl extends ServiceImpl<TextbookEntityMapper, TextbookEntity> implements TextbookService {

    @Autowired
    private TextbookService textbookService;//教材
    @Autowired
    private ResourcesManager resourcesManager;//静态资源

    /**
     * @param page(页码) rows(返回条数) textbookEntity(教材参数)
     * @return map(数据集合)
     * @Title: index
     * @Description: 教材列表
     * @author lixiong
     */
    @Override
    public Object index(int page, int rows, TextbookEntity textbookEntity) {
        EntityWrapper<TextbookEntity> wrapper = new EntityWrapper<TextbookEntity>();//查询条件wrapper
        wrapper.setEntity(new TextbookEntity());
        wrapper.like("name",textbookEntity.getName());
        if(null != textbookEntity.getState()){
        	  wrapper.eq("state",textbookEntity.getState());
        }
        wrapper.orderBy("updateTime", false);
        if(null != textbookEntity.getIsTop()){  //置顶标识
        	//wrapper.eq("isTop", "1");
        	wrapper.orderBy("isTop", false);
			//wrapper.orderBy("updateTime", false);
        }
        
        Page<TextbookEntity> pages = new Page<TextbookEntity>(page, rows);//分页page
        pages = textbookService.selectPage(pages,wrapper);
        for(int i=0; i<pages.getRecords().size();i++){//寻找资源库对应封面图片 并且对对应教材设置封面
            EntityWrapper<Resources> wrapper1 = new EntityWrapper<Resources>();
            wrapper1.eq("id", pages.getRecords().get(i).getCoverimg());
            Resources resources = resourcesManager.selectOne(wrapper1);
            if(resources!=null){
                pages.getRecords().get(i).setImgpath(resources.getPath());//对象为空说明图片资源不存在
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", pages.getTotal());
        map.put("rows", pages.getRecords());
        return map;
    }

    /**
     * @param textbookEntity(教材参数) file（图片文件）
     * @return success/error
     * @Title: index
     * @Description: 教材新增
     * @author lixiong
     */
    @Override
    public R add(HttpServletRequest request, TextbookEntity textbookEntity, MultipartFile file) {
        String coverimg = "";//图片初始地址
        boolean b = false;
        if(file.isEmpty()){
            return R.error("封面未上传,请重新操作!");
        }
        coverimg = UploadUtil.uploadFile("/upload/textbook", request, file);
        if(StringUtils.isNotEmpty(coverimg)){
        	  Resources entity = new Resources();
              entity.setPath(coverimg);
              entity.setName(coverimg.substring(coverimg.lastIndexOf(".")+1));
              entity.setCreateTime(new Date());
              resourcesManager.insert(entity);
              textbookEntity.setCoverimg(entity.getId().toString());
              textbookEntity.setCreatetime(new Date());
              textbookEntity.setUpdatetime(new Date());
              textbookEntity.setState(1);
              b = textbookService.insert(textbookEntity);
        }
        if(b==true){
            return R.success();
        }else {
            return R.error("服务器异常");
        }
    }

    /**
     * @param textbookEntity(教材参数) file（图片文件）
     * @return success/error
     * @Title: index
     * @Description: 字典修改
     * @author lixiong
     */
    @Override
    public R update(HttpServletRequest request, TextbookEntity textbookEntity, MultipartFile file) {
    	/*if(textbookEntity.getFlag() == null){
    		textbookEntity.setFlagStr("");
    	}else{
        	textbookEntity.setFlagStr(StringUtils.join(textbookEntity.getFlag(), ","));
    	}*/
        String coverimg = "";
        if(!file.isEmpty()){//只要上传了图片就重置教材封面
            coverimg = UploadUtil.uploadFile("/upload/textbook", request, file);
            Resources entity = new Resources();
            entity.setPath(coverimg);
            entity.setName(coverimg.substring(coverimg.lastIndexOf(".")+1));
            entity.setCreateTime(new Date());
            resourcesManager.insert(entity);
            textbookEntity.setCoverimg(entity.getId().toString());
        }
        EntityWrapper<TextbookEntity> wrapper = new EntityWrapper<TextbookEntity>();
        wrapper.setEntity(new TextbookEntity());
        wrapper.eq("id",textbookEntity.getId());
        textbookEntity.setUpdatetime(new Date());
        boolean b = textbookService.update(textbookEntity,wrapper);
        if(b==true){
            return   R.success();
        }else {
            return R.error("服务器异常");
        }
    }
    /**
     * @param id(教材编号) state(教材状态)
     * @return success/error
     * @Title: index
     * @Description: 教材上/下架
     * @author lixiong
     */
    @Override
    public R delete(Integer id, Integer state) {
        TextbookEntity textbookEntity =new TextbookEntity();
        textbookEntity.setState(state);
        EntityWrapper<TextbookEntity> wrapper1 = new EntityWrapper<TextbookEntity>();
        wrapper1.setEntity(new TextbookEntity());
        wrapper1.eq("id",id);
        boolean b = textbookService.update(textbookEntity,wrapper1);
        if(b==true){
            return   R.success();
        }else {
            return R.error("服务器异常");
        }
    }

    /**
     * @param id(教材编号)
     * @return  textbookEntity（教材对象）
     * @Title: index
     * @Description: 教材详情
     * @author lixiong
     */
    @Override
    public R textbook(Integer id) {
        EntityWrapper<TextbookEntity> wrapper = new EntityWrapper<TextbookEntity>();
        wrapper.setEntity(new TextbookEntity());
        wrapper.eq("id",id);
        TextbookEntity textbookEntity = textbookService.selectOne(wrapper);//获取教材
        if(textbookEntity!=null){
            if(!StringUtils.isEmpty(textbookEntity.getCoverimg())){//获取教材封面
                EntityWrapper<Resources> wrapper1 = new EntityWrapper<Resources>();
                wrapper1.eq("id",textbookEntity.getCoverimg());
                Resources resources = resourcesManager.selectOne(wrapper1);
                if(resources!=null){
                    textbookEntity.setImgpath(resources.getPath());
                }
            }
            return R.success().put("textbookEntity",textbookEntity);
        }else {
            return R.error("教材异常");
        }

    }
}
