package org.anson.miniProject.domain.sys.log.impl;

import cn.hutool.db.DaoTemplate;
import org.anson.miniProject.domain.sys.log.ILoginLogDomain;
import org.anson.miniProject.model.bo.sys.log.LoginLogBo;
import org.anson.miniProject.repository.sys.log.LoginLogRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(rollbackFor = Exception.class)
public class LoginLogDomain implements ILoginLogDomain {
    @Autowired
    private LoginLogRep rep;

    @Override
    public String beginLogin(LoginLogBo bo, String operUserId, DaoTemplate operTime) {
        return null;
    }

    @Override
    public void loginSuccess(String loginLogId) {

    }
}
