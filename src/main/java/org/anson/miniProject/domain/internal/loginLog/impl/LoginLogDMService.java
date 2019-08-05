package org.anson.miniProject.domain.internal.loginLog.impl;

import org.anson.miniProject.constrant.dict.LoginTypeEnum;
import org.anson.miniProject.domain.internal.loginLog.ILoginLogDMService;
import org.anson.miniProject.domain.internal.loginLog.cmd.LoginFailedCMD;
import org.anson.miniProject.domain.internal.loginLog.cmd.LoginSuccessCMD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional(rollbackFor = Exception.class)
class LoginLogDMService implements ILoginLogDMService {
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public String logLoginSuccess(LoginSuccessCMD cmd) throws Exception {
        LoginLog loginLog = new LoginLog();

        loginLog.setUserId(cmd.getUserId());
        loginLog.setClientKey(cmd.getClientKey().getKey());
        loginLog.setLoginUserNo(cmd.getLoginUserNo());
        loginLog.setIpv4(cmd.getIpv4());
        loginLog.setLongitude(cmd.getLongitude());
        loginLog.setLatitude(cmd.getLatitude());

        loginLog.setLoginTypeKey(LoginTypeEnum.LOGIN.getKey());       // 登录
        loginLog.setOperTime(operTime);
        loginLog.setAreSuccessful(true);  // 成功

        String id = this.dao.insert(loginLog);

        return id;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public String logLoginFailed(LoginFailedCMD cmd) throws Exception {
        LoginLog loginLog = new LoginLog();

        loginLog.setClientKey(cmd.getClientKey().getKey());
        loginLog.setLoginUserNo(cmd.getLoginUserNo());
        loginLog.setFailReason(cmd.getFailReason());
        loginLog.setIpv4(cmd.getIpv4());
        loginLog.setLongitude(cmd.getLongitude());
        loginLog.setLatitude(cmd.getLatitude());

        loginLog.setLoginTypeKey(LoginTypeEnum.LOGIN.getKey());   // 登录
        loginLog.setOperTime(operTime);
        loginLog.setAreSuccessful(false); // 失败

        String id = this.dao.insert(loginLog);

        return id;
    }

    @Override
    public String logLogoutSuccess() throws Exception {
        return null;
    }

    @Override
    public String logLogoutFailed() throws Exception {
        return null;
    }

    // 注入
    @Autowired
    private LoginLogDao dao;
    private String operUserId = "operUserId";
    private Date operTime = new Date();
}
