package com.xhpower.education.platform.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xhpower.education.platform.dao.UserBehaviourEntityMapper;
import com.xhpower.education.platform.entity.UserBehaviourEntity;
import com.xhpower.education.platform.service.UserBehaviourService;

/**
 * 
* @ClassName: UserBehaviourServiceImpl 
* @Description: 用户行为业务实现类
* @author xiong li 
* @date 2017年10月30日 下午5:48:27 
*
 */
@Service
@Transactional
public class UserBehaviourServiceImpl extends ServiceImpl<UserBehaviourEntityMapper, UserBehaviourEntity> implements UserBehaviourService {

	

}
