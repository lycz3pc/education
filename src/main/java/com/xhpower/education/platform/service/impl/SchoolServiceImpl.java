package com.xhpower.education.platform.service.impl;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xhpower.education.platform.dao.SchoolMapper;
import com.xhpower.education.platform.entity.SchoolColumnEntity;
import com.xhpower.education.platform.entity.SchoolEntity;
import com.xhpower.education.platform.service.SchoolColumnService;
import com.xhpower.education.platform.service.SchoolService;
import com.xhpower.education.system.dao.ResourcesMapper;
import com.xhpower.education.system.entity.Resources;
import com.xhpower.education.utils.R;
import com.xhpower.education.utils.UploadUtil;
import com.xhpower.wexin.menu.manager.NpWxMenuTManager;
import com.xhpower.wexin.menu.manager.impl.NpWxMenuTManagerImpl;
import com.xhpower.wexin.utils.CallWebService;

@Service
@Transactional
public class SchoolServiceImpl extends ServiceImpl<SchoolMapper, SchoolEntity> implements SchoolService {
	
	@Autowired
	private SchoolMapper schoolMapper;
	
	@Autowired
    private SchoolColumnService schoolColumnService;
	
	@Autowired
	private ResourcesMapper resourcesMapper;
	
	@Autowired
	private NpWxMenuTManagerImpl npWxMenuTManagerImpl;
	
	@Override
	public String getQrCode(HttpServletRequest request,JSONObject jsonObject) {
		String token = this.npWxMenuTManagerImpl.findAccessToken(request);
		String qrucode_url = "";
		System.out.println("token*********************"+token);
		if(StringUtils.isNotEmpty(token)){
			String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+token;
			JSONObject post_json = new JSONObject();
			post_json.put("action_name", "QR_LIMIT_STR_SCENE");
			JSONObject action_info = new JSONObject();
			JSONObject scene = new JSONObject();
			scene.put("scene_str", jsonObject.getString("userId"));
			action_info.put("scene", scene);
			post_json.put("action_info", action_info);
			
			String result = CallWebService.httpPost(url, post_json.toJSONString());
			System.out.println("result*********************"+result);
			if(StringUtils.isNotEmpty(result)){
				JSONObject result_json = JSONObject.parseObject(result);
				qrucode_url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+URLEncoder.encode(result_json.getString("ticket"));
			}
		}
		System.out.println("qrucode_url*********************"+qrucode_url);
		return qrucode_url;
	}
	
	@Override
	public Boolean save(SchoolEntity school, MultipartFile[] files, HttpServletRequest request) {
		if(school.getId() == null){
			school.setCreateTime(new Date());
			String filePath = "/upload/school";
			List<String> stringList=new ArrayList<String>();
			for(int i=0;i<files.length;i++){
				if(!files[i].isEmpty()){
					String filePathUrl = UploadUtil.uploadFile(filePath, request, files[i]);
					if(StringUtils.isNotEmpty(filePathUrl)){
						Resources entity = new Resources();
						entity.setPath(filePathUrl);
						entity.setName(filePathUrl.substring(filePathUrl.lastIndexOf(".")+1));
						entity.setCreateTime(new Date());
						resourcesMapper.insert(entity);
						stringList.add(String.valueOf(entity.getId()));
					}
				}
			}
			school.setIsTop(0);  //新增默认设置为 0：不置顶
			school.setResourceStr(StringUtils.join(stringList.toArray(), ","));
			
			/**
			 * 生成校区二维码
			 */
			JSONObject jsonObject = new JSONObject();
			//2代表通过校区二维码关注
			jsonObject.put("userId", school.getId()+"_2");
			String qrCode = getQrCode(request,jsonObject);
			if(StringUtils.isNoneEmpty(qrCode)){
				school.setQrCodeUrl(qrCode);
			}
			return this.insert(school);
			
		}else{
			return this.update(school, files,request);
		}
		
	}

	/* (non-Javadoc)
	 * @see com.xhpower.education.platform.service.SchoolService#update(com.xhpower.education.platform.entity.SchoolEntity, org.springframework.web.multipart.MultipartFile[], javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public Boolean update(SchoolEntity school, MultipartFile[] files, HttpServletRequest request) {
		school.setUpdateTime(new Date());
		String filePath = "/upload/school";
		String [] arr = school.getResourceStr().split(",");
		if(files!=null){
			for(int i=0;i<files.length;i++){
				if(!files[i].isEmpty()){
					String filePathUrl = UploadUtil.uploadFile(filePath, request, files[i]);
					Resources entity = new Resources();
					entity.setPath(filePathUrl);
					entity.setName(filePathUrl.substring(filePathUrl.lastIndexOf(".")+1));
					entity.setCreateTime(new Date());
					resourcesMapper.insert(entity);
					String [] temp = new String[arr.length];
					System.arraycopy(arr, 0, temp, 0, arr.length);
					temp[i] = String.valueOf(entity.getId());
					arr = temp;
				}
				//String resourceStr = StringUtils.join(arr, ",");
				school.setResourceStr(StringUtils.join(arr, ","));
			}
		}
		return this.updateById(school);
	}
	
	@Override
	public Page<SchoolEntity> list(Page<SchoolEntity> pages, SchoolEntity school) {
		EntityWrapper<SchoolEntity> wrapper = new EntityWrapper<SchoolEntity>();
		if(null != school){
			if(!StringUtils.isEmpty(school.getSchName())){
				wrapper.like("schName", school.getSchName());
			}
			if(!StringUtils.isEmpty(school.getProvince())){
				wrapper.like("province", school.getProvince());
			}
			if(!StringUtils.isEmpty(school.getCity())){
				wrapper.like("city", school.getCity());
			}
			if(!StringUtils.isEmpty(school.getArea())){
				wrapper.like("area", school.getArea());
			}
			//是否置顶
			if(null != school.getIsTop()){
				wrapper.orderBy("isTop", false);
				wrapper.orderBy("updateTime", false);
			}
		}
		wrapper.orderBy("createTime", false);
		pages = selectPage(pages, wrapper);
		
		for (SchoolEntity entity : pages.getRecords()) {
		    EntityWrapper<SchoolColumnEntity> entityWrapper = new EntityWrapper<>();
		    entityWrapper.eq("school_id", entity.getId());
		    entity.setStationStatus(false);
		    if(schoolColumnService.selectCount(entityWrapper) > 0)
		        entity.setStationStatus(true);
        }
		
		return pages;
	}

	@Override
	public SchoolEntity isTop(Integer id) {
		SchoolEntity entity = schoolMapper.selectById(id);
		if(entity.getIsTop().equals(0)){
			entity.setIsTop(1);   //置顶
		}else{
			entity.setIsTop(0);   //取消置顶
		}
		return entity;
	}

	/**
    * 保存信息
    * 
    * @author Liu Youcheng
    * @date 2017年10月11日 下午4:42:37
    * @param response
    * @param files
    */
	@Override
	public boolean saveJoinUnion(SchoolEntity school, MultipartFile[] files, HttpServletRequest request) {
		school.setCreateTime(new Date());
		school.setIsTop(0);
		String filePath = "/upload/school";
		List<String> stringList=new ArrayList<String>();
		for(int i=0;i<files.length;i++){
			if(!files[i].isEmpty()){
				String filePathUrl = UploadUtil.uploadFile(filePath, request, files[i]);
				Resources entity = new Resources();
				entity.setPath(filePathUrl);
				entity.setName(filePathUrl.substring(filePathUrl.lastIndexOf(".")+1));
				entity.setCreateTime(new Date());
				resourcesMapper.insert(entity);
				stringList.add(String.valueOf(entity.getId()));
			}
		}
		school.setResourceStr(StringUtils.join(stringList.toArray(), ","));
		return this.insert(school);
	}

	@Override
	public R check(SchoolEntity school) {
		school.setStatus(1);
		Boolean bool = this.updateById(school);
		
		return  bool?R.success():R.error();
	}
	
}
