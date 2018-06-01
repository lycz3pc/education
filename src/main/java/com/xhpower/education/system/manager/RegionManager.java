package com.xhpower.education.system.manager;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xhpower.education.system.entity.Region;

public interface RegionManager extends IService<Region> {

	
	List<Region>  selectByParentId(String id);
	List<Region> like(String str);
}
