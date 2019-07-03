package org.anson.miniProject.framework.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.framework.res.ResHelper;
import org.anson.miniProject.framework.res.Response;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

/**
 * 全局异常捕获 - 只捕获 controller 层的异常
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Response handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        //log.error(e.toString(), e);
        return ResHelper.badRequest("参数解析失败", e.getMessage(), new Date());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public Response noHandlerFoundException(NoHandlerFoundException e) {
        //log.debug(e.toString(), e);
        return ResHelper.notFound("notFound", new Date());
    }

    // 参数校验失败
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response methodArgumentNotValidException(MethodArgumentNotValidException e) throws JsonProcessingException {
        //log.debug(e.toString(), e);
        BindingResult bindingResult = e.getBindingResult();

        StringBuilder sb = new StringBuilder();

        for (FieldError error : bindingResult.getFieldErrors()) {
            String field = error.getField();
            Object value = error.getRejectedValue();
            String msg = error.getDefaultMessage();
            String message = String.format("错误字段: %s，错误值: %s, 原因: %s；", field, value, msg);
            sb.append(message).append("\r\n");
        }
        return ResHelper.badRequest(bindingResult.getFieldErrors(), sb.toString(), null, new Date());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Response constraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();

        List<Map> data = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (ConstraintViolation<?> violation : violations) {
            sb.append(violation.getMessage()).append(" ").append("\r\n");
            Map map = new HashMap();
            map.put("field", violation.getPropertyPath().toString());
            map.put("message", violation.getMessage());
            map.put("rejectedValue", violation.getInvalidValue());
            data.add(map);
        }

        return ResHelper.badRequest(data, sb.toString(), null, new Date());
    }

    @ExceptionHandler(Exception.class)
    public Response defaultErrorHandler(Exception e){
        log.error(e.toString(), e);

        return ResHelper.internalServerErr("未知异常", e.getMessage() == null ? e.toString() : e.getMessage(), new Date());

    }
}
