package com.xhpower.education.system.core;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

/**
 * 
* @ClassName: RetryLimitHashedCredentialsMatcher 
* @Description: 登录验证与次数限制
* @author lisf 
* @date 2017年4月12日 下午9:13:16 
*
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

	 private Cache<String, AtomicInteger> passwordRetryCache;  
	  
	    public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {  
	        passwordRetryCache = cacheManager.getCache("passwordRetryCache");  
	    }  
	  
	    @Override  
	    public boolean doCredentialsMatch(AuthenticationToken token,  
	            AuthenticationInfo info) {  
	        String username = (String) token.getPrincipal();  
	        // retry count + 1  
	        AtomicInteger retryCount = passwordRetryCache.get(username);  
	        if (retryCount == null) {  
	            retryCount = new AtomicInteger(0);  
	            passwordRetryCache.put(username, retryCount);  
	        }  
	        if (retryCount.incrementAndGet() > 5) {  
	            // if retry count > 5 throw  
	            throw new ExcessiveAttemptsException();  
	        }  
	  
	       // boolean matches = super.doCredentialsMatch(token, info);  
	        boolean  matches =new String((char[]) token.getCredentials()).equals((String)info.getCredentials()); 
	        if (matches) {  
	            // clear retry count  
	            passwordRetryCache.remove(username);  
	        }  
	        return matches;  
	    }  
}
