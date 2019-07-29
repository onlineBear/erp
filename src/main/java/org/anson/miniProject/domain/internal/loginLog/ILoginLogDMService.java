package org.anson.miniProject.domain.internal.loginLog;

import org.anson.miniProject.domain.internal.loginLog.cmd.LoginFailedCMD;
import org.anson.miniProject.domain.internal.loginLog.cmd.LoginSuccessCMD;

public interface ILoginLogDMService {
    String logLoginSuccess(LoginSuccessCMD cmd) throws Exception;
    String logLoginFailed(LoginFailedCMD cmd) throws Exception;
    String logLogoutSuccess() throws Exception;
    String logLogoutFailed() throws Exception;
}
