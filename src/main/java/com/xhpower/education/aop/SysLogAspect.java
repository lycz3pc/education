package com.xhpower.education.aop;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.xhpower.education.annotation.SysLog;
import com.xhpower.education.system.entity.SysLogEntity;
import com.xhpower.education.system.manager.SysLogService;
import com.xhpower.education.utils.HttpContextUtils;
import com.xhpower.education.utils.IPUtils;
import com.xhpower.education.utils.ShiroUtils;

/**
 * 系统日志，切面处理类
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017年3月8日 上午11:07:35
 */
@Aspect
@Component
public class SysLogAspect {
    @Autowired
    private SysLogService sysLogService;

    @Pointcut("@annotation(com.xhpower.education.annotation.SysLog)")
    public void logPointCut() {

    }

    @Before("logPointCut()")
    public void saveSysLog(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        System.out.println("saveSysLog:>>>>>>>>>>>>>>>.....");
        SysLogEntity sysLog = new SysLogEntity();
        SysLog syslog = method.getAnnotation(SysLog.class);
        if (syslog != null) {
            // 注解上的描述
            sysLog.setOperation(syslog.value());
        }

        // 请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");
        System.out.println(1231231232);
        // 请求的参数
        Object[] args = joinPoint.getArgs();
        String params = "";
        if (args.length > 0) {
            try {
                String temp = JSON.toJSONString(args[0]);
                if (temp.length() < 1500) {
                    params = temp;
                } else {
                    params = "参数过长,不予显示";
                }
            } catch (Exception e) {
                params = "参数异常,可能包含文件上传等,不予显示";
            }

        }
        sysLog.setParams(params);

        // 获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        // 设置IP地址
        sysLog.setIp(IPUtils.getIpAddr(request));

        // 用户名
        String username = ShiroUtils.getUsername();
        sysLog.setUsername(username);

        sysLog.setCreateDate(new Date());
        // 保存系统日志
        sysLogService.save(sysLog);
    }

}
