package org.zmqy.erp.framework.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.zmqy.erp.framework.exception.model.unI18n.InternalException;
import org.zmqy.erp.framework.exception.model.i18n.none.NonePHException;
import org.zmqy.erp.framework.exception.model.i18n.one.OnePHException;
import org.zmqy.erp.model.mis.vo.Response;
import org.zmqy.erp.tool.util.common.I18nMessageUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * 全局异常捕获 - 只捕获 controller 层的异常
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandle {

    private static final String HEADER_LANGNO = "loginLangNo";

    @ExceptionHandler(value = Exception.class)
    public Response handle(HttpServletRequest request, Exception e) {

        //获取请求头中的语言类型
        String langNo = request.getHeader(HEADER_LANGNO);
        // 记录日志
        log.error(e.toString(), e);

        // 内部异常
        if(e instanceof InternalException){
            //InternalException internalException = (InternalException)e;
            return Response.serverErr(I18nMessageUtil.getMessageSource(langNo, InternalException.msg));
        }
        // 没有占位符
        else if(e instanceof NonePHException){
            NonePHException nonePHException = (NonePHException)e;
            return Response.clientErr(I18nMessageUtil.getMsg(langNo, nonePHException.getMsgEnum()));
        }
        // 一个占位符
        else if(e instanceof OnePHException){
            OnePHException onePHException = (OnePHException)e;
            return Response.clientErr(I18nMessageUtil.getMsg(langNo, onePHException.getMsgEnum(), onePHException.getPlaceHolder()));
        }

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
