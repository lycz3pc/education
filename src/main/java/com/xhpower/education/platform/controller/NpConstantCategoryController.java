package com.xhpower.education.platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xhpower.education.platform.entity.NpConstantCategory;
import com.xhpower.education.platform.service.NpConstantCategoryService;
import com.xhpower.education.utils.R;

/**
* @ClassName: NpConstantCategory 
* @Description: 新父母学校常量类型控制层
* @author xiong li 
* @date 2017年9月30日 上午11:19:10 
*
 */
@RestController
@RequestMapping("/platform/constant/category")
public class NpConstantCategoryController {
	
	@Autowired
	private NpConstantCategoryService npConstantCategoryService;
	
	/**
	 * 
	* @Title: list 
	* @Description: 分页条件查询常量类型列表
	* @param page 当前页
	* @param rows 每页显示条数
	* @param npConstantCategory
	* @return 
	* @author xiong li
	 */
	@RequestMapping("/list")
	public R list(int page, int rows, NpConstantCategory npConstantCategory){
		return npConstantCategoryService.getCategoryList(page, rows, npConstantCategory);
	}
	
	/**
	 * 
	* @Title: addOrUpdate 
	* @Description: 新增或更新常量类型
	* @param npConstantCategory
	* @return 
	* @author xiong li
	 */
	@RequestMapping("/addOrUpdate")
	public R addOrUpdate(NpConstantCategory npConstantCategory){
		return npConstantCategoryService.addOrUpdateCategory(npConstantCategory);
	}
	
	/**
	 * 
	* @Title: delete 
	* @Description: 删除常量类型
	* @param categoryId
	* @return 
	* @author xiong li
	 */
	@RequestMapping("/delete")
	public R delete(String categoryId){
		return npConstantCategoryService.deleteCategoryById(categoryId);
	}
	
	/**
	 * 
	* @Title: checkCategoryName 
	* @Description: 验证类型名称是否存在 
	* @param npConstantCategory
	* @return 
	* @author xiong li
	 */
	@RequestMapping("/checkCategoryName")
	public R checkCategoryName(NpConstantCategory npConstantCategory){
		return npConstantCategoryService.checkCategoryName(npConstantCategory);
	}
	
	/**
	 * 
	* @Title: allConstantCategory 
	* @Description: 查询所有常量类型 
	* @return 
	* @author xiong li
	 */
	@RequestMapping("/allConstantCategory")
	public R allConstantCategory(){
		return npConstantCategoryService.allConstantCategory();
	}

}
