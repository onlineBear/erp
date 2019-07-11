package org.anson.miniProject.framework.log.service;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.core.domain.sys.log.IOperLogDomain;
import org.anson.miniProject.core.model.dmo.sys.log.operLog.OperFailedDmo;
import org.anson.miniProject.core.model.dmo.sys.log.operLog.OperSuccessDmo;
import org.anson.miniProject.core.model.dto.service.BaseDTO;
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

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Aspect
@Order
@Component
@Slf4j
public class ServiceLogAop {
    @Autowired
    private IOperLogDomain operLogDomain;

    private static final String LONGITUDE_KEY = "longitude";
    private static final String LATITUDE_KEY = "latitude";

    @Pointcut(value = "@annotation(org.anson.miniProject.framework.log.service.ServiceLog)")
    public void cutService() {
    }

    @Around("cutService()")
    public Object serviceLog(ProceedingJoinPoint point) throws Throwable {
        log.debug("begin serviceLog");
        CommonParam commonParam = null;
        Double longitude = null;
        Double latitude = null;

        // 获取注解的值
        ServiceLog serviceLog = this.getServiceLog(point);

        String descriptionVal = null, pkVal = null;

        Object[] args = point.getArgs();

        if (ArrayUtil.isNotEmpty(args)) {
            for (int i=0;i<args.length;i++){
                Object arg = args[i];
                if (BaseDTO.class.isAssignableFrom(arg.getClass())){
                    descriptionVal = (String) this.getVal(arg, serviceLog.valKey());
                    if(descriptionVal == null){
                        throw new RuntimeException("记录操作日志出错, dto 没有这个 valKey, valKey : " + serviceLog.valKey());
                    }

                    // 两个可空
                    longitude = (Double) this.getVal(arg, LONGITUDE_KEY);
                    latitude = (Double) this.getVal(arg, LATITUDE_KEY);

                    if (PkClassFrom.INPUT.equals(serviceLog.pkKey())){
                        pkVal = (String) this.getVal(arg, serviceLog.pkKey());
                    }
                }

                if (arg instanceof CommonParam){
                    commonParam = (CommonParam) arg;
                }
            }
        }

        try {
            // 执行业务
            Object result = point.proceed();

            if (PkClassFrom.RETURN.equals(serviceLog.pkCalssFrom())){
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
            // 记录业务失败日志
            OperFailedDmo dmo = OperFailedDmo.builder()
                                    .operMenuId(commonParam.getMenuId())
                                    .description(serviceLog.description() + descriptionVal)
                                    .failReason(this.getExceptionMsg(e))
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

    private String getExceptionMsg(Exception e){
        if (e.getCause() != null && StrUtil.isNotBlank(e.getCause().getMessage())){
            return e.getCause().getMessage();
        }

        if (StrUtil.isNotBlank(e.getMessage())){
            return e.getMessage();
        }

        return e.toString();
    }
}
