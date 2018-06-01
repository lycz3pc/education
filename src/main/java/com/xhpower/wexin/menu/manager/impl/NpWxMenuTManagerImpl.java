package com.xhpower.wexin.menu.manager.impl;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xhpower.wexin.menu.dao.NpWxMenuTMapper;
import com.xhpower.wexin.menu.entity.NpWxMenuT;
import com.xhpower.wexin.menu.manager.NpWxMenuTManager;
import com.xhpower.wexin.utils.CallWebService;
import com.xhpower.wexin.utils.PublicMethod;

/**
 * <p>
 * 
 * </p>
 *
 * @author Deng BinBin
 * @since 2017-10-18
 */
@Service
@Transactional
public class NpWxMenuTManagerImpl extends ServiceImpl<NpWxMenuTMapper, NpWxMenuT>  implements NpWxMenuTManager {

	@Autowired
	private NpWxMenuTMapper npWxMenuTMapper;
	
	@Override
	public JSONObject findMenuList(HttpServletRequest request) {
		String token = findAccessToken(request);
		if(StringUtils.isNotEmpty(token)){
			String str_json = CallWebService.httpGet("https://api.weixin.qq.com/cgi-bin/menu/get?access_token="+token);
			if(StringUtils.isNotEmpty(str_json)){
				return JSONObject.parseObject(str_json);
			}
		}
		return null;
	}
	
	@Override 
	public boolean genderWxMenu(HttpServletRequest request) {
		 //获取token
		 String token = findAccessToken(request);
		 System.out.println("----------------------"+token);
		 if(StringUtils.isNotEmpty(token)){
			 boolean result_del = removeMenu(token);
			 JSONObject reslut_json = new JSONObject();
			 if(result_del){
				 System.out.println("----------------------"+result_del);
				 EntityWrapper<NpWxMenuT> wrapper = new EntityWrapper<>();
				 wrapper.eq("level", 1);
				 List<NpWxMenuT> npWxMenuTs =this.npWxMenuTMapper.selectList(wrapper);
				 if(npWxMenuTs!=null && npWxMenuTs.size()>0){
					List<JSONObject> button_jsons=new ArrayList<>();
					
					for (NpWxMenuT npWxMenuT : npWxMenuTs) {
						JSONObject button = new JSONObject();
						
						wrapper = new EntityWrapper<>();
						wrapper.eq("level", 2);
						wrapper.eq("parent_id", npWxMenuT.getId());
						npWxMenuTs = this.npWxMenuTMapper.selectList(wrapper);
						
						button.put("name", npWxMenuT.getName());
						if(npWxMenuTs!=null && npWxMenuTs.size()>0){
							List<JSONObject> sub_button = new ArrayList<>();
							for (NpWxMenuT npWxMenu : npWxMenuTs) {
								JSONObject two_button = new JSONObject(); 
								two_button.put("type", npWxMenu.getType());
								two_button.put("name", npWxMenu.getName());
								if("view".equals(npWxMenu.getType())){
									two_button.put("url", npWxMenu.getUrl());
								}else{
									two_button.put("key", npWxMenu.getId());
								}
								
								sub_button.add(two_button);
							}
							button.put("sub_button", sub_button);
						}else{
							button.put("type", npWxMenuT.getType());
							if("view".equals(npWxMenuT.getType())){
								button.put("url", npWxMenuT.getUrl());
							}else{
								button.put("key", npWxMenuT.getId());
							}
						}
						button_jsons.add(button);
						reslut_json.put("button", button_jsons);
					}
				 }
				 System.out.println("result_json:---------------------"+reslut_json.toJSONString());
				 String gender_json = CallWebService.httpPost("https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+token,reslut_json.toJSONString());
				 System.out.println("-------------------"+gender_json);
				 if(StringUtils.isNotEmpty(gender_json)){
					 JSONObject jsonObject = JSONObject.parseObject(gender_json);
					 if("ok".equals(jsonObject.getString("errmsg"))){
							return true;
					 }
				 }
			 }
		 }
		 return false;
	}
	
	public boolean removeMenu(String token){
		String str_json = CallWebService.httpGet("https://api.weixin.qq.com/cgi-bin/menu/delete?access_token="+token);
		if(StringUtils.isNotEmpty(str_json)){
			JSONObject jsonObject = JSONObject.parseObject(str_json);
			if("ok".equals(jsonObject.getString("errmsg"))){
				return true;
			}
		}
		return false;
	}
	
	public String findAccessToken(HttpServletRequest request){
		Object token = request.getSession().getAttribute("token");
		if(token!=null){
			return token.toString();
		}else{
			String str_json = CallWebService.httpGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+PublicMethod.getProperties("APP_ID")+"&secret="+PublicMethod.getProperties("APP_SECRET_ID"));
			if(StringUtils.isNotEmpty(str_json)){
				JSONObject jsonObject = JSONObject.parseObject(str_json);
				if(jsonObject!=null && StringUtils.isNotEmpty(jsonObject.getString("access_token"))){
					request.getSession().setAttribute("token",jsonObject.getString("access_token"));
					return jsonObject.getString("access_token");
				}
			}
			return "";
		}
	}

	
}
