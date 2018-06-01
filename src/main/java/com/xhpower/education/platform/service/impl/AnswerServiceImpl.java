package com.xhpower.education.platform.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xhpower.education.platform.dao.AnswerEntityMapper;
import com.xhpower.education.platform.entity.AnswerEntity;
import com.xhpower.education.platform.entity.QuestionsEntity;
import com.xhpower.education.platform.entity.SysmsgEntity;
import com.xhpower.education.platform.entity.UserBehaviourEntity;
import com.xhpower.education.platform.service.AnswerService;
import com.xhpower.education.platform.service.QuestionsService;
import com.xhpower.education.platform.service.SysmsgService;
import com.xhpower.education.platform.service.UserBehaviourService;
import com.xhpower.education.utils.R;


@Service("answerService")
@Transactional
public class AnswerServiceImpl extends ServiceImpl<AnswerEntityMapper, AnswerEntity> implements AnswerService {
	
	@Autowired
	private QuestionsService questionsService;
	@Autowired
	private SysmsgService SysmsgService;
	@Autowired
	private UserBehaviourService userBehaviourService;

	@Override
	public R selectAnswerList(Page<AnswerEntity> pages, AnswerEntity answerEntity) {
		
		EntityWrapper<AnswerEntity> wrapper = new EntityWrapper<AnswerEntity>();
        wrapper.eq("questions_id",answerEntity.getId());
        if(answerEntity.getContent()!=null && !"".equals(answerEntity.getContent())){
            wrapper.like("content",answerEntity.getContent().replace(" ", ""));
        }
        wrapper.orderBy("create_time", false);
        pages = this.selectPage(pages,wrapper);
        
        List<AnswerEntity> list = pages.getRecords();
        for(AnswerEntity ques : list){
        	ques.setUserName(questionsService.getUserInfo(ques.getCreateUser()).getUserName());
        }
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", pages.getTotal());
        map.put("rows", list);
		
		return R.success(map);
	}

	@Override
	public R deleteAnswer(Integer id) {
		
		AnswerEntity answer = this.selectById(id);
		
		//删除子回答
		EntityWrapper<AnswerEntity> childWrapper = new EntityWrapper<AnswerEntity>();
		childWrapper.eq("parent_id", id);
		this.delete(childWrapper);
		
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
        entity.setObjectId(id.toString());
        userBehaviourService.delete(behaviourWrapper);
		
        boolean b = this.deleteById(id);
        //更新评论数
        if(answer.getParentId().equals("-1")){
        	QuestionsEntity que = questionsService.selectById(answer.getQuestionsId());
        	if(que != null){
        		Integer cnt = que.getComments()==null?0:que.getComments();
        		que.setComments(cnt>0?(cnt-1):0);
        		questionsService.updateById(que);
        	}
        }else{
        	AnswerEntity ans = this.selectById(answer.getParentId());
        	if(ans != null){
        		Integer cnt = ans.getComments()==null?0:ans.getComments();
        		ans.setComments(cnt>0?(cnt-1):0);
        		this.updateById(ans);
        	}
        }
        
        
        return b ? R.success() : R.error("服务器异常");
        
	}

	@Override
	public R answerAuth(AnswerEntity answerEntity) {
		AnswerEntity enty = this.selectById(answerEntity.getId());
		if(null == enty){
			return R.error("记录不存在");
		}
		enty.setStatus(answerEntity.getStatus());
		//更新父记录的回复量
		if(answerEntity.getStatus()==1){
			if (enty.getParentId().equals("-1")) {
				//父节点是问题
				QuestionsEntity dto = questionsService.selectById(Integer.parseInt(enty.getQuestionsId()));
				if (null != dto) {
					Integer num = dto.getComments() == null ? 1 : (dto.getComments() + 1);
					dto.setComments(num);

				}
				questionsService.updateById(dto);

			} else {
				AnswerEntity answer = this.selectById(Integer.parseInt(enty.getParentId()));
				if (null != answer) {
					Integer num = answer.getComments() == null ? 1 : (answer.getComments() + 1);
					answer.setComments(num);
				}
				this.updateById(answer);
			}
		}

		return this.updateById(enty)?R.success():R.error("审核失败");
	}


}
