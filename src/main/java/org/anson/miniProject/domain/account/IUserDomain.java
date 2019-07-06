package org.anson.miniProject.domain.account;

import org.apache.shiro.authc.AuthenticationException;

public interface IUserDomain {
    String authentication(String userNo, String encryptedPsd) throws AuthenticationException;
}
