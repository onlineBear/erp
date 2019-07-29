package org.anson.miniProject.domain.account.user;

import org.anson.miniProject.domain.account.user.cmd.LoginCMD;
import org.anson.miniProject.domain.account.user.cmd.LogoutCMD;
import org.apache.shiro.authc.AuthenticationException;

public interface IUserDMService {
    String authentication(String userNo, String encryptedPsd) throws AuthenticationException;
    void login(LoginCMD cmd) throws Exception;
    void logout(LogoutCMD cmd) throws Exception;
}
