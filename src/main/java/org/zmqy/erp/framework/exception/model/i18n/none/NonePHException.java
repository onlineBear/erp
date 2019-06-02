package org.zmqy.erp.framework.exception.model.i18n.none;

import lombok.Getter;
import org.zmqy.erp.constract.mis.enums.ExceptionMsgEnum;

/**
 * 没有占位符的异常, 格式: xxx
 */
public class NonePHException extends RuntimeException{
    @Getter
    private ExceptionMsgEnum msgEnum;

    public NonePHException(ExceptionMsgEnum msgEnum){
        this.msgEnum = msgEnum;
    }
}
