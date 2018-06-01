package com.xhpower.education.platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xhpower.education.platform.entity.NpConstantItem;
import com.xhpower.education.platform.service.NpConstantItemService;
import com.xhpower.education.utils.R;

/**
 * 
* @ClassName: NpConstantItemController 
* @Description: 新父母学校常量项管理控制层
* @author xiong li 
* @date 2017年9月30日 上午11:22:29 
*
 */
@RestController
@RequestMapping("/platform/constant/item")
public class NpConstantItemController {

	@Autowired
	private NpConstantItemService npConstantItemService;

	/**
	 * 
	* @Title: list 
	* @Description: 分页查询常量列表
	* @param page 当前页
	* @param rows 每页显示条数
	* @param npConstantItem 条件
	* @return 
	* @author Administrator
	 */
	@RequestMapping("/list")
	public R list(int page, int rows, NpConstantItem npConstantItem){
		return npConstantItemService.getConstantList(page, rows, npConstantItem);
	}
	
	/**
	 * 
	* @Title: addOrUpdate 
	* @Description: 新增或更新常量
	* @param npConstantItem
	* @return 
	* @author xiong li
	 */
	@RequestMapping("/addOrUpdate")
	public R addOrUpdate(NpConstantItem npConstantItem){
		
		return npConstantItemService.addOrUpdateConstant(npConstantItem);
	}
	
	/**
	 * 
	* @Title: delete 
	* @Description: 删除常量 
	* @param id
	* @return 
	* @author xiong li
	 */
	@RequestMapping("/delete")
	public R delete(String id){
		return npConstantItemService.deleteConstant(id);
	}
	
	/**
	 * 
	* @Title: hasConstant 
	* @Description: 是否有属于常量类型的常量
	* @param categoryId
	* @return 
	* @author xiong li
	 */
	@RequestMapping("/hasConstant")
	public R hasConstant(String categoryId){
		return npConstantItemService.getConstantByCategoryId(categoryId).size() > 0 ? R.success() : R.error();
	}
	
	/**
	 * 
	* @Title: refreshWebConstantItem 
	* @Description: 刷新家校共育常量
	* @return 
	* @author xiong li
	 */
	@RequestMapping("/refreshWebConstantItem")
	public R refreshWebConstantItem(){
		 npConstantItemService.refreshWebConstantItem();
		 return R.success();
	}
}
