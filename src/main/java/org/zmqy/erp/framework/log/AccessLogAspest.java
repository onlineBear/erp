package org.zmqy.erp.framework.log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.zmqy.erp.tool.helper.Ip.IpHelper;
import org.zmqy.erp.tool.helper.id.IdHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 访问日志切面
 */
@Aspect
@Order(1)
@Component
@Slf4j
public class AccessLogAspest {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 切点 controller
     */
    @Pointcut("execution(public * org.zmqy.erp.web.controller..*.*(..))")
    public void AccessLog(){}

    /**
     * 切点 异常
     */
    @Pointcut("execution(public * org.zmqy.erp.framework.exception.ExceptionHandle.handle(..))")
    public void AccessExceptionLog(){}

    /**
     * 输出请求日志
     * @param joinPoint
     */
    @Before("AccessLog()")
    public void doBefore(JoinPoint joinPoint) {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        log.info("url : {}", request.getRequestURL().toString());
        log.info("contentType : {}", request.getContentType());
        log.info("ip : {}", IpHelper.getRemoteHost(request));
        log.info("controller_class_method : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        log.info("args : {}", Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * 输出响应成功日志
     * @param ret
     * @throws JsonProcessingException
     */
    @AfterReturning(returning = "ret", pointcut = "AccessLog()")
    public void doAfterReturning(Object ret) throws JsonProcessingException {
        // 处理完请求，返回内容
        log.info("response : {}", AccessLogAspest.objectMapper.writeValueAsString(ret));
    }

    /**
     * 输出响应异常日志
     * @param ret
     * @throws JsonProcessingException
     */
    @AfterReturning(returning = "ret", pointcut = "AccessExceptionLog()")
    public void doAfterExceptionReturning(Object ret) throws JsonProcessingException {
        // 处理完请求，返回内容
        log.info("response : {}", objectMapper.writeValueAsString(ret));
    }
}
