package com.xhpower.education.system.core;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xhpower.education.system.entity.Admin;
import com.xhpower.education.system.manager.AdminManager;

@Service
public class UserRealm extends AuthorizingRealm  {

	@Autowired
	private AdminManager adminManager;
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Admin admin = (Admin) principals.getPrimaryPrincipal();
		return adminManager.getAuthorizationInfo(admin.getUsername());
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();
		EntityWrapper<Admin> wrapper  =  new EntityWrapper<>();
		Admin entity = new Admin();
		entity.setUsername(username);
		wrapper.setEntity(entity);
		Admin admin = adminManager.selectOne(wrapper);
		
		if(admin == null){
			//木有找到用户
			throw new UnknownAccountException("没有找到该账号");
		}
		
		if(admin.getStatus()!=null && admin.getStatus()){
			throw new LockedAccountException("账号已锁定");
		}
		
		
		/**
		 * 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以在此判断或自定义实现  
		 */
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(admin, admin.getPassword(),getName());
		
		return info;
	}
	
	
	@Override
	public String getName() {
		return getClass().getName();
	}
	
}
