package org.anson.miniProject.core.domain.sys.log;

import org.anson.miniProject.core.model.param.sys.log.loginLog.LoginFailedParam;
import org.anson.miniProject.core.model.param.sys.log.loginLog.LoginSuccessParam;
import org.anson.miniProject.core.model.param.sys.log.loginLog.LogoutFailedParam;
import org.anson.miniProject.core.model.param.sys.log.loginLog.LogoutSuccessParam;

import java.util.Date;

public interface ILoginLogDomain {
    String loginSuccess(LoginSuccessParam dmo, String operUserId, Date operTime) throws Exception;
    String loginFailed(LoginFailedParam dmo, Date operTime) throws Exception;
    String logoutSuccess(LogoutSuccessParam param, String operUserId, Date operTime) throws Exception;
    String logoutFailed(LogoutFailedParam param, String operUserId, Date operTime) throws Exception;
}
