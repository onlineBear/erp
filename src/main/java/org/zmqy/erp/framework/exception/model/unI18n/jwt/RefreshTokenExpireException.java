package org.zmqy.erp.framework.exception.model.unI18n.jwt;

/**
 * refresh token 已过期
 */
public class RefreshTokenExpireException extends RuntimeException{

    private static final String msg = "refresh token 已过期";
    private static final String code = "";

    public RefreshTokenExpireException(){}
}
