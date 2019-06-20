package org.anson.miniProject.domain.account;

import org.apache.shiro.authc.AuthenticationException;

import java.util.Date;

/**
 * @ClassName IUserDomain
 * @Description TODO
 * @Author wanganxiong
 * @Date 2019/6/20 8:47
 * @Version 1.0
 **/
public interface IUserDomain {
    void login(String no, String psd) throws AuthenticationException;
}
