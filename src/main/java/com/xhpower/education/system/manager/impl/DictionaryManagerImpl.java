package com.xhpower.education.system.manager.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xhpower.education.system.dao.DictionaryMapper;
import com.xhpower.education.system.entity.Dictionary;
import com.xhpower.education.system.manager.DictionaryManager;

@Service
public class DictionaryManagerImpl extends ServiceImpl<DictionaryMapper, Dictionary>
		implements DictionaryManager {

}
