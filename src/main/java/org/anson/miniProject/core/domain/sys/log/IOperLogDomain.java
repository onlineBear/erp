package org.anson.miniProject.core.domain.sys.log;

import org.anson.miniProject.core.model.param.sys.log.operLog.OperFailedParam;
import org.anson.miniProject.core.model.param.sys.log.operLog.OperSuccessParam;

import java.util.Date;

public interface IOperLogDomain {
    String operSuccess(OperSuccessParam dmo, String operUserId, Date operTime) throws Exception;
    String operFailed(OperFailedParam dmo, String operUserId, Date operTime) throws Exception;
}
