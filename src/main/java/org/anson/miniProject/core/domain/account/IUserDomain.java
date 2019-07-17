package org.anson.miniProject.core.domain.account;

import org.anson.miniProject.core.model.param.sys.LoginParam;
import org.apache.shiro.authc.AuthenticationException;

import java.util.Date;

public interface IUserDomain {
    void login(LoginParam param, Date loginTime) throws Exception;
    String authentication(String userNo, String encryptedPsd) throws AuthenticationException;
}
