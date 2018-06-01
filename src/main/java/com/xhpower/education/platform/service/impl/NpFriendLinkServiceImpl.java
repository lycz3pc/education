package com.xhpower.education.platform.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xhpower.education.platform.dao.NpFriendLinkMapper;
import com.xhpower.education.platform.entity.NpFriendLink;
import com.xhpower.education.platform.service.NpFriendLinkService;

/**
 * <p>
 * 友情链接表 服务实现类
 * </p>
 *
 * @author Lian YouJie
 * @since 2017-10-23
 */
@Service
@Transactional
public class NpFriendLinkServiceImpl extends ServiceImpl<NpFriendLinkMapper, NpFriendLink>
        implements NpFriendLinkService {

}
