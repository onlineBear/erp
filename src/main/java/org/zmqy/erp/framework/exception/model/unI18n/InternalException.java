package org.zmqy.erp.framework.exception.model.unI18n;

import lombok.Getter;
import org.zmqy.erp.constract.mis.enums.ExceptionMsgEnum;

/**
 * 内部异常类
 * 即不做国际化的异常类
 * 一般情况下, 用户无法直接接触到该类异常, 即使接触到了, 用户看到了详细的异常信息, 也无法解决
 */
public class InternalException extends RuntimeException{

    @Getter
    private String internaleMsg;

    public static final String msg = ExceptionMsgEnum.INTERNAL_ERR.getErrMsg();

    public InternalException(String internaleMsg){
        this.internaleMsg = internaleMsg;
    }
}
