package com.xhpower.education.platform.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xhpower.education.annotation.SysLog;
import com.xhpower.education.common.BaseController;
import com.xhpower.education.platform.entity.TextbookEntity;
import com.xhpower.education.platform.service.TextbookService;
import com.xhpower.education.system.entity.Resources;
import com.xhpower.education.system.manager.ResourcesManager;
import com.xhpower.education.utils.R;

@RestController
@RequestMapping(value = "/platform/textbook")
public class TextbookController extends BaseController {

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
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/index", method = RequestMethod.POST)
    public Object index(int page , int rows,TextbookEntity textbookEntity) {
        Map<String, Object>  map = (Map<String, Object>) textbookService.index(page,rows,textbookEntity);
        return map;
    }

    /**
     * @param textbookEntity(教材参数) file（图片文件）
     * @return success/error
     * @Title: index
     * @Description: 教材新增
     * @author lixiong
     */
    @RequestMapping(value = "/add")
    @SysLog("教材新增")
    public R add(HttpServletRequest request, TextbookEntity textbookEntity ,MultipartFile file) {
     //  textbookEntity.setFlagStr(StringUtils.join(textbookEntity.getFlag(), ","));
       textbookEntity.setBrowse(0);
       return textbookService.add(request,textbookEntity,file);
    }

    /**
     * @param textbookEntity(教材参数) file（图片文件）
     * @return success/error
     * @Title: index
     * @Description: 教材修改
     * @author lixiong
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @SysLog("教材修改")
    public R update(HttpServletRequest request, TextbookEntity textbookEntity , MultipartFile file) {
      return textbookService.update(request,textbookEntity,file);
    }

    /**
     * @param
     * @return success/error
     * @Title: index
     * @Description: 重置排序
     * @author lixiong
     */
    @RequestMapping(value = "/replace", method = RequestMethod.POST)
    @SysLog("重置排序")
    public R replace(Integer id,Integer state) {
        EntityWrapper<TextbookEntity> wrapper = new EntityWrapper<TextbookEntity>();
        wrapper.setEntity(new TextbookEntity());
        TextbookEntity textbookEntity = new TextbookEntity();
        /*if(id==0){//重置排序
            textbookEntity.setSequence(0);
        }*/
        if(id!=0){//置顶
            wrapper.eq("id",id);
            textbookEntity.setIsTop(state);
            textbookEntity.setUpdatetime(new Date());
        }
        textbookService.update(textbookEntity,wrapper);
        return R.success();
    }

    /**
     * @param id(教材编号) state(教材状态)
     * @return success/error
     * @Title: index
     * @Description: 教材上/下架
     * @author lixiong
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @SysLog(" 教材上/下架")
    public R delete(Integer id,Integer state) {
        return textbookService.delete(id,state);
    }


    /**
     * @param id(教材编号)
     * @return  textbookEntity（教材对象）
     * @Title: index
     * @Description: 教材详情
     * @author lixiong
     */
    @RequestMapping(value = "/textbook", method = RequestMethod.POST)
    public R textbook(Integer id) {
     return  textbookService.textbook(id);
    }

    /**
     * @param
     * @return
     * @Title: index
     * @Description: 教材图片
     * @author lixiong
     */
    @RequestMapping(value="/queryImgs",method=RequestMethod.POST)
    public R queryImgs(String resourceStr){
        String[] resourceImg = resourceStr.split(",");
        EntityWrapper<Resources> wrapper = new EntityWrapper<Resources>();
        wrapper.in("id", resourceImg);
        List<Resources> imgList = resourcesManager.selectList(wrapper);
        return R.success().put("imgList", imgList);
    }
}