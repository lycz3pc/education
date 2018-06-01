package com.xhpower.wexin.regist.dao;

import com.xhpower.wexin.regist.entity.MonthRegist;
import com.xhpower.wexin.regist.entity.NpCampusRegist;
import com.xhpower.wexin.regist.entity.NpRegistCount;
import com.xhpower.wexin.regist.entity.NpWxRegistRecordT;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  * 通过校区二维码注册记录 Mapper 接口
 * </p>
 *
 * @author Song BoLin
 * @since 2017-10-23
 */
public interface NpWxRegistRecordTMapper extends BaseMapper<NpWxRegistRecordT> {

	 /**
	 * 
	* @Title: 统计每一天的注册量
	* @Description: 统计图
	* @param start 开始时间
	* @param end 结束时间
	* @return 某时间段每一天的注册量
	* @author song bolin
	 */
@Select("SELECT FROM_UNIXTIME(create_date,'%Y%m%d') days,count(regist_user_id) count FROM np_wx_regist_record_t where create_date>=#{start} AND create_date<=#{end} GROUP BY days")
public List<NpRegistCount> dayRegistList(@Param("start") Integer start,@Param("end")Integer end);


/**
	 * 
	* @Title: 统计历史总注册量注册量
	* @Description: 统计图
	* @return 历史总注册量
	* @author song bolin
	 */
@Select("SELECT COUNT(regist_user_id) count FROM np_wx_regist_record_t")
public Integer countAllRegist();


/**
	 * 
	* @Title: 统计当前月的注册量
	* @Description: 统计图
	* @param month 当前月份
	* @return 当前月的注册量
	* @author song bolin
	 */
@Select("SELECT FROM_UNIXTIME(create_date,'%Y%m') months,COUNT(regist_user_id) COUNT FROM np_wx_regist_record_t where FROM_UNIXTIME(create_date,'%m')=#{month} GROUP BY months")
public  List<MonthRegist> countOneMonthRegist(@Param("month") Integer month);

/**
	 * 
	* @Title: 统计每一个校区的注册量
	* @Description: 统计图
	* @param start 开始时间
	* @param end 结束时间
	* @return 某时间段每个校区的注册量
	* @author song bolin
	 */
@Select("SELECT campus_name campus,COUNT(campus_name) count FROM np_wx_regist_record_t where create_date>= #{start} AND create_date<= #{end} GROUP BY  campus_name")
public List<NpCampusRegist> campusRegistList(@Param("start") Integer start,@Param("end")Integer end);
}