package org.anson.miniProject.framework.exception;

import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.model.vo.Response;
import org.springframework.dao.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * 全局异常捕获 - 只捕获 controller 层的异常
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    public Response handle(HttpServletRequest request, Exception e) {

        // 记录日志
        log.error(e.toString(), e);

        // 客户端错误
        if (e instanceof UnsupportedEncodingException) {
            return Response.clientErr("UnsupportedEncodingExceptiond");
        } else if (e instanceof HttpMessageNotReadableException) {
            return Response.clientErr("Please enter the json parameter");
        } else if (e instanceof NoHandlerFoundException) {
            return Response.notFound();
        }

        // 服务端错误
        // sql错误
        else if (e instanceof DuplicateKeyException || e instanceof UncategorizedSQLException
                || e instanceof CleanupFailureDataAccessException || e instanceof DataAccessResourceFailureException || e instanceof DataIntegrityViolationException
                || e instanceof DataRetrievalFailureException || e instanceof DeadlockLoserDataAccessException || e instanceof IncorrectUpdateSemanticsDataAccessException
                || e instanceof OptimisticLockingFailureException || e instanceof UncategorizedDataAccessException || e instanceof BadSqlGrammarException) {
            return Response.serverErr(e.getCause().getMessage());
        } else {
            return Response.serverErr(e.getMessage() == null ? e.toString() : e.getMessage());
        }
    }
}
