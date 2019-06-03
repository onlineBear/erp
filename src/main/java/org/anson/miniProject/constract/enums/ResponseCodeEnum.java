package org.anson.miniProject.constract.enums;

/**
 * 状态代码 - 枚举
 */
public enum ResponseCodeEnum {

    /**
     * 200 ok
     */
    OK("200"),

    /**
     * 404 not found
     */
    NOT_FOUND("404"),

    /**
     * 无权限访问接口
     */
    UNAUTHORIZED("430"),

    /**
     * 未登陆
     */
    UNLOGIN("450"),

    /**
     * token已过期
     */
    TOKEN_EXPIRED("451"),

    /**
     * refreshtoken已过期
     */
    REFRESH_TOKEN_EXPIRED("452"),

    /**
     * token未过期
     */
    TOKEN_UNEXPIRED("453"),

    /**
     * token为空
     */
    TOKEN_EMPTY("454"),

    /**
     * 签名错误
     */
    SIGN_ERROR("455"),

    /**
     * 客户端错误
     */
    CLIENT_ERROR("499"),

    /**
     * 服务器错误
     */
    SERVER_ERROR("599");

    private String code;

    private ResponseCodeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
