package org.anson.miniProject.framework.log.service;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.core.model.dto.service.BaseDTO;
import org.anson.miniProject.domain.sys.operLog.IOperLogDMService;
import org.anson.miniProject.domain.sys.operLog.cmd.OperFailedCMD;
import org.anson.miniProject.domain.sys.operLog.cmd.OperSuccessCMD;
import org.anson.miniProject.framework.shiro.ShiroHelper;
import org.anson.miniProject.tool.helper.ExceptionHelper;
import org.anson.miniProject.tool.helper.IpHelper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Aspect
@Order(20)
@Component
@Slf4j
public class ServiceLogAop {
    @Autowired
    private IOperLogDMService operLogDMService;
    @Autowired
    private HttpServletRequest req;

    private static final String LONGITUDE_KEY = "longitude";
    private static final String LATITUDE_KEY = "latitude";

    @Pointcut(value = "@annotation(org.anson.miniProject.framework.log.service.UserOperLog)")
    public void cutService() {
    }

    @Around("cutService()")
    public Object serviceLog(ProceedingJoinPoint point) throws Throwable {
        log.debug("begin userOperLog");
        Double longitude = null;
        Double latitude = null;

        // 获取注解的值
        UserOperLog userOperLog = this.getUserOperLog(point);

        Object descriptionVal = null;

        Object[] args = point.getArgs();
        String ipv4 = IpHelper.getRemoteHost(req);

        if (StrUtil.isNotEmpty(userOperLog.valKey()) && ArrayUtil.isNotEmpty(args)) {
            for (int i=0;i<args.length;i++){
                Object arg = args[i];
                if (BaseDTO.class.isAssignableFrom(arg.getClass())){
                    descriptionVal = this.getVal(arg, userOperLog.valKey());
                    if(descriptionVal == null){
                        throw new RuntimeException("记录操作日志出错, dto 没有这个 valKey, valKey : " + userOperLog.valKey());
                    }

                    // 两个可空
                    longitude = (Double) this.getVal(arg, LONGITUDE_KEY);
                    latitude = (Double) this.getVal(arg, LATITUDE_KEY);

                    break;  // 匹配到即可退出循环
                }
            }
        }

        try {
            // 执行业务
            Object result = point.proceed();

            // 记录业务成功日志
            OperSuccessCMD operSuccessCMD = new OperSuccessCMD();
            operSuccessCMD.setOperUserId(ShiroHelper.getUserId());
            operSuccessCMD.setOperMenuId(userOperLog.menuId());
            operSuccessCMD.setClientKey(userOperLog.clientKey());
            operSuccessCMD.setDescription(userOperLog.description() + descriptionVal);
            operSuccessCMD.setIpv4(ipv4);
            operSuccessCMD.setLongitude(longitude);
            operSuccessCMD.setLatitude(latitude);

            operLogDMService.operSuccess(operSuccessCMD);
            return result;
        } catch (Exception e) {
            // 记录业务失败日志
            OperFailedCMD cmd = new OperFailedCMD();
            cmd.setOperUserId(ShiroHelper.getUserId());
            cmd.setOperMenuId(userOperLog.menuId());
            cmd.setClientKey(userOperLog.clientKey());
            cmd.setDescription(userOperLog.description() + descriptionVal);
            cmd.setFailReason(ExceptionHelper.getMsg(e));
            cmd.setIpv4(ipv4);
            cmd.setLongitude(longitude);
            cmd.setLatitude(latitude);

            this.operLogDMService.operFailed(cmd);
            throw e;
        }
    }

    private UserOperLog getUserOperLog(ProceedingJoinPoint point) throws NoSuchMethodException {
        Signature sig = point.getSignature();
        MethodSignature msig = (MethodSignature) sig;
        Object target = point.getTarget();
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());

        return currentMethod.getAnnotation(UserOperLog.class);
    }

    private Object getVal(Object obj, String key){

        Field field = null;
        try {
            field = obj.getClass().getDeclaredField(key);
        } catch (NoSuchFieldException e) {
            log.debug(e.getMessage());
        }

        if (field == null){
            return null;
        }

        field.setAccessible(true);
        Object val = null;
        try {
             val = field.get(obj);
        } catch (IllegalAccessException e) {
            log.debug(e.getMessage());
        }

        return val;
    }
}
