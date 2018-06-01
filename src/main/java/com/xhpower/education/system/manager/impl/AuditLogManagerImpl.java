package com.xhpower.education.system.manager.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xhpower.education.system.dao.AuditLogMapper;
import com.xhpower.education.system.entity.AuditLog;
import com.xhpower.education.system.manager.AuditLogManager;

@Service
@Transactional
public class AuditLogManagerImpl  extends ServiceImpl<AuditLogMapper, AuditLog> implements AuditLogManager{

}
