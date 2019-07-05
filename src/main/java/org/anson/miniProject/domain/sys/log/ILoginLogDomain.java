package org.anson.miniProject.domain.sys.log;

import org.anson.miniProject.model.bo.sys.log.LoginLogBo;

import java.util.Date;

public interface ILoginLogDomain {
    String beginLogin(LoginLogBo bo, Date operTime);
    void loginSuccess(String loginLogId, String userId, Date operTime);
    void loginFail(String loginLogId, String failReason, Date operTime);
}
