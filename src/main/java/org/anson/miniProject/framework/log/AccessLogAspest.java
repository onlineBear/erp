package org.anson.miniProject.framework.log;

/**
 * @ClassName AccessLogAspest
 * @Description TODO
 * @Author wanganxiong
 * @Date 2019/6/21 16:34
 * @Version 1.0
 **/

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.tool.helper.IpHelper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 访问日志切面
 */
@Aspect
@Order
@Component
@Slf4j
public class AccessLogAspest {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 切点 controller
     */
    @Pointcut("execution(public * org.anson.miniProject.web.controller..*.*(..))")
    public void AccessLog(){}

    /**
     * 切点 异常
     */
    @Pointcut("execution(public * org.anson.miniProject.framework.exception.ExceptionHandle.*(..))")
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
        log.info("url : {} contentType : {} ip : {} controller_class_method : {}.{}\nargs : {}",
                 request.getRequestURL().toString(), request.getContentType(), IpHelper.getRemoteHost(request), joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(),
                 Arrays.toString(joinPoint.getArgs()));
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
        log.error("response : {}", AccessLogAspest.objectMapper.writeValueAsString(ret));
    }
}
