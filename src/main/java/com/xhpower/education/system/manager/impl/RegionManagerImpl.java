package com.xhpower.education.system.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xhpower.education.system.dao.RegionMapper;
import com.xhpower.education.system.entity.Region;
import com.xhpower.education.system.manager.RegionManager;
@Service
public class RegionManagerImpl extends ServiceImpl<RegionMapper, Region> implements RegionManager {

	@Autowired
	private RegionMapper  regionMapper;
	
	@Override
	public List<Region> selectByParentId(String id) {
		return regionMapper.selectByParentId(id);
	}

	@Override
	public List<Region> like(String str) {
		// TODO Auto-generated method stub
		return regionMapper.like(str);
	}

}
