package com.xhpower.education.platform.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xhpower.education.platform.dao.NpConstantItemMapper;
import com.xhpower.education.platform.entity.NpConstantItem;
import com.xhpower.education.platform.service.NpConstantItemService;
import com.xhpower.education.system.entity.Admin;
import com.xhpower.education.utils.HttpSendUtils;
import com.xhpower.education.utils.PropertyUtil;
import com.xhpower.education.utils.R;
import com.xhpower.education.utils.ShiroUtils;

/**
 * 
* @ClassName: NpConstantItemServiceImpl 
* @Description: 新父母学校常量项管理SERVICE层实现类
* @author xiong li 
* @date 2017年9月30日 上午11:15:51 
*
 */
@Service
@Transactional
public class NpConstantItemServiceImpl extends ServiceImpl<NpConstantItemMapper, NpConstantItem>
                                       implements NpConstantItemService {
	
	@Override
	public List<NpConstantItem> getConstantByCategoryId(String categoryId) {
		//查询条件
		EntityWrapper<NpConstantItem> wrapper = new EntityWrapper<NpConstantItem>();
		wrapper.eq("category_id", categoryId);

		return this.selectList(wrapper);
	}

	@Override
	public R getConstantList(int page, int rows, NpConstantItem npConstantItem) {
		
		//创建分页对象
		Page<NpConstantItem> pages = new Page<NpConstantItem>(page, rows);
		//查询对象
		EntityWrapper<NpConstantItem> wrapper = new EntityWrapper<NpConstantItem>(npConstantItem);
		wrapper.orderBy("category_id");
		wrapper.orderBy("constant_value");
		
		pages = this.selectPage(pages, wrapper);
		//easyui列表格式数据
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", pages.getTotal());
		map.put("rows", pages.getRecords());
		return R.success(map);
	}

	@Override
	public R addOrUpdateConstant(NpConstantItem npConstantItem) {
		
		Date date = new Date();
		//当前登录用户
		Admin admin = ShiroUtils.getAdminEntity();
		if(npConstantItem.getConstantId() == null){
			npConstantItem.setCreateTime(date);
			npConstantItem.setCreatorId(admin.getId().toString());
		}else{
			npConstantItem.setLastModifyBy(admin.getId().toString());
		}
		npConstantItem.setLastModifyTime(date);
		Boolean bool = this.insertOrUpdate(npConstantItem);
		return bool ? R.success() : R.error();
	}

	@Override
	public R deleteConstant(String costantId) {
		
		return this.deleteById(costantId) ? R.success() : R.error();
	}

	@Override
	public List<NpConstantItem> getTextBookType(String categoryId) {
		//查询条件
		EntityWrapper<NpConstantItem> wrapper = new EntityWrapper<NpConstantItem>();
		wrapper.eq("category_id", categoryId);
		return this.selectList(wrapper);
	}
	
	@Override
	public void refreshWebConstantItem(){
		String url = PropertyUtil.getPropertiesValue("plat.properties", "WEB_SERVER_PATH")+"/common/refreshConstant";
		String out=HttpSendUtils.sendHTTP(url, "post", "utf-8", null, null, null);
	}
	
	/**
	 * 
	* @Title: createConstantId 
	* @Description: 生成常量id
	* @param categoryId
	* @return 
	* @author xiong li
	 */
	/*private String createConstantId(String categoryId){
		String id = "";
		EntityWrapper<NpConstantItem> wrapper = new EntityWrapper<NpConstantItem>();
		wrapper.eq("category_id", categoryId);
		wrapper.orderBy("constant_id", false);
		Page<NpConstantItem> page = new Page<NpConstantItem>(1,1);
		
		page = this.selectPage(page, wrapper);
		
		if(page.getRecords().size() > 0){
			Long count = Long.parseLong(page.getRecords().get(0).getConstantId())+1;
			id = count.toString();
		}else{
			id = categoryId + "001";
		}
		
		return id;
	}*/

}
