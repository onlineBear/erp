package org.zmqy.erp.framework.exception.model.i18n.one;

import lombok.Getter;
import org.zmqy.erp.constract.mis.enums.ExceptionMsgEnum;

/**
 * 一个占位符的异常, 格式: xxx: {0}
 */
public class OnePHException extends RuntimeException{
    @Getter
    private ExceptionMsgEnum msgEnum;

    @Getter
    private String placeHolder;

    public OnePHException(ExceptionMsgEnum msgEnum, String placeHolder){
        this.msgEnum = msgEnum;
        this.placeHolder = placeHolder;
    }
}
