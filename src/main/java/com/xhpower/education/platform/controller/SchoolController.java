package com.xhpower.education.platform.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xhpower.education.annotation.SysLog;
import com.xhpower.education.platform.entity.SchoolColumn;
import com.xhpower.education.platform.entity.SchoolEntity;
import com.xhpower.education.platform.service.SchoolColumnService;
import com.xhpower.education.platform.service.SchoolService;
import com.xhpower.education.system.entity.Resources;
import com.xhpower.education.system.manager.ResourcesManager;
import com.xhpower.education.utils.R;

/**
 *@ClassName: SchoolController 
 *@Description: 联盟校园控制类 
 *@author liuyoucheng
 */

@RestController
@RequestMapping("/platform/school")
public class SchoolController {
	
	@Autowired
	private SchoolService schoolService;
	
	@Autowired
	private ResourcesManager resourcesManager;
	
	@Autowired
	private SchoolColumnService schoolColumnService;

	
	/**
	 * @Title: list 
	 * @Description: 联盟校园列表
	 * @param school
	 * @param page
	 * @param rows
	 * @return
	 * @author liuyoucheng
	 */
	@RequestMapping(value="/schoolList",method=RequestMethod.POST)
    public R list(SchoolEntity school,Integer page,Integer rows){
		Page<SchoolEntity> pages = new Page<SchoolEntity>(page, rows);
		pages = schoolService.list(pages,school);
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
	public R isTop(Integer id){
		SchoolEntity entity = schoolService.isTop(id);
		return schoolService.updateById(entity)?R.success():R.error();
	}
	
	/**
	 * 
	* @Title: save 
	* @Description: 新增或修改联盟校园
	* @param school
	* @param files
	* @param request
	* @return 
	* @author Liu YouCheng
	 */
	@RequestMapping(value="/save",method=RequestMethod.POST)
	@SysLog("保存联盟校园")
    public R save(SchoolEntity school,MultipartFile[] files,HttpServletRequest request){
		 return schoolService.save(school, files,request)?R.success():R.error();
    }
	
	/**
	 * 
	* @Title: 审核 
	* @Description: 学校审核
	* @param school
	* @return 
	* @author Liu YouCheng
	 */
	@RequestMapping(value="/check",method=RequestMethod.POST)
	@SysLog("审核专家")
    public R check(SchoolEntity school){
		/*ExpertEntity entity = expertService.selectById(expert.getId());
		entity.setStatus(1);
		return  expertService.updateById(entity)?R.success():R.error();*/
		return schoolService.check(school);
    }
	
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@SysLog("删除联盟校园")
    public R delete(Integer[] ids){
		 return  schoolService.deleteBatchIds(Arrays.asList(ids))?R.success():R.error();
    }
	
	/**
	 * 生成二维码
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/genderQrcode",method=RequestMethod.POST)
    public R genderQrcode(HttpServletRequest request,Integer id){
		 JSONObject jsonObject = new JSONObject();
		 jsonObject.put("userId",id+"_2");
		 String qrcode = schoolService.getQrCode(request,jsonObject);
		 if(StringUtils.isNotEmpty(qrcode)){
			 SchoolEntity schoolEntity = new SchoolEntity();
			 schoolEntity.setId(id);
			 schoolEntity.setQrCodeUrl(qrcode);
			 this.schoolService.updateById(schoolEntity);
		 }
		 System.out.println("qrcode*********************"+qrcode);
		 return  StringUtils.isNotEmpty(qrcode)?R.success().put("qrcode",qrcode):R.error();
    }
	
	/**
	 * 
	* @Title: queryImgs 
	* @Description: 文件回显
	* @param resourceStr
	* @return 
	* @author Liu YouCheng
	 */
	@RequestMapping(value="/queryImgs",method=RequestMethod.POST)
    public R queryImgs(String resourceStr){
		List<Resources> imgList = resourcesManager.queryImgs(resourceStr);
		return R.success().put("imgList", imgList);
    }

    /**
     * 
     * @Title: createStation
     * @Description: 生成子站栏目--保存所选子站栏目列表
     * @param schoolColumn
     * @author Lian Youjie
     * @return R 返回类型
     */
    @RequestMapping("/createStation")
    public R createStation(@RequestBody SchoolColumn schoolColumn) {
        return schoolColumnService.createStation(schoolColumn.getSchoolColumns()) ? R.success() : R.error();
    }
    
    /**
     * 保存信息
     * 
     * @author Liu Youcheng
     * @date 2017年10月11日 下午4:42:37
     * @param response
     * @param files
     */
    @RequestMapping(value="/saveJoinUnion",method=RequestMethod.POST)
    public void saveJoinUnion(SchoolEntity school,MultipartFile[] files,HttpServletRequest request,HttpServletResponse response){
       schoolService.saveJoinUnion(school, files,request);
       response.setContentType("text/html;charset=utf-8");	   
       response.setHeader("Access-Control-Allow-Origin","*");
       response.setHeader("Access-Control-Allow-Methods","POST,GET,OPTIONS,DELETE");
       response.setHeader("Access-Control-Max-Age","3600");
       response.setHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept");
       response.setHeader("","");
       response.setHeader("","");
  	   String json=JSON.toJSONString(R.success());
  	   ServletOutputStream out = null;
  	   try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			InputStream is = new ByteArrayInputStream(json.getBytes("UTF-8"));
			out = response.getOutputStream();
			byte b[] = new byte[1024];
			for (int i = 0; (i = is.read(b, 0, 1024)) > 0;) {
				os.write(b, 0, i);
			}
			byte req[] = os.toByteArray();
			out.write(req);
			response.flushBuffer();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null)
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
  	   return;
     }
    
    /**
  	 * 
  	* @Title: checkUnitSitPhone 
  	* @Description: 验证单位座机号是否存在
  	* @param resourceStr
  	* @return 
  	* @author Liu YouCheng
  	 */
      @RequestMapping(value="/checkUnitSitPhone",method=RequestMethod.POST)
      public Boolean checkUnitSitPhone(@RequestBody SchoolEntity school) {
          if(school.getId() != null){
          	EntityWrapper<SchoolEntity> wrapper = new EntityWrapper<SchoolEntity>();
          	wrapper.ne("id", school.getId());
            wrapper.eq("unit_sitphone", school.getUnitSitPhone());
            return schoolService.selectCount(wrapper) == 0;
          }
          EntityWrapper<SchoolEntity> wrapper2 = new EntityWrapper<SchoolEntity>();
          wrapper2.eq("unit_sitphone", school.getUnitSitPhone());
          return schoolService.selectCount(wrapper2) == 0;
     }
     
     /**
      * 
      * @Title: checkUnitTelPhone 
	  * @Description: 验证单位手机号是否存在
      * @param resourceStr
      * @return 
      * @author Liu YouCheng
     */
      @RequestMapping(value="/checkUnitTelPhone",method=RequestMethod.POST)
      public Boolean checkUnitTelPhone(@RequestBody SchoolEntity school) {
          if(school.getId() != null){
            EntityWrapper<SchoolEntity> wrapper = new EntityWrapper<SchoolEntity>();
            wrapper.ne("id", school.getId());
            wrapper.eq("unit_telphone", school.getUnitTelPhone());
            return schoolService.selectCount(wrapper) == 0;
           }
           EntityWrapper<SchoolEntity> wrapper2 = new EntityWrapper<SchoolEntity>();
           wrapper2.eq("unit_telphone", school.getUnitTelPhone());
           return schoolService.selectCount(wrapper2) == 0;
      }
     
      /**
    	 * 
    	* @Title: checkPreSitPho 
    	* @Description: 验证校长座机号是否存在
    	* @param resourceStr
    	* @return 
    	* @author Liu YouCheng
    	 */
        @RequestMapping(value="/checkPreSitPho",method=RequestMethod.POST)
        public Boolean checkPreSitPho(@RequestBody SchoolEntity school) {
            if(school.getId() != null){
            	EntityWrapper<SchoolEntity> wrapper = new EntityWrapper<SchoolEntity>();
            	wrapper.ne("id", school.getId());
                wrapper.eq("pre_sit_pho", school.getPreSitPho());
                return schoolService.selectCount(wrapper) == 0;
            }
            EntityWrapper<SchoolEntity> wrapper2 = new EntityWrapper<SchoolEntity>();
            wrapper2.eq("pre_sit_pho", school.getPreSitPho());
            return schoolService.selectCount(wrapper2) == 0;
       }
      
       /**
        * 
        * @Title: checkPrePhone 
   	    * @Description: 验证校长手机号是否存在
        * @param resourceStr
        * @return 
        * @author Liu YouCheng
       */
       @RequestMapping(value="/checkPrePhone",method=RequestMethod.POST)
       public Boolean checkPrePhone(@RequestBody SchoolEntity school) {
             if(school.getId() != null){
               EntityWrapper<SchoolEntity> wrapper = new EntityWrapper<SchoolEntity>();
               wrapper.ne("id", school.getId());
               wrapper.eq("pre_phone", school.getPrePhone());
               return schoolService.selectCount(wrapper) == 0;
             }
          EntityWrapper<SchoolEntity> wrapper2 = new EntityWrapper<SchoolEntity>();
          wrapper2.eq("pre_phone", school.getPrePhone());
          return schoolService.selectCount(wrapper2) == 0;
       }
       
       /**
   	   * 
   	   * @Title: checkInfreceiSitP 
   	   * @Description: 验证校长座机号是否存在
       * @param resourceStr
       * @return 
   	   * @author Liu YouCheng
   	   */
       @RequestMapping(value="/checkInfreceiSitP",method=RequestMethod.POST)
       public Boolean checkInfreceiSitP(@RequestBody SchoolEntity school) {
           if(school.getId() != null){
           	EntityWrapper<SchoolEntity> wrapper = new EntityWrapper<SchoolEntity>();
           	   wrapper.ne("id", school.getId());
               wrapper.eq("inf_recei_sitp", school.getInfreceiSitP());
               return schoolService.selectCount(wrapper) == 0;
           }
           EntityWrapper<SchoolEntity> wrapper2 = new EntityWrapper<SchoolEntity>();
           wrapper2.eq("inf_recei_sitp", school.getInfreceiSitP());
           return schoolService.selectCount(wrapper2) == 0;
      }
      
       /**
        * 
        * @Title: checkPrePhone 
   	    * @Description: 验证信息对接任人电话是否存在
        * @param resourceStr
        * @return 
        * @author Liu YouCheng
       */
       @RequestMapping(value="/checkInfreceiphone",method=RequestMethod.POST)
       public Boolean checkInfreceiphone(@RequestBody SchoolEntity school) {
             if(school.getId() != null){
               EntityWrapper<SchoolEntity> wrapper = new EntityWrapper<SchoolEntity>();
               wrapper.ne("id", school.getId());
               wrapper.eq("inf_recei_phone", school.getInfreceiphone());
               return schoolService.selectCount(wrapper) == 0;
             }
          EntityWrapper<SchoolEntity> wrapper2 = new EntityWrapper<SchoolEntity>();
          wrapper2.eq("inf_recei_phone", school.getInfreceiphone());
          return schoolService.selectCount(wrapper2) == 0;
       }
}
