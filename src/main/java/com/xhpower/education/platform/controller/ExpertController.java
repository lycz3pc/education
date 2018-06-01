package com.xhpower.education.platform.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xhpower.education.annotation.SysLog;
import com.xhpower.education.platform.entity.ExpertEntity;
import com.xhpower.education.platform.entity.UserInfoEntity;
import com.xhpower.education.platform.service.ExpertService;
import com.xhpower.education.platform.service.UserInfoService;
import com.xhpower.education.system.manager.ResourcesManager;
import com.xhpower.education.utils.R;

/**
 * 
* @ClassName: ExpertController 
* @Description: 专家控制类 
* @author liuyoucheng
* @date 2017年8月26日 下午3:28:04 
*
 */
@RestController
@RequestMapping("/platform/expert")
public class ExpertController {
	
	@Autowired
	private ExpertService expertService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private ResourcesManager resourcesManager;
	/**
	 * 
	* @Title: list 
	* @Description: 专家列表
	* @param page
	* @param rows
	* @return 
	* @author liuyoucheng
	 */
	@RequestMapping("/list")
	public R list(int page , int rows, ExpertEntity expert){
		Page<ExpertEntity> pages = new Page<ExpertEntity>(page, rows);
		pages = expertService.list(pages,expert);
		R r = R.success();
		r.put("total", pages.getTotal());
		r.put("rows", pages.getRecords());
		return r;
	}
	
	/**
	 * 
	* @Title: isTop 
	* @Description: 是否置顶  0:否  1：是
	* @param id
	* @return 
	* @author Liu YouCheng
	 */
	@RequestMapping(value="/isTop",method=RequestMethod.POST)
	@SysLog("置顶专家")
	public R isTop(Integer id){
		ExpertEntity entity = expertService.isTop(id);
		return expertService.updateById(entity)?R.success():R.error();
	}
	
	/**
	 * 
	* @Title: 审核 
	* @Description: 专家审核
	* @param expert
	* @return 
	* @author Liu YouCheng
	 */
	@RequestMapping(value="/check",method=RequestMethod.POST)
	@SysLog("审核专家")
    public R check(ExpertEntity expert){
		/*ExpertEntity entity = expertService.selectById(expert.getId());
		entity.setStatus(1);
		return  expertService.updateById(entity)?R.success():R.error();*/
		return expertService.check(expert);
    }
	
	@RequestMapping(value="/save",method=RequestMethod.POST)
	@SysLog("保存专家")
    public R save(ExpertEntity expert,MultipartFile file,HttpServletRequest request, HttpServletResponse response){
	//	userInfoService
		return expertService.save(expert,file,request) ? R.success():R.error();
    }
	
	/**
	 * 
	* @Title: updateImg 
	* @Description: 更新专家头像
	* @param expert
	* @param file
	* @param request
	* @param response
	* @return 
	* @author xiong li
	 */
	@RequestMapping(value="/updateImg",method=RequestMethod.POST)
	@SysLog("更新专家头像")
	public R updateImg(ExpertEntity expert,MultipartFile file,HttpServletRequest request, HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		return expertService.updateImg(expert, file, request);
	}
	
	/**
	 * 
	* @Title: 删除
	* @Description: 专家逻辑删除
	* @param expert
	* @return 
	* @author Liu YouCheng
	 */
	@RequestMapping(value="/delete")
	@SysLog("删除专家")
    public R delete(Integer id){
		ExpertEntity expert =  expertService.selectById(id);
		expert.setLogicStatus(0);
		return expertService.updateById(expert)?R.success():R.error();
    }
	
	/**
	* @Title: validateSort 
	* @Description: 验证排序 
	* @param expert
	* @return 
	* @author Liu YouCheng
	 */
	/*@RequestMapping("/validateSort")
	public R validateSort(ExpertEntity expert){
		return expertService.validateSort(expert) ? R.success():R.error();
	}*/
	
	/**
	 * 
	* @Title: queryImgs 
	* @Description: 图片回显
	* @param resourceStr
	* @return 
	* @author Liu YouCheng
	 */
	@RequestMapping(value="/queryImgs",method=RequestMethod.POST)
    public Object queryImgs(String resourceStr){
		return resourcesManager.selectById(Integer.parseInt(resourceStr));
    }
	
	/**
	 * 
	* @Title: checkEmail 
	* @Description: 验证邮箱是否存在
	* @param resourceStr
	* @return 
	* @author Liu YouCheng
	 */
    @RequestMapping(value="/checkEmail",method=RequestMethod.POST)
    public Boolean checkEmail(@RequestBody ExpertEntity expert) {
        if(expert.getId() != null){
        	EntityWrapper<ExpertEntity> wrapper1 = new EntityWrapper<ExpertEntity>();
        	EntityWrapper<UserInfoEntity> wrapper2 = new EntityWrapper<UserInfoEntity>();
        	wrapper1.ne("id", expert.getId());
            wrapper1.eq("email", expert.getEmail());
            
            wrapper2.ne("user_id", expert.getUserId());
            wrapper2.eq("login_email", expert.getEmail());
            return expertService.selectCount(wrapper1) == 0 && userInfoService.selectCount(wrapper2) == 0;
        }
        EntityWrapper<ExpertEntity> wrapper3 = new EntityWrapper<ExpertEntity>();
        EntityWrapper<UserInfoEntity> wrapper4= new EntityWrapper<UserInfoEntity>();
        wrapper3.eq("email", expert.getEmail());
        wrapper4.eq("login_email", expert.getEmail());
        return expertService.selectCount(wrapper3) == 0 && userInfoService.selectCount(wrapper4) == 0;
    }
    
    /**
	 * 
	* @Title: checkEmail 
	* @Description: 验证账号是否存在
	* @param resourceStr
	* @return 
	* @author Liu YouCheng
	 */
    @RequestMapping(value="/checkAccount",method=RequestMethod.POST)
    public Boolean checkAccount(@RequestBody ExpertEntity expert) {
        if(expert.getId() != null){
        	EntityWrapper<ExpertEntity> wrapper = new EntityWrapper<ExpertEntity>();
        	EntityWrapper<UserInfoEntity> wrapper2 = new EntityWrapper<UserInfoEntity>();
        	wrapper.ne("id", expert.getId());
            wrapper.eq("telephone", expert.getTelephone());
            
            wrapper2.ne("user_id", expert.getUserId());
            wrapper2.eq("login_phone", expert.getTelephone());
            return expertService.selectCount(wrapper) == 0 && userInfoService.selectCount(wrapper2) == 0;
        }
        EntityWrapper<ExpertEntity> wrapper3 = new EntityWrapper<ExpertEntity>();
        EntityWrapper<UserInfoEntity> wrapper4= new EntityWrapper<UserInfoEntity>();
        wrapper3.eq("telephone", expert.getTelephone());
        wrapper4.eq("login_phone", expert.getTelephone());
        return expertService.selectCount(wrapper3) == 0 && userInfoService.selectCount(wrapper4) == 0;
    }
}
