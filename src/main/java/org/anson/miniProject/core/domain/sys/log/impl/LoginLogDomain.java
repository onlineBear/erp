package org.anson.miniProject.core.domain.sys.log.impl;

import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.core.domain.sys.log.ILoginLogDomain;
import org.anson.miniProject.core.model.dmo.sys.log.BeginLoginDo;
import org.anson.miniProject.core.model.po.sys.log.LoginLog;
import org.anson.miniProject.core.repository.sys.log.LoginLogRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class LoginLogDomain implements ILoginLogDomain {
    @Autowired
    private LoginLogRep rep;

    @Override
    // 事务传播属性为 Propagation.REQUIRES_NEW, 新建事务，如果当前存在事务，把当前事务挂起。
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public String beginLogin(BeginLoginDo dmo) throws Exception{
        LoginLog po = BeginLoginDo.toLoginLog(dmo);
        po.setAreSuccessful(false);
        return this.rep.insert(po, "", dmo.getOperTime());
    }

    @Override
    // 事务传播属性为 Propagation.REQUIRES_NEW, 新建事务，如果当前存在事务，把当前事务挂起。
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void loginSuccess(String loginLogId, String userId, Date operTime) throws Exception{
        LoginLog po = new LoginLog();
        po.setId(loginLogId);
        po.setAreSuccessful(true);
        po.setUserId(userId);

        this.rep.update(po, operTime);
    }

    @Override
    // 事务传播属性为 Propagation.REQUIRES_NEW, 新建事务，如果当前存在事务，把当前事务挂起。
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void loginFail(String loginLogId, String failReason, Date operTime) throws Exception {
        LoginLog po = new LoginLog();
        po.setId(loginLogId);
        po.setAreSuccessful(false);
        po.setFailReason(failReason);

        this.rep.update(po, operTime);
    }
}
