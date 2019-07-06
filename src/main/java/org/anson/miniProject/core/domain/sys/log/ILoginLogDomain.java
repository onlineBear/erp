package org.anson.miniProject.core.domain.sys.log;

import org.anson.miniProject.core.model.dmo.sys.log.BeginLoginDo;

import java.util.Date;

public interface ILoginLogDomain {
    String beginLogin(BeginLoginDo dto) throws Exception;
    void loginSuccess(String loginLogId, String userId, Date operTime) throws Exception;
    void loginFail(String loginLogId, String failReason, Date operTime) throws Exception;
}
