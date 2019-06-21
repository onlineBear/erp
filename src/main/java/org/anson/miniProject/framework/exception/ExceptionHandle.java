package org.anson.miniProject.framework.exception;

import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.framework.res.ResHelper;
import org.anson.miniProject.framework.res.Response;
import org.springframework.dao.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.UnsupportedEncodingException;

/**
 * 全局异常捕获 - 只捕获 controller 层的异常
 */
/**
 * @ClassName ExceptionHandle
 * @Description TODO
 * @Author wanganxiong
 * @Date 2019/6/21 17:05
 * @Version 1.0
 **/
@Slf4j
@RestControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    public Response handle(Exception e) {
        // 记录日志
        log.error(e.toString(), e);

        // 客户端错误
        if (e instanceof HttpMessageNotReadableException){
            return ResHelper.badRequest("json 格式错误", e.toString());
        } else if (e instanceof UnsupportedEncodingException) {
            return ResHelper.badRequest("badReques", e.toString());
        } else if (e instanceof HttpMessageNotReadableException) {
            return ResHelper.badRequest("badReques", e.toString());
        } else if (e instanceof NoHandlerFoundException) {
            return ResHelper.notFound("notFound");
        }

        // 服务端错误
        // sql错误
        else if (e instanceof DuplicateKeyException || e instanceof UncategorizedSQLException
                || e instanceof CleanupFailureDataAccessException || e instanceof DataAccessResourceFailureException || e instanceof DataIntegrityViolationException
                || e instanceof DataRetrievalFailureException || e instanceof DeadlockLoserDataAccessException || e instanceof IncorrectUpdateSemanticsDataAccessException
                || e instanceof OptimisticLockingFailureException || e instanceof UncategorizedDataAccessException || e instanceof BadSqlGrammarException) {
            return ResHelper.internalServerErr("数据库异常", e.getCause().getMessage());
        }

        return ResHelper.internalServerErr("未知异常", e.getMessage() == null ? e.toString() : e.getMessage());
    }
}
