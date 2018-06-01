package com.xhpower.wexin.regist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xhpower.education.platform.entity.CmsEntity;
import com.xhpower.education.utils.R;
import com.xhpower.wexin.jdclick.manager.NpWxClickRecordTManager;
import com.xhpower.wexin.regist.entity.NpWxRegistRecordT;
import com.xhpower.wexin.regist.manager.NpWxRegistRecordTManager;

/**
 * <p>
 * 通过校区二维码注册记录 前端控制器
 * </p>
 *
 * @author Song BoLin
 * @since 2017-10-23
 */
@RestController
@RequestMapping("/regist/npWxRegistRecordT")
public class NpWxRegistRecordTController {
	@Autowired
	private NpWxRegistRecordTManager npWxRegistRecordTManager;
	
	 /**
	 * 
	* @Title: 统计每一天注册量
	* @Description: 统计图
	* @param start 开始时间
	* @param end 结束时间
	* @return 
	* @author song bolin
	 */
	 @RequestMapping("/countRegistList")
	 public R countRegistList(@RequestBody JSONObject jsonObject){
		 //最近一个月的注册量查询
           if(jsonObject.getInteger("start") ==null){
        	   jsonObject.put("start",(int) (System.currentTimeMillis()/1000-2591000));
           }
           if(jsonObject.getInteger("end") ==null){
        	   jsonObject.put("end",(int) (System.currentTimeMillis()/1000));
            }
			 return  R.success().list(npWxRegistRecordTManager.dayRegistList(jsonObject.getInteger("start"),jsonObject.getInteger("end")));
		 
	 }
	 
	 
	 /**
		 * 
		* @Title: 统计历史总注册量
		* @Description: 统计图
		* @return 
		* @author song bolin
		 */
	 @RequestMapping("/countAllRegist")
	 public Integer countAllRegist(){
		 return  npWxRegistRecordTManager.countAllRegist();
	 }
	 
	 
	 /**
		 * 
		* @Title: 统计当前月注册量
		* @Description: 统计图
		* @return 
		* @author song bolin
		 */
	 @RequestMapping("/countOneMonthRegist")
	 public R countOneMonthRegist(@RequestBody JSONObject jsonObject){
		 return R.success().list(npWxRegistRecordTManager.countOneMonthRegist(jsonObject.getInteger("month")));
	 }
	 
	 /**
		 * 
		* @Title: 统计当前月各个校区的注册量
		* @Description: 统计图
		* @return 
		* @author song bolin
		 */
	 @RequestMapping("/countCampusRegistList")
	 public R countCampusRegistList(@RequestBody JSONObject jsonObject){
		//最近一个月的各个校区注册量查询
         if(jsonObject.getInteger("start") ==null){
      	   jsonObject.put("start",(int) (System.currentTimeMillis()/1000-2591000));
         }
         if(jsonObject.getInteger("end") ==null){
      	   jsonObject.put("end",(int) (System.currentTimeMillis()/1000));
          }
			 return  R.success().list(npWxRegistRecordTManager.campusRegistList(jsonObject.getInteger("start"),jsonObject.getInteger("end")));
		 
	 }
	 
	 /**
		 * 
		* @Title: 查询各个校区
		* @Description: 统计图
		* @return 
		* @author song bolin
		 */
	 @RequestMapping("/queryCampusList")
	 public R queryCampusList(){
		 EntityWrapper<NpWxRegistRecordT> wrapper = new EntityWrapper<>();
		 wrapper.groupBy("campus_name");
		 return R.success().list(npWxRegistRecordTManager.selectList(wrapper));
	 }
}
