package org.anson.miniProject.framework.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * jwt 认证 token
 */
public class JWTAuthenticationToken implements AuthenticationToken {
    /**
     * token 存储 userId
     */
    private String token;

    /**
     * 用户密码 (存储空串)
     */
    private String password;

    public JWTAuthenticationToken(){

    }

    public JWTAuthenticationToken(String token, String password) {
        this.token = token;
        this.password = password;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return password;
    }
}
