package org.zmqy.erp.framework.exception.model.unI18n.jwt;

/**
 * token 已过期 (refresh token 未过期)
 */
public class TokenExpireException extends RuntimeException {
    private static final String msg = "token 已过期";
    private static final String code = "";

    public TokenExpireException(){}
}
