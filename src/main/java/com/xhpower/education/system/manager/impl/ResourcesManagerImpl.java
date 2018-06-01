package com.xhpower.education.system.manager.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xhpower.education.system.dao.ResourcesMapper;
import com.xhpower.education.system.entity.Resources;
import com.xhpower.education.system.manager.ResourcesManager;

/**
 * 
 * @ClassName: ResourcesManagerImpl
 * @Description: 上传资源服务实现类
 * @author Lian Youjie
 * @date 2017年8月30日 上午11:13:00
 *
 */
@Transactional
@Service
public class ResourcesManagerImpl extends ServiceImpl<ResourcesMapper, Resources> implements ResourcesManager {

    @Override
    public boolean insertResources(Resources resources) {
        if (!resources.getPath().isEmpty()) {
            if ((resources.getId() != null && resources.getId() == 0) && updateById(resources)) {
                return false;
            }
            resources.setCreateTime(new Date());
            return insert(resources);
        }
        return false;
    }

    /**
	 * 
	* @Title: queryImgs 
	* @Description: 学校图片从resource表回显
	* @param resourceStr
	* @return 
	* @author Liu YouCheng
	 */
	@Override
	public List<Resources> queryImgs(String resourceStr) {
		String[] resourceImg = resourceStr.split(",");
		List<Resources> imgList = new ArrayList<Resources>();
		for(int i=0;i<resourceImg.length;i++){
			Resources entity = this.selectById(resourceImg[i]);
			imgList.add(entity);
		}
		return imgList;
	}

}
