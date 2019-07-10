package org.anson.miniProject.framework.log.service;

import cn.hutool.core.util.ArrayUtil;
import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.core.domain.sys.log.IOperLogDomain;
import org.anson.miniProject.core.model.dmo.sys.log.operLog.OperFailedDmo;
import org.anson.miniProject.core.model.dmo.sys.log.operLog.OperSuccessDmo;
import org.anson.miniProject.framework.pojo.CommonParam;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Aspect
@Order
@Component
@Slf4j
public class ServiceLogAop {
    @Autowired
    private IOperLogDomain operLogDomain;

    @Pointcut(value = "@annotation(org.anson.miniProject.framework.log.service.ServiceLog)")
    public void cutService() {
    }

    @Around("cutService()")
    public Object serviceLog(ProceedingJoinPoint point) throws Throwable {
        log.debug("begin serviceLog");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest req = attributes.getRequest();

        CommonParam commonParam = null;
        Double longitude = null;
        Double latitude = null;

        try {
            // 执行业务
            Object result = point.proceed();

            // 获取注解的值
            ServiceLog serviceLog = this.getServiceLog(point);

            String descriptionVal = null, pkVal = null;

            Object[] args = point.getArgs();

            if (ArrayUtil.isNotEmpty(args)) {
                for (int i=0;i<point.getArgs().length;i++){
                    // 第一个参数是 dto
                    Object arg = args[i];
                    if (i == 0){
                        Field field = arg.getClass().getDeclaredField(serviceLog.valKey());
                        field.setAccessible(true);
                        descriptionVal = (String) field.get(arg);

                        if ("input".equals(serviceLog.pkKey())){
                            field = arg.getClass().getField(serviceLog.pkKey());
                            field.setAccessible(true);
                            pkVal = (String) field.get(arg);
                        }
                    }else if (i == 1 && arg instanceof CommonParam){
                        // 第二个参数是 CommonParam
                        commonParam = (CommonParam) arg;
                    }
                }
            }

            if ("return".equals(serviceLog.pkCalssFrom())){
                Class rsClass = result.getClass();
                Field field = rsClass.getDeclaredField(serviceLog.pkKey());
                field.setAccessible(true);
                pkVal = (String) field.get(result);
            }

            // 记录业务成功日志
            OperSuccessDmo dmo = OperSuccessDmo.builder()
                                    .ipv4(commonParam.getIpv4())
                                    .operMenuId(commonParam.getMenuId())
                                    .clientKey(commonParam.getClientKey())
                                    .longitude(longitude)
                                    .latitude(latitude)
                                    .pk(pkVal)
                                    .mainTableName(serviceLog.mainTableName())
                                    .description(serviceLog.description() + descriptionVal)
                                    .build();
                this.operLogDomain.operSuccess(dmo, commonParam.getLoginUserId(), commonParam.getOperTime());
            return result;
        } catch (Exception e) {
            // 获取注解的值
            ServiceLog serviceLog = this.getServiceLog(point);

            String descriptionVal = null, pkVal = null;
            Object[] args = point.getArgs();

            if (ArrayUtil.isNotEmpty(args)) {
                for (int i=0;i<point.getArgs().length;i++){
                    // 第一个参数是 dto
                    Object arg = args[i];
                    if (i == 0){
                        Field field = arg.getClass().getDeclaredField(serviceLog.valKey());
                        field.setAccessible(true);
                        descriptionVal = (String) field.get(arg);

                        if ("input".equals(serviceLog.pkKey())){
                            field = arg.getClass().getField(serviceLog.pkKey());
                            field.setAccessible(true);
                            pkVal = (String) field.get(arg);
                        }
                    }else if (i == 1 && arg instanceof CommonParam){
                        // 第二个参数是 CommonParam
                        commonParam = (CommonParam) arg;
                    }
                }
            }

            // 记录业务失败日志
            OperFailedDmo dmo = OperFailedDmo.builder()
                                    .operMenuId(commonParam.getMenuId())
                                    .description(serviceLog.description() + descriptionVal)
                                    .failReason(e.getMessage())
                                    .pk(pkVal)
                                    .clientKey(commonParam.getClientKey())
                                    .mainTableName(serviceLog.mainTableName())
                                    .ipv4(commonParam.getIpv4())
                                    .longitude(longitude)
                                    .latitude(latitude)
                                    .build();
            this.operLogDomain.operFailed(dmo, commonParam.getLoginUserId(), commonParam.getOperTime());
            throw e;
        }
    }

    private ServiceLog getServiceLog(ProceedingJoinPoint point) throws NoSuchMethodException {
        Signature sig = point.getSignature();
        MethodSignature msig = (MethodSignature) sig;
        Object target = point.getTarget();
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());

        return currentMethod.getAnnotation(ServiceLog.class);
    }
}
