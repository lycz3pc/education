package com.xhpower.education.platform.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xhpower.education.platform.dao.ConstantEntityMapper;
import com.xhpower.education.platform.entity.ConstantEntity;
import com.xhpower.education.platform.service.ConstantService;

@Service("constantService")
@Transactional
public class ConstantServiceImpl extends ServiceImpl<ConstantEntityMapper, ConstantEntity> implements ConstantService {
	@Autowired
	private ConstantEntityMapper constantEntityMapper;
	
	@Override
	public List<ConstantEntity> getSectionData(String category_key,String parent_id) {
		EntityWrapper<ConstantEntity> wrapper = new EntityWrapper<ConstantEntity>();
		wrapper.eq("category_id", "800");
		List<ConstantEntity> sectionList = constantEntityMapper.selectList(wrapper);
		if("section".equals(category_key)){
			    //如果parent_id为空返回所有年段List
				if(StringUtils.isEmpty(parent_id)){
					return sectionList;
				}else{
					//如果parent_id不为空返回该年段List
					EntityWrapper<ConstantEntity> wrapper2 = new EntityWrapper<ConstantEntity>();
					wrapper2.eq("category_id", "800");
					wrapper2.eq("id", parent_id);
					sectionList = constantEntityMapper.selectList(wrapper);
					for (ConstantEntity constant : sectionList) {
						EntityWrapper<ConstantEntity> wrapper3 = new EntityWrapper<ConstantEntity>();
						wrapper3.like("parent_ids", constant.getId());
						wrapper3.eq("category_id", "900");
						List<ConstantEntity> gradeList = constantEntityMapper.selectList(wrapper3);
						constant.setChirdList(gradeList);
					}
					return sectionList;
				}
		}else{
			//查询所有年段下所有年级
			for (ConstantEntity constant : sectionList) {
				EntityWrapper<ConstantEntity> wrapper3 = new EntityWrapper<ConstantEntity>();
				wrapper3.like("parent_ids", constant.getId());
				wrapper3.eq("category_id", "900");
				List<ConstantEntity> gradeList = constantEntityMapper.selectList(wrapper3);
				constant.setChirdList(gradeList);
			}
			return sectionList;
		}
	}

	@Override
	public  List<ConstantEntity> getConstantByCategory(String categoryId) {
		EntityWrapper<ConstantEntity> wrapper = new EntityWrapper<ConstantEntity>(); //获取省
		wrapper.eq("category_id", "100");
		List<ConstantEntity> provinceList = constantEntityMapper.selectList(wrapper);
		List<ConstantEntity> cityList =new ArrayList<ConstantEntity>();
		//map.put("province", provinceList);
		for(ConstantEntity province : provinceList){
        	//根据省获取市
			EntityWrapper<ConstantEntity> wrapper1 = new EntityWrapper<ConstantEntity>(); //获取市
			wrapper1.eq("category_id", "200");
			wrapper1.eq("parent_ids", province.getId());
			cityList= constantEntityMapper.selectList(wrapper1);
        	province.setChirdList(cityList);
        	//map.put(province.getId(), cityList);
        	for(ConstantEntity city : cityList){
        		//根据市获取区县
        		EntityWrapper<ConstantEntity> wrapper2 = new EntityWrapper<ConstantEntity>(); //获取区、县
        		wrapper2.eq("category_id", "300");
        		wrapper2.eq("parent_ids",  city.getId());
        		city.setChirdList(constantEntityMapper.selectList(wrapper2));
            	}
        }
		return provinceList;
	}


	/*@Override
	public List<ConstantEntity> getTextBookType(String categoryId) {
		EntityWrapper<ConstantEntity> wrapper = new EntityWrapper<ConstantEntity>(); //获取省
		wrapper.eq("category_id", categoryId);
		return constantEntityMapper.selectList(wrapper);
	}*/

}
