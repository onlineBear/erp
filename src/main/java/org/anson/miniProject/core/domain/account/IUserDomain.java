package org.anson.miniProject.core.domain.account;

import org.anson.miniProject.core.model.param.account.user.LoginParam;
import org.anson.miniProject.core.model.param.account.user.LogoutParam;
import org.apache.shiro.authc.AuthenticationException;

import java.util.Date;

public interface IUserDomain {
    void login(LoginParam param, Date loginTime) throws Exception;
    void logout(LogoutParam param, String logoutUserId, Date logoutTime) throws Exception;
    String authentication(String userNo, String encryptedPsd) throws AuthenticationException;
}
