package org.anson.miniProject.domain.sys.log;

import cn.hutool.db.DaoTemplate;
import org.anson.miniProject.model.bo.sys.log.LoginLogBo;

public interface ILoginLogDomain {
    String beginLogin(LoginLogBo bo, String operUserId, DaoTemplate operTime);
    void loginSuccess(String loginLogId);
}
