package org.anson.miniProject.core.domain.sys.log;

import org.anson.miniProject.core.model.dmo.sys.log.LoginFailedDo;
import org.anson.miniProject.core.model.dmo.sys.log.LoginSuccessDo;

import java.util.Date;

public interface ILoginLogDomain {
    String loginSuccess(LoginSuccessDo dmo, String operUserId, Date operTime) throws Exception;
    String loginFailed(LoginFailedDo dmo, Date operTime) throws Exception;
    String logoutSuccess();
    String logoutFailed();
}
