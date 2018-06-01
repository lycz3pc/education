package com.xhpower.education.platform.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xhpower.education.platform.entity.NpConstantItem;
import com.xhpower.education.utils.R;

/**
 * 
* @ClassName: NpConstantItemService 
* @Description: 新父母学校常量项管理SERVICE层 
* @author xiong li
* @date 2017年9月30日 上午11:09:10 
*
 */
public interface NpConstantItemService extends IService<NpConstantItem> {
	
	/**
	* @Title: getConstantByCategoryId 
	* @Description: 根据常量类型查找常量
	* @param categoryId
	* @return 
	* @author xiong li
	 */
	public List<NpConstantItem> getConstantByCategoryId(String categoryId);
	
	/**
	 * 
	* @Title: getConstantList 
	* @Description: 分页查询常量列表
	* @param page
	* @param rows
	* @param npConstantItem
	* @return 
	* @author xiong li
	 */
	public R getConstantList(int page, int rows, NpConstantItem npConstantItem);
	
	/**
	 * 
	* @Title: addOrUpdateConstant 
	* @Description: 新增或更新常量
	* @param npConstantItem
	* @return 
	* @author xiong li
	 */
	public R addOrUpdateConstant(NpConstantItem npConstantItem);
	
	/**
	 * 
	* @Title: deleteConstant 
	* @Description: 删除常量
	* @param costantId
	* @return 
	* @author xiong li
	 */
	public R deleteConstant(String costantId);

	/**
	 * 
	* @Title: getTextBookType 
	* @Description: 获取教材类型
	* @param categoryId
	* @return 
	* @author liu youcheng
	 */
	public List<NpConstantItem> getTextBookType(String categoryId);
	
	/**
	 * 
	* @Title: refreshWebConstantItem 
	* @Description: 刷新家校共育常量
	* @author xiong li
	 */
	public void refreshWebConstantItem();

	
}
