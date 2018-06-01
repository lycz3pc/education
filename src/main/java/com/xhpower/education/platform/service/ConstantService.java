package com.xhpower.education.platform.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xhpower.education.platform.entity.ConstantEntity;

public interface ConstantService extends IService<ConstantEntity>{

	List<ConstantEntity> getSectionData(String category_key,String parent_id);

	List<ConstantEntity>  getConstantByCategory(String categoryId);


}
