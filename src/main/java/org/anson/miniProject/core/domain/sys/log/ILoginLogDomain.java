package org.anson.miniProject.core.domain.sys.log;

import org.anson.miniProject.core.model.dmo.sys.log.LoginFailedDmo;
import org.anson.miniProject.core.model.dmo.sys.log.LoginSuccessDmo;

import java.util.Date;

public interface ILoginLogDomain {
    String loginSuccess(LoginSuccessDmo dmo, String operUserId, Date operTime) throws Exception;
    String loginFailed(LoginFailedDmo dmo, Date operTime) throws Exception;
    String logoutSuccess();
    String logoutFailed();
}
