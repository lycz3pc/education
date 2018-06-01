package com.xhpower.education.platform.service;

import com.baomidou.mybatisplus.service.IService;
import com.xhpower.education.platform.entity.NpConstantCategory;
import com.xhpower.education.utils.R;
/**
 * 
* @ClassName: NpConstantCategoryService 
* @Description: 新父母学校常量类型管理SERVICE层
* @author xiong li 
* @date 2017年9月30日 上午11:07:13 
*
 */
public interface NpConstantCategoryService extends IService<NpConstantCategory> {
	
	/**
	 * 
	* @Title: getCategoryList 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param page 当前页
	* @param rows 每页显示条数
	* @param npConstantCategory
	* @return 
	* @author xiong li
	 */
	public R getCategoryList(int page, int rows, NpConstantCategory npConstantCategory);
	
	/**
	 * 
	* @Title: addOrUpdateCategory 
	* @Description: 新增或更新常量类型
	* @param npConstantCategory
	* @return 
	* @author xiong li
	 */
	public R addOrUpdateCategory(NpConstantCategory npConstantCategory);
	
	/**
	 * 
	* @Title: deleteCategoryById 
	* @Description: 删除常量类型
	* @param categoryId
	* @return 
	* @author xiong li
	 */
	public R deleteCategoryById(String categoryId);
	
	/**
	 * 
	* @Title: checkCategoryName 
	* @Description: 验证类型名称是否存在 
	* @param npConstantCategory
	* @return 
	* @author xiong li
	 */
	public R checkCategoryName(NpConstantCategory npConstantCategory);
	
	/**
	 * 
	* @Title: allConstantCategory 
	* @Description: 查询所有常量类型 
	* @return 
	* @author xiong li
	 */
	public R allConstantCategory();

}
