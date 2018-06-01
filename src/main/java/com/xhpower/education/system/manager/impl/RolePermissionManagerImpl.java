package com.xhpower.education.system.manager.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xhpower.education.system.dao.RolePermissionMapper;
import com.xhpower.education.system.entity.RolePermission;
import com.xhpower.education.system.manager.RolePermissionManager;

@Service
@Transactional
public class RolePermissionManagerImpl extends ServiceImpl<RolePermissionMapper,RolePermission> implements RolePermissionManager {

}
