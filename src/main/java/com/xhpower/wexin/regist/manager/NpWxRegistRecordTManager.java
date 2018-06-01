package com.xhpower.wexin.regist.manager;

import com.xhpower.wexin.regist.entity.MonthRegist;
import com.xhpower.wexin.regist.entity.NpCampusRegist;
import com.xhpower.wexin.regist.entity.NpRegistCount;
import com.xhpower.wexin.regist.entity.NpWxRegistRecordT;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 通过校区二维码注册记录 服务类
 * </p>
 *
 * @author Song BoLin
 * @since 2017-10-23
 */
public interface NpWxRegistRecordTManager extends IService<NpWxRegistRecordT> {
	public List<NpRegistCount> dayRegistList( Integer start,Integer end);
	public Integer countAllRegist();
	public  List<MonthRegist> countOneMonthRegist(Integer month);
	List<NpCampusRegist> campusRegistList( Integer start,Integer end);
	
}
