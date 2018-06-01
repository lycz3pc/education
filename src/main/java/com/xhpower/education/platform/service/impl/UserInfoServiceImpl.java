package com.xhpower.education.platform.service.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xhpower.education.platform.dao.UserInfoEntityMapper;
import com.xhpower.education.platform.entity.UserInfoEntity;
import com.xhpower.education.platform.service.UserInfoService;

@Service
@Transactional
public class UserInfoServiceImpl extends ServiceImpl<UserInfoEntityMapper, UserInfoEntity> implements UserInfoService{

}
