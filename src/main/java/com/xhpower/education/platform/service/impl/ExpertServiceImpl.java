package com.xhpower.education.platform.service.impl;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xhpower.education.platform.dao.ExpertEntityMapper;
import com.xhpower.education.platform.dao.UserInfoEntityMapper;
import com.xhpower.education.platform.entity.ExpertEntity;
import com.xhpower.education.platform.entity.UserInfoEntity;
import com.xhpower.education.platform.service.ExpertService;
import com.xhpower.education.system.dao.ResourcesMapper;
import com.xhpower.education.system.entity.Resources;
import com.xhpower.education.utils.R;
import com.xhpower.education.utils.UploadUtil;
import com.xhpower.plugins.common.security.MD5;

@Service
@Transactional
public class ExpertServiceImpl extends ServiceImpl<ExpertEntityMapper, ExpertEntity> implements ExpertService {

    public Logger log = Logger.getLogger(ExpertServiceImpl.class);

    @Autowired
    private ExpertEntityMapper expertEntityMapper;

    @Autowired
    private ResourcesMapper resourcesMapper;

    @Autowired
    private UserInfoEntityMapper userInfoMapper;

    @Override
    public Page<ExpertEntity> list(Page<ExpertEntity> pages, ExpertEntity expert) {
        EntityWrapper<ExpertEntity> wrapper = new EntityWrapper<ExpertEntity>();
        if (null != expert) {
            if (null != expert.getName() && !"".equals(expert.getName())) {
                wrapper.like("name", expert.getName());
            }
            // 是否置顶
            if (null != expert.getIsTop()) {
                // wrapper.eq("isTop", expert.getIsTop());
                wrapper.orderBy("is_top", false);
                wrapper.orderBy("update_time", false);
            }
        }
        wrapper.eq("logic_status", 1);
        wrapper.orderBy("create_time", false);
        pages = this.selectPage(pages, wrapper);
        return pages;
    }

    @Override
    public ExpertEntity isTop(Integer id) {
        ExpertEntity entity = expertEntityMapper.selectById(id);
        if (entity.getIsTop().equals(0)) {
            entity.setIsTop(1); // 置顶
        } else {
            entity.setIsTop(0); // 取消置顶
        }
        return entity;
    }

    @Override
    public boolean save(ExpertEntity expert, MultipartFile file, HttpServletRequest request) {
        String filePath = "/upload/expert";
        if (!file.isEmpty()) {
            String filePathUrl = UploadUtil.uploadFile(filePath, request, file);
            Resources entity = new Resources();
            entity.setPath(filePathUrl);
            entity.setName(filePathUrl.substring(filePathUrl.lastIndexOf(".") + 1));
            entity.setCreateTime(new Date());
            resourcesMapper.insert(entity);
            expert.setExpertImg(String.valueOf(entity.getId()));
        }

        boolean res = false;
        try {
            // 修改
            if (expert.getId() != null) {
                // String salt = UUID.randomUUID().toString();
                ExpertEntity expertEntity = expertEntityMapper.selectById(expert.getId());
                /*
                 * if(expert.getUserId() != null && expert.getUserId()!=0){
                 * UserInfoEntity entity =
                 * userInfoMapper.selectById(expert.getUserId());
                 * entity.setLogin_email(expert.getEmail());
                 * entity.setLogin_phone(expert.getTelephone());
                 * entity.setReal_name(expert.getName());
                 * if(!expertEntity.getPassword().equals(expert.getPassword())){
                 * entity.setPasswd(MD5.encrypt(expert.getPassword() + "{"
                 * +salt+"}")); entity.setPasswd_salt(salt);
                 * 
                 * } if(expert.getStatus().equals(1)){
                 * userInfoMapper.updateById(entity); } }
                 */
                expert.setPassword(expertEntity.getPassword());
                expert.setSynopsis(expertEntity.getSynopsis());
                expert.setChargeRate(Double.parseDouble(expert.getChargeRate()) / 100 + "");
                // 修改专家表
                // expert.setPassword(newPassword);
                expert.setUpdatetime(new Date());
                res = this.updateById(expert);
            } else {
                // 新增
                UserInfoEntity entity = new UserInfoEntity();
                entity.setLogin_email(expert.getEmail());
                entity.setLogin_phone(expert.getTelephone());
                entity.setReal_name(expert.getName());
                String salt = UUID.randomUUID().toString();
                entity.setPasswd(MD5.encrypt(expert.getPassword() + "{" + salt + "}"));
                entity.setPasswd_salt(salt);
                entity.setIs_locked(false);
                entity.setIs_deleted(false);
                // 用户已审核后插入贝壳网用户表
                if (expert.getStatus().equals(1)) {
                    userInfoMapper.insert(entity);
                }
                entity.setCreate_time(new Date());

                // md5加密
                expert.setPassword(entity.getPasswd());
                expert.setChargeRate(Double.parseDouble(expert.getChargeRate()) / 100 + "");
                // 盐
                expert.setSynopsis(entity.getPasswd_salt());
                expert.setIsTop(0);
                expert.setAttention(0);
                expert.setUserId(entity.getUser_id());
                expert.setLogicStatus(1);
                expert.setCreatetime(new Date());
                res = this.insert(expert);
            }
        } catch (Exception e) {
            log.error("专家注册异常");
            e.printStackTrace();
        }

        return res;
    }

    @Override
    public R updateImg(ExpertEntity expert, MultipartFile file, HttpServletRequest request) {
        Boolean bool = false;
        String filePathUrl = "";
        String filePath = "/upload/expert";
        if (!file.isEmpty()) {
            filePathUrl = UploadUtil.uploadFile(filePath, request, file);
            Resources entity = new Resources();
            entity.setPath(filePathUrl);
            entity.setName(filePathUrl.substring(filePathUrl.lastIndexOf(".") + 1));
            entity.setCreateTime(new Date());
            resourcesMapper.insert(entity);
            expert.setExpertImg(String.valueOf(entity.getId()));
        }

        if (expert.getId() != null) {
            expert.setUpdatetime(new Date());
            bool = this.updateById(expert);
        }

        return bool ? R.success().put("path", filePathUrl) : R.error();
    }

    /*
     * public boolean validateSort(ExpertEntity expert) {
     * EntityWrapper<ExpertEntity> wrapper = new EntityWrapper<ExpertEntity>();
     * if(null != expert.getId()){ ExpertEntity entity =
     * this.selectById(expert.getId());
     * if(expert.getSort().equals(entity.getSort())){ return false; }else{
     * expert.setId(null); } } wrapper.setEntity(expert); return
     * this.selectCount(wrapper)>0?false:true; }
     */

    @Override
    public R check(ExpertEntity expert) {

        expert = this.selectById(expert.getId());

        UserInfoEntity entity = new UserInfoEntity();
        entity.setLogin_email(expert.getEmail());
        entity.setLogin_phone(expert.getTelephone());
        entity.setReal_name(expert.getName());
        entity.setPasswd(expert.getPassword());
        entity.setPasswd_salt(expert.getSynopsis());
        entity.setIs_locked(false);
        entity.setIs_deleted(false);
        entity.setCreate_time(new Date());
        // 用户已审核后插入贝壳网用户表
        userInfoMapper.insert(entity);

        expert.setStatus(1);
        expert.setUserId(entity.getUser_id());
        Boolean bool = this.updateById(expert);

        return bool ? R.success() : R.error();
    }

}
