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
import com.xhpower.education.platform.dao.NpConstantCategoryMapper;
import com.xhpower.education.platform.entity.NpConstantCategory;
import com.xhpower.education.platform.entity.NpConstantItem;
import com.xhpower.education.platform.service.NpConstantCategoryService;
import com.xhpower.education.platform.service.NpConstantItemService;
import com.xhpower.education.utils.R;

/**
 * 
* @ClassName: NpConstantCategoryServiceImpl 
* @Description: 新父母学校常量类型管理SERVICE层实现类
* @author xiong li 
* @date 2017年9月30日 上午11:13:11 
*
 */
@Service
@Transactional
public class NpConstantCategoryServiceImpl extends ServiceImpl<NpConstantCategoryMapper, NpConstantCategory>
                                           implements NpConstantCategoryService {

	@Autowired
	private NpConstantItemService npConstantItemService;
	
	@Override
	public R getCategoryList(int page, int rows, NpConstantCategory npConstantCategory) {
		//创建分页
		Page<NpConstantCategory> pages = new Page<NpConstantCategory>(page, rows);
		//查询条件
		EntityWrapper<NpConstantCategory> wrapper = new EntityWrapper<NpConstantCategory>(npConstantCategory);
		
		pages = this.selectPage(pages, wrapper);
		//封装成easyui列表显示的数据个数
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", pages.getTotal());
		map.put("rows", pages.getRecords());
		return R.success(map);
	}

	@Override
	public R addOrUpdateCategory(NpConstantCategory npConstantCategory) {
		Boolean bool = false;
		bool = this.insertOrUpdate(npConstantCategory);
		if(npConstantCategory.getCategoryId() != null){
			NpConstantItem item = new NpConstantItem();
			item.setCategoryName(npConstantCategory.getCategoryName());
			EntityWrapper<NpConstantItem> wrapper = new EntityWrapper<NpConstantItem>();
			wrapper.eq("category_id", npConstantCategory.getCategoryId());
			npConstantItemService.update(item, wrapper);
		}
		
		return bool ? R.success() : R.error();
	}

	@Override
	public R deleteCategoryById(String categoryId) {
		//查询常量类型下的常量项
		List<NpConstantItem> constants = npConstantItemService.getConstantByCategoryId(categoryId);
		//该类型下存在常量时，先删除常量
		if(constants.size() > 0){
			npConstantItemService.deleteBatchIds(constants);
		}
		
		return this.deleteById(categoryId) ? R.success() : R.error();
	}

	@Override
	public R checkCategoryName(NpConstantCategory npConstantCategory) {
		//判断新增或更新
		if(npConstantCategory.getCategoryId() != null){
			EntityWrapper<NpConstantCategory> wrapper = new EntityWrapper<NpConstantCategory>();
			wrapper.eq("category_name", npConstantCategory.getCategoryName());
			NpConstantCategory ck = this.selectOne(wrapper);
			//更新时，如果验证对象是本身就通过
			if(ck != null && ck.getCategoryId().equals(npConstantCategory.getCategoryId())){
				return R.success();
			}else{
				npConstantCategory.setCategoryId(null);
			}
		}
		
		return this.selectOne(new EntityWrapper<NpConstantCategory>(npConstantCategory)) != null ? R.error() : R.success();
	}

	@Override
	public R allConstantCategory() {
		
		return R.success().list(this.selectList(new EntityWrapper<NpConstantCategory>()));
	}



}
