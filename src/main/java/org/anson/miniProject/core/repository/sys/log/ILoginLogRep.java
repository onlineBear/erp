package org.anson.miniProject.core.repository.sys.log;

import org.anson.miniProject.core.mapper.sys.log.LoginLogMapper;
import org.anson.miniProject.core.model.po.sys.log.LoginLog;
import org.anson.miniProject.core.repository.IBaseRep;

import java.util.Date;

public interface ILoginLogRep extends IBaseRep<LoginLog, LoginLogMapper> {
    String insert(LoginLog po, String operUserId, Date operTime);
}
