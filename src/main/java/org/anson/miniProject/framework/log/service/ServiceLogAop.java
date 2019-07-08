package org.anson.miniProject.framework.log.service;

import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.core.domain.sys.log.IOperLogDomain;
import org.anson.miniProject.core.model.dmo.sys.log.operLog.OperFailedDmo;
import org.anson.miniProject.core.model.dmo.sys.log.operLog.OperSuccessDmo;
import org.anson.miniProject.framework.shiro.ShiroHelper;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

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

        String ipv4 = IpHelper.getRemoteHost(req);
        String operUserId = ShiroHelper.getUserId();
        String operMenuId = "operMenuId";
        String clientKey = "pc";
        Date operTime = new Date();
        Double longitude = null;
        Double latitude = null;

        try {
            // 执行业务
            Object result = point.proceed();

            // 获取注解的值
            ServiceLog serviceLog = this.getServiceLog(point);

            // 记录业务成功日志
            OperSuccessDmo dmo = OperSuccessDmo.builder()
                                    .ipv4(ipv4)
                                    .operMenuId(operMenuId)
                                    .operTypeKey(serviceLog.operTypeKey())
                                    .clientKey(clientKey)
                                    .longitude(longitude)
                                    .latitude(latitude)
                                    .pk("pk")
                                    .mainTableName(serviceLog.mainTableName())
                                    .description(serviceLog.value())
                                    .build();
                this.operLogDomain.operSuccess(dmo, operUserId, operTime);
            return result;
        } catch (Exception e) {
            // 获取注解的值
            ServiceLog serviceLog = this.getServiceLog(point);

            // 记录业务失败日志
            OperFailedDmo dmo = OperFailedDmo.builder()
                                    .operMenuId(operMenuId)
                                    .operTypeKey(serviceLog.operTypeKey())
                                    .description(serviceLog.value())
                                    .failReason(e.getMessage())
                                    .pk("pk")
                                    .clientKey(clientKey)
                                    .mainTableName(serviceLog.mainTableName())
                                    .ipv4(ipv4)
                                    .longitude(longitude)
                                    .latitude(latitude)
                                    .build();
            this.operLogDomain.operFailed(dmo, operUserId, operTime);
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
