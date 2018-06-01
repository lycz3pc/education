package com.xhpower.education.platform.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xhpower.education.platform.dao.ExpertEntityMapper;
import com.xhpower.education.platform.dao.QuestionsEntityMapper;
import com.xhpower.education.platform.entity.AnswerEntity;
import com.xhpower.education.platform.entity.BkUserInfoVO;
import com.xhpower.education.platform.entity.ExpertEntity;
import com.xhpower.education.platform.entity.QuestionsEntity;
import com.xhpower.education.platform.entity.SysmsgEntity;
import com.xhpower.education.platform.entity.UserBehaviourEntity;
import com.xhpower.education.platform.service.AnswerService;
import com.xhpower.education.platform.service.QuestionsService;
import com.xhpower.education.platform.service.SysmsgService;
import com.xhpower.education.platform.service.UserBehaviourService;
import com.xhpower.education.utils.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("questionsService")
@Transactional
public class QuestionsServiceImpl extends ServiceImpl<QuestionsEntityMapper, QuestionsEntity> implements QuestionsService {

	@Autowired
	private QuestionsEntityMapper questionsMapper;
	@Autowired
	private ExpertEntityMapper expertMapper;
	@Autowired
	private AnswerService answerService;
	@Autowired
	private SysmsgService SysmsgService;
	@Autowired
	private UserBehaviourService userBehaviourService;
	
	@Override
	public R selectQueList(Page<QuestionsEntity> pages, QuestionsEntity questionsEntity) {
        EntityWrapper<QuestionsEntity> wrapper = new EntityWrapper<QuestionsEntity>();
        wrapper.setEntity(new QuestionsEntity());
        if(questionsEntity.getName()!=null && !"".equals(questionsEntity.getName())){
            wrapper.like("name",questionsEntity.getName().replace(" ", ""));
        }
        wrapper.orderBy("create_date", false);
        pages = this.selectPage(pages,wrapper);
        
        List<QuestionsEntity> list = pages.getRecords();
        for(QuestionsEntity ques : list){
        	ques.setUserName(this.getUserInfo(ques.getCreateUser()).getUserName());
        }
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", pages.getTotal());
        map.put("rows", list);
		
		return R.success(map);
	}
	
	@Override
	public R deleteQue(Integer id) {
        
		EntityWrapper<AnswerEntity> wrapper = new EntityWrapper<AnswerEntity>();
        wrapper.eq("questions_id",id);
        //查询回答
        List<AnswerEntity> answerList = answerService.selectList(wrapper);
        
        //删除消息
        EntityWrapper<SysmsgEntity> msgWrapper = new EntityWrapper<SysmsgEntity>();
        msgWrapper.eq("`from`", "parent");
        msgWrapper.like("object_paths", id.toString());
        SysmsgService.delete(msgWrapper);
        	
        //删除用户行为
        UserBehaviourEntity entity = new UserBehaviourEntity();
        EntityWrapper<UserBehaviourEntity> behaviourWrapper = new EntityWrapper<UserBehaviourEntity>(entity);
        entity.setSource("parent");
        entity.setType("1");
        for(AnswerEntity answer : answerList){
        	entity.setObjectId(answer.getId().toString());
        	userBehaviourService.delete(behaviourWrapper);
        }
        
        //删除问题关注
        entity.setType("2");
        entity.setObjectId(id.toString());
        userBehaviourService.delete(behaviourWrapper);
        
        //删除回答
        answerService.delete(wrapper);
        EntityWrapper<QuestionsEntity> wrapper1 = new EntityWrapper<QuestionsEntity>();
        wrapper1.eq("id",id);
        boolean b = this.delete(wrapper1);
        
        return b ? R.success() : R.error("服务器异常");
	}

	@Override
	public BkUserInfoVO getUserInfo(Integer userId) {
		BkUserInfoVO userInfo = questionsMapper.getUserInfo(userId);
		if(userInfo == null){
			userInfo = new BkUserInfoVO();
			ExpertEntity expert = expertMapper.selectById(userId);
			if(expert != null){
				userInfo.setUserId(expert.getId());
				userInfo.setUserName(expert.getName());
			}
		}
		return userInfo;
	}

	@Override
	public R questionAuth(QuestionsEntity ques) {
		QuestionsEntity enty = this.selectById(ques.getId());
		if(null == enty){
			return R.error("问题不存在");
		}
		//审核成功
		enty.setStatus(ques.getStatus());
		return this.updateById(enty)?R.success():R.error("审核失败");
	}

}
