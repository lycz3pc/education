package com.xhpower.education.system.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xhpower.education.common.BaseController;
import com.xhpower.education.system.entity.Dictionary;
import com.xhpower.education.system.manager.DictionaryManager;
import com.xhpower.education.utils.R;

@RestController
@RequestMapping("/admin/dictionary")
public class DictionaryController extends BaseController {
	@Autowired
	private  DictionaryManager dictionaryManager;
	@RequestMapping("list")
	public R list(Page<Dictionary> page,Dictionary dictionary){
		EntityWrapper<Dictionary> wrapper = new EntityWrapper<>();
		wrapper.setEntity(dictionary);
		return R.success().page(dictionaryManager.selectPage(page,wrapper));
	}
	
	 @RequestMapping(value="/save",method=RequestMethod.POST)
     public R save(@RequestBody Dictionary dictionary){ 
		return  dictionaryManager.insert(dictionary)?R.success():R.error();
     }
	 
	 @RequestMapping(value="/update",method=RequestMethod.POST)
     public R update(@RequestBody Dictionary dictionary){
		 return  dictionaryManager.updateById(dictionary)?R.success():R.error();
     }
	 
	 @RequestMapping(value="/delete",method=RequestMethod.DELETE)
     public R delete(Long[] ids){
		 return  dictionaryManager.deleteBatchIds( Arrays.asList(ids))?R.success():R.error();
     }
	 
	@RequestMapping("/query")
	public R query(Dictionary dictionary){
		EntityWrapper<Dictionary> wrapper = new EntityWrapper<>();
		wrapper.setEntity(dictionary);
		return R.success().list(dictionaryManager.selectList(wrapper));
	}
}
