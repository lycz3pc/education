package com.xhpower.wexin.jdclick.dao;

import com.xhpower.education.system.entity.Role;
import com.xhpower.wexin.jdclick.entity.NpClickSum;
import com.xhpower.wexin.jdclick.entity.NpWxClickRecordT;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  * 数据埋点记录表 Mapper 接口
 * </p>
 *
 * @author Song BoLin
 * @since 2017-10-19
 */
@Mapper
public interface NpWxClickRecordTMapper extends BaseMapper<NpWxClickRecordT> {
	 /**
		 * 
		* @Title: 统计每一天京东点击量 
		* @Description: 统计图
		* @param start 开始时间
		* @param end 结束时间
		* @return 
		* @author song bolin
		 */
	@Select("SELECT FROM_UNIXTIME(create_date,'%Y%m%d') days,count(code) sum FROM np_wx_click_record_t where create_date >= #{start} AND create_date <= #{end} GROUP BY days")
	public List<NpClickSum> list(@Param("start") Integer start,@Param("end")Integer end);

}