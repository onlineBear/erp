package org.anson.miniProject.domain.account;

import org.apache.shiro.authc.AuthenticationException;

/**
 * @ClassName IUserDomain
 * @Description TODO
 * @Author wanganxiong
 * @Date 2019/6/20 8:47
 * @Version 1.0
 **/
public interface IUserDomain {
    /**
     * 登录
     */
    void login(String userNo, String psd) throws AuthenticationException;
}
