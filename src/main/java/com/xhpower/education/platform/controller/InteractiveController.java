package com.xhpower.education.platform.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xhpower.education.annotation.SysLog;
import com.xhpower.education.common.BaseController;
import com.xhpower.education.platform.entity.AnswerEntity;
import com.xhpower.education.platform.entity.QuestionsEntity;
import com.xhpower.education.platform.service.AnswerService;
import com.xhpower.education.platform.service.QuestionsService;
import com.xhpower.education.utils.R;

@RestController
@RequestMapping(value = "/platform/Interactive")
public class InteractiveController extends BaseController {

    @Autowired
    private QuestionsService questionsService;//问题
    @Autowired
    private AnswerService answerService;//评论

    /**
     * @param
     * @return
     * @Title: index
     * @Description: 问答列表
     * @author lixiong
     */
    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public R index(int page , int rows,QuestionsEntity questionsEntity) {
        Page<QuestionsEntity> pages = new Page<QuestionsEntity>(page, rows,"create_date");
        /*EntityWrapper<QuestionsEntity> wrapper = new EntityWrapper<QuestionsEntity>();
        wrapper.setEntity(new QuestionsEntity());
        if(questionsEntity.getName()!=null && !"".equals(questionsEntity.getName())){
            wrapper.like("name",questionsEntity.getName().replace(" ", ""));
        }
        pages = questionsService.selectPage(pages,wrapper);
        List<CustomerEntity> customerEntityList = customerService.selectList(new EntityWrapper<CustomerEntity>());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", pages.getTotal());
        map.put("rows", pages.getRecords());
        map.put("customerEntityList",customerEntityList);*/
        return questionsService.selectQueList(pages, questionsEntity);
    }

    /**
     * @param
     * @return
     * @Title: index
     * @Description: 评论列表
     * @author lixiong
     */
    @RequestMapping(value = "/answerindex", method = RequestMethod.POST)
    public R answerindex(int page , int rows,AnswerEntity answerEntity) {
        Page<AnswerEntity> pages = new Page<AnswerEntity>(page, rows,"create_time");
        /*EntityWrapper<AnswerEntity> wrapper = new EntityWrapper<AnswerEntity>();
        wrapper.setEntity(new AnswerEntity());
        wrapper.eq("questionsid",answerEntity.getId());
        if(answerEntity.getContent()!=null && !"".equals(answerEntity.getContent())){
            wrapper.like("content",answerEntity.getContent().replace(" ", ""));
        }
        pages = answerService.selectPage(pages,wrapper);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", pages.getTotal());
        map.put("rows", pages.getRecords());*/
        return answerService.selectAnswerList(pages, answerEntity);
    }



    /**
     * @param
     * @return
     * @Title: index
     * @Description: 验证是否存在评论
     * @author lixiong
     */
    @RequestMapping(value = "/checkdel", method = RequestMethod.POST)
    public R checkdel(Integer id) {
        EntityWrapper<AnswerEntity> wrapper = new EntityWrapper<AnswerEntity>();
        wrapper.setEntity(new AnswerEntity());
        wrapper.eq("questions_id",id);
        List<AnswerEntity> answerEntityList = answerService.selectList(wrapper);
        if(answerEntityList.size()>0){
            return R.success().put("bools",false);
        }else {
            return R.success().put("bools",true);
        }
    }

    /**
     * @param
     * @return
     * @Title: index
     * @Description: 问题删除
     * @author lixiong
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @SysLog("问题删除")
    public R delete(Integer id) {
        /*EntityWrapper<AnswerEntity> wrapper = new EntityWrapper<AnswerEntity>();
        wrapper.setEntity(new AnswerEntity());
        wrapper.eq("questionsid",id);
        answerService.delete(wrapper);
        EntityWrapper<QuestionsEntity> wrapper1 = new EntityWrapper<QuestionsEntity>();
        wrapper1.setEntity(new QuestionsEntity());
        wrapper1.eq("id",id);
        boolean b = questionsService.delete(wrapper1);
        if(b==true){
            return   R.success();
        }else {
            return R.error("服务器异常");
        }*/
    	
    	return questionsService.deleteQue(id);
    }

    /**
     * @param
     * @return
     * @Title: index
     * @Description: 评论删除
     * @author lixiong
     */
    @RequestMapping(value = "/answedelete", method = RequestMethod.POST)
    @SysLog("评论删除")
    public R answedelete(Integer id) {
       /* EntityWrapper<AnswerEntity> wrapper1 = new EntityWrapper<AnswerEntity>();
        wrapper1.setEntity(new AnswerEntity());
        wrapper1.eq("id",id);
        boolean b = answerService.delete(wrapper1);
        if(b==true){
            return   R.success();
        }else {
            return R.error("服务器异常");
        }*/
    	return answerService.deleteAnswer(id);
    }
    
    /**
     * 
    * @Title: questionAuth 
    * @Description: 问题审核
    * @param ques
    * @return 
    * @author xiong li
     */
    @RequestMapping(value = "/questionAuth", method = RequestMethod.POST)
    @SysLog("家长圈子问题审核")
    public R questionAuth(QuestionsEntity ques){
    	
    	return questionsService.questionAuth(ques);
    }
    
    /**
     * 
    * @Title: answerAuth 
    * @Description: TODO(这里用一句话描述这个方法的作用) 
    * @param answerEntity
    * @return 
    * @author Administrator
     */
    @RequestMapping(value = "/answerAuth", method = RequestMethod.POST)
    @SysLog("家长圈子问题回复审核")
    public R answerAuth(AnswerEntity answerEntity){
    	
    	return answerService.answerAuth(answerEntity);
    }
}