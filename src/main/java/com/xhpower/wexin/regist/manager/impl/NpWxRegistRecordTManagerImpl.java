package com.xhpower.wexin.regist.manager.impl;

import com.xhpower.wexin.regist.entity.MonthRegist;
import com.xhpower.wexin.regist.entity.NpCampusRegist;
import com.xhpower.wexin.regist.entity.NpRegistCount;
import com.xhpower.wexin.regist.entity.NpWxRegistRecordT;
import com.xhpower.wexin.regist.dao.NpWxRegistRecordTMapper;
import com.xhpower.wexin.regist.manager.NpWxRegistRecordTManager;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 通过校区二维码注册记录 服务实现类
 * </p>
 *
 * @author Song BoLin
 * @since 2017-10-23
 */
@Service
public class NpWxRegistRecordTManagerImpl extends ServiceImpl<NpWxRegistRecordTMapper, NpWxRegistRecordT> implements NpWxRegistRecordTManager {
    @Autowired
    private NpWxRegistRecordTMapper npWxRegistRecordTMapper;
	@Override
	public List<NpRegistCount> dayRegistList(Integer start, Integer end) {
		return npWxRegistRecordTMapper.dayRegistList(start, end);
	}
	@Override
	public Integer countAllRegist() {
		return npWxRegistRecordTMapper.countAllRegist();
	}
	@Override
	public List<MonthRegist> countOneMonthRegist(Integer month) {
		return npWxRegistRecordTMapper.countOneMonthRegist(month);
	}
	@Override
	public List<NpCampusRegist> campusRegistList(Integer start, Integer end) {
		return npWxRegistRecordTMapper.campusRegistList(start, end);
	}
	
	
	
}
