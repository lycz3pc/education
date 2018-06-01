package com.xhpower.education.cache;


/** 
 * @ClassName: CacheUtil 
 * @Description: 缓存工具类
 * @author lisf 
 * @date 2016年10月11日 下午5:18:10 
 *  
 */
public class CacheUtil {
	public static CacheMap<String, Object> map = new CacheMap<String,Object>(10000,45*60*60);
	
	
	public static void put(String key,Object value){
		map.put(key, value);
	}
	
	public static Object get(String key){
		Object  object =  map.get(key);
		return object;
	}
	
	public static void remove(String key){
		 map.remove(key);
	}
}
