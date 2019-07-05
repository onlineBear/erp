package org.anson.miniProject.domain.sys.log.impl;

import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.domain.sys.log.ILoginLogDomain;
import org.anson.miniProject.model.bo.sys.log.LoginLogBo;
import org.anson.miniProject.model.entity.sys.log.LoginLog;
import org.anson.miniProject.repository.sys.log.LoginLogRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class LoginLogDomain implements ILoginLogDomain {
    @Autowired
    private LoginLogRep rep;

    @Override
    public String beginLogin(LoginLogBo bo, Date operTime) {
        LoginLog entity = LoginLogBo.boToentity(bo);
        entity.setAreSuccessful(false);
        return this.rep.insert(entity, "", operTime);
    }

    @Override
    public void loginSuccess(String loginLogId, String userId, Date operTime) {
        LoginLog entity = new LoginLog();
        entity.setId(loginLogId);
        entity.setAreSuccessful(true);
        entity.setUserId(userId);

        this.rep.update(entity, operTime);
    }

    @Override
    public void loginFail(String loginLogId, String failReason, Date operTime) {
        LoginLog entity = new LoginLog();
        entity.setId(loginLogId);
        entity.setAreSuccessful(false);
        entity.setFailReason(failReason);

        this.rep.update(entity, operTime);
    }
}
