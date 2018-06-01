package com.xhpower.wexin.jdclick.manager.impl;

import com.xhpower.wexin.jdclick.entity.NpClickSum;
import com.xhpower.wexin.jdclick.entity.NpWxClickRecordT;
import com.xhpower.wexin.jdclick.dao.NpWxClickRecordTMapper;
import com.xhpower.wexin.jdclick.manager.NpWxClickRecordTManager;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 数据埋点记录表 服务实现类
 * </p>
 *
 * @author Song BoLin
 * @since 2017-10-19
 */
@Service
public class NpWxClickRecordTManagerImpl extends ServiceImpl<NpWxClickRecordTMapper, NpWxClickRecordT> implements NpWxClickRecordTManager {
	
	@Autowired
	private NpWxClickRecordTMapper npWxClickRecordTMapper;
	 /**
		 * 
		* @Title: 统计每一天京东点击量 
		* @Description: 统计图
		* @param start 开始时间
		* @param end 结束时间
		* @return 
		* @author song bolin
		 */
	
	@Override
	public List<NpClickSum> list(Integer start,Integer end) {
		
		return npWxClickRecordTMapper.list(start, end);
	}
	
}
