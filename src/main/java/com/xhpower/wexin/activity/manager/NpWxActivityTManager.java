package com.xhpower.wexin.activity.manager;

import com.baomidou.mybatisplus.service.IService;
import com.xhpower.wexin.activity.entity.NpWxActivityT;
import com.xhpower.wexin.article.entity.NpWxArticleT;

/**
 * <p>
 * 活动表 服务类
 * </p>
 *
 * @author Deng BinBin
 * @since 2017-10-18
 */
public interface NpWxActivityTManager extends IService<NpWxActivityT> {

	boolean insertOrUpdateActivity(NpWxActivityT npWxActivityT);
	
	NpWxActivityT selectNpWxActivityObject(Integer hid);
}
