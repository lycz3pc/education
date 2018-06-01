package com.xhpower.education.utils;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;

/**
 * 
* @ClassName: R 
* @Description: 返回数据
* @author lisf 
* @date 2017年3月31日 下午7:57:06 
*
 */
public class R extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 类型
	 */
	public enum Type {

		/** 成功 */
		success,

		/** 警告 */
		warn,

		/** 错误 */
		error
	}
	
	public R() {
		put("code", 0);
		put("time",System.currentTimeMillis());
	}
	
	public static R error() {
		return error(500, "未知异常，请联系管理员");
	}
	
	public static R error(String msg) {
		return error(500, msg);
	}
	
	public static R error(int code, String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		r.put("type", Type.error);
		return r;
	}

	public static R success(String msg) {
		R r = new R();
		r.put("msg", msg);
		r.put("type", Type.success);
		return r;
	}
	public static R success(int code,String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		r.put("type", Type.success);
		return r;
	}
	public static R success(Map<String, Object> map) {
		R r = new R();
		r.putAll(map);
		return r;
	}
	
	public static R success() {
		R r = new R();
		r.put("type", Type.success);
		return r;
	}

	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}
	
	public R page(Page<?> page) {
		Map<String, Object> map =  new HashMap<>();
		map.put("total", page.getTotal());
		map.put("size", page.getSize());
		map.put("current", page.getCurrent());
		map.put("pages", page.getPages());
		map.put("records",page.getRecords());
		super.put("page", map);
		//super.putAll(map);
		return this;
	}
	
	public R list(List<?> list) {
		super.put("list", list);
		//super.putAll(map);
		return this;
	}
}
