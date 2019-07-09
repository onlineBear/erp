package org.anson.miniProject.constrant.dict;

/**
 * 客户端
 */
public enum LoginTypeEnum {

    /**
     * 登录
     */
    LOGIN("loginType-login"),

    /**
     * 登出
     */
    LOGOUT("loginType-logout");

    private String key;

    private LoginTypeEnum(String key){
        this.key = key;
    }

    public String getKey(){
        return this.key;
    }
}
