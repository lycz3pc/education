package com.xhpower.education.utils;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.xhpower.education.system.entity.Admin;




/**
 * 
* @ClassName: ShiroUtils 
* @Description: shiro工具类
* @author lisf 
* @date 2017年4月7日 下午8:40:54 
*
 */
public class ShiroUtils {

	public static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}

	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	public static Admin getAdminEntity() {
		if(SecurityUtils.getSubject().getPrincipal() instanceof Admin)
			return (Admin)SecurityUtils.getSubject().getPrincipal();
		return null;
	}
	
	public static String getUsername() {
		return getAdminEntity()!=null?getAdminEntity().getUsername():null;
	}
	
	public static void setSessionAttribute(Object key, Object value) {
		getSession().setAttribute(key, value);
	}

	public static Object getSessionAttribute(Object key) {
		return getSession().getAttribute(key);
	}

	public static boolean isLogin() {
		return SecurityUtils.getSubject().getPrincipal() != null;
	}

	public static void logout() {
		SecurityUtils.getSubject().logout();
	}
	
	public static String getKaptcha(String key) {
		String kaptcha = getSessionAttribute(key).toString();
		getSession().removeAttribute(key);
		return kaptcha;
	}

}
