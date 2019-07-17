package org.anson.miniProject.core.domain.account.impl;

import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.core.domain.account.IUserDomain;
import org.anson.miniProject.core.repository.sys.UserRep;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class UserDomain implements IUserDomain {
    @Autowired
    private UserRep rep;

    @Override
    // 成功返回 userId; 验证失败返回 null
    public String authentication(String userNo, String encryptedPsd) throws AuthenticationException {
        return this.rep.authentication(userNo, encryptedPsd);
    }
}