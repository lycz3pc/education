package com.xhpower.education.system.controller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.xhpower.education.system.entity.Admin;
import com.xhpower.education.system.entity.Role;
import com.xhpower.education.system.manager.AdminManager;
import com.xhpower.education.system.manager.RoleManager;
import com.xhpower.education.utils.R;
import com.xhpower.education.utils.ShiroUtils;
import com.xhpower.plugins.common.security.MD5;
import com.xhpower.plugins.common.utils.StringHelper;

@RestController
@RequestMapping("/admin/sys/admin")
public class AdminController {
	@Autowired
	private AdminManager adminManager;
	@Autowired
	private RoleManager roleManager;
	
	 @RequestMapping("/list")
     public R list(Page<Admin> page, Admin admin){
	     EntityWrapper<Admin> wrapper = new EntityWrapper<>();
	     wrapper.like("name", admin.getName());
	     wrapper.like("username", admin.getUsername());
		 wrapper.notIn("username","xhpower");
    	 return R.success().page(adminManager.selectPage(page, wrapper));
     }

	@RequestMapping("/listxh")
	public R listxh(Page<Admin> page, Admin admin){
		EntityWrapper<Admin> wrapper = new EntityWrapper<>();
		wrapper.like("name", admin.getName());
		wrapper.like("username", admin.getUsername());
		return R.success().page(adminManager.selectPage(page, wrapper));
	}
	 
	 @RequestMapping(value="/save",method=RequestMethod.POST)
     public R save(Admin admin){
		admin.setPassword(MD5.encrypt(admin.getPassword()));
		return  adminManager.insert(admin)?R.success():R.error();
     }
	 
	 @RequestMapping(value="/update",method=RequestMethod.POST)
     public R update(Admin admin){
	 	if(!StringUtils.isEmpty(admin.getPassword())){
			admin.setPassword(MD5.encrypt(admin.getPassword()));
		}else {
			admin.setPassword(null);
		}
		 adminManager.updateById(admin);
		 return  R.success();
     }
	 
	 @RequestMapping(value="/delete",method=RequestMethod.POST)
     public R delete(Long id){
		 if(adminManager.delectAdmin(id)){
			 return R.success();
		 }else {
			 return R.error("服务器异常");
		 }
     }
	 @RequestMapping(value="/check",method=RequestMethod.POST)
	 public R check(Admin admin){
		    EntityWrapper<Admin> wrapper = new EntityWrapper<Admin>();
		    wrapper.setEntity(admin);
		    return adminManager.selectCount(wrapper)>0?R.error():R.success();
		    
	 }
	 
	 @RequestMapping(value="/resetPassword",method=RequestMethod.POST)
	 public R resetPassword(Long []ids){
		 List<Admin> list  = new ArrayList<>();
		 for (Long id : ids) {
			 Admin entity = adminManager.selectById(id);
			 entity.setPassword(MD5.encrypt("123456"));
			 if(entity!=null){
				 adminManager.updateByPrimaryKey(entity);
				 list.add(entity);
			 }
		  }
		 return list.size()==ids.length?R.success():R.error("用户不存在");
	 }
	 
	 @RequestMapping(value="/getRoles",method=RequestMethod.GET)
	 public R getRoles(Long  id){
		 if(id!=null)  {
			 Admin admin = adminManager.selectById(id);
			 return  R.success().list(roleManager.list()).put("roles", roleManager.selectByUsername(admin.getUsername()));
		 }else{
			 return  R.success().list(roleManager.list());
		 }
	 }
	 
	 @RequestMapping(value="/assignRole",method=RequestMethod.POST)
	 public R assignRole(Long []ids,String []roleNames){
		  List<Admin> list  = new ArrayList<>();
		 for (Long id : ids) {
			 Admin entity = adminManager.selectById(id);
			 Set<Role> roles = new  HashSet<Role>();
			 for (String roleName : roleNames) {
				 	EntityWrapper<Role> wrapper =  new EntityWrapper<Role>();
					 roles.add(roleManager.selectOne(wrapper.eq("name", roleName)));
				}
			 if(entity!=null){
				 adminManager.updateAdmin(entity, roles);
				 list.add(entity);
			 }
		  }
		 return list.size()==ids.length?R.success():R.error("用户不存在");
	 }
	 
	 @RequestMapping("/info")
	 public R getAdmin(Long id){
		 return R.success().put("admin", ShiroUtils.getAdminEntity());
	 }

	@RequestMapping("/getAdmin")
	public R getAdmin(Integer id){
		return R.success().put("admin", adminManager.selectById(id));
	}
	 
	 @RequestMapping("/password")
	 public R password(String password, String newPassword){
		 if(StringHelper.isStringEmpty(newPassword)){
			 return R.error("新密码不能为空");
		 }
		 Admin admin =  ShiroUtils.getAdminEntity();
		 admin = adminManager.selectById(admin.getId());
		 if(!admin.getPassword().equals(MD5.encrypt(password))){
			 return R.error("原密码不正确");
		 }
		 admin.setPassword(MD5.encrypt(newPassword));
		 return adminManager.updateById(admin) ? R.success():R.error();
	 }
	 private static String getHash(String fileName, String hashType)  
		        throws Exception  
		    {  
		        InputStream fis = new FileInputStream(fileName);  
		        byte buffer[] = new byte[1024];  
		        MessageDigest md5 = MessageDigest.getInstance(hashType);  
		        for(int numRead = 0; (numRead = fis.read(buffer)) > 0;)  
		        {  
		            md5.update(buffer, 0, numRead);  
		        }  
		        fis.close();
		        return bytesToHexString(md5.digest());  
		    }  
	 
	
	 public static String bytesToHexString(byte[] src){  
		    StringBuilder stringBuilder = new StringBuilder("");  
		    if (src == null || src.length <= 0) {  
		        return null;  
		    }  
		    for (int i = 0; i < src.length; i++) {  
		        int v = src[i] & 0xFF;  
		        String hv = Integer.toHexString(v);  
		        if (hv.length() < 2) {  
		            stringBuilder.append(0);  
		        }  
		        stringBuilder.append(hv);  
		    }  
		    return stringBuilder.toString();  
		}  
	 
	 public static void main(String[] args) throws Exception {
		 String s = getHash("D:\\LudashiDownloads\\电魂游戏.rar", "SHA1");
		 System.out.println(s);
		 List<Long> idList = new ArrayList<>();
		 idList.add(1L);
		 idList.add(2L);
		 System.out.println(JSON.toJSONString(idList));
	}
}
