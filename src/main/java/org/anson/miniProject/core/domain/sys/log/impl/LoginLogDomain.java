package org.anson.miniProject.core.domain.sys.log.impl;

import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.core.domain.sys.log.ILoginLogDomain;
import org.anson.miniProject.core.model.dmo.sys.log.LoginFailedDmo;
import org.anson.miniProject.core.model.dmo.sys.log.LoginSuccessDmo;
import org.anson.miniProject.core.model.po.sys.log.LoginLog;
import org.anson.miniProject.core.repository.sys.log.LoginLogRep;
import org.anson.miniProject.tool.helper.IdHelper;
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
    public String loginSuccess(LoginSuccessDmo dmo, String operUserId, Date operTime) throws Exception{
        LoginLog po = LoginSuccessDmo.toLoginLog(dmo);
        po.setLoginTypeKey("login");
        po.setOperTime(operTime);
        po.setAreSuccessful(true);

        po.setId(IdHelper.nextSnowflakeId());

        this.rep.insert(po, operUserId, operTime);

        return po.getId();
    }

    @Override
    // 事务传播属性为 Propagation.REQUIRES_NEW, 新建事务，如果当前存在事务，把当前事务挂起。
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public String loginFailed(LoginFailedDmo dmo, Date operTime) throws Exception {
        LoginLog po = LoginFailedDmo.toLoginLog(dmo);
        po.setLoginTypeKey("login");
        po.setOperTime(operTime);
        po.setAreSuccessful(false);

        po.setId(IdHelper.nextSnowflakeId());

        this.rep.insert(po, "", operTime);

        return po.getId();
    }

    @Override
    public String logoutSuccess() {
        return null;
    }

    @Override
    public String logoutFailed() {
        return null;
    }
}
