package com.xhpower.education.system.manager.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xhpower.education.system.dao.SysLogDao;
import com.xhpower.education.system.entity.SysLogEntity;
import com.xhpower.education.system.manager.SysLogService;


/**
 * 
* @ClassName: SysLogServiceImpl 
* @Description: 系统日志服务实现类
* @author xiong li 
* @date 2017年8月31日 上午11:12:51 
*
 */
@Service("sysLogService")
@Transactional
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLogEntity> implements SysLogService {
	@Autowired
	private SysLogDao sysLogDao;
	
	@Override
	public SysLogEntity queryObject(Long id){
		return sysLogDao.queryObject(id);
	}
	
	@Override
	public List<SysLogEntity> queryList(Map<String, Object> map){
		return sysLogDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return sysLogDao.queryTotal(map);
	}
	
	@Override
	public void save(SysLogEntity sysLog){
		sysLogDao.save(sysLog);
	}
	
	@Override
	public void update(SysLogEntity sysLog){
		sysLogDao.update(sysLog);
	}
	
	@Override
	public void delete(Long id){
		sysLogDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		sysLogDao.deleteBatch(ids);
	}
	
}
