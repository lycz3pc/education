package com.xhpower.education.system.manager;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xhpower.education.system.entity.Resources;

/**
 * 
 * @ClassName: ResourcesManager
 * @Description: 上传资源服务接口类
 * @author Lian Youjie
 * @date 2017年8月30日 上午11:10:53
 *
 */
public interface ResourcesManager extends IService<Resources> {

    boolean insertResources(Resources resources);

	List<Resources> queryImgs(String resourceStr);

}
