package org.anson.miniProject.core.domain.sys.log;

import org.anson.miniProject.core.model.dmo.sys.log.operLog.OperFailedDmo;
import org.anson.miniProject.core.model.dmo.sys.log.operLog.OperSuccessDmo;

import java.util.Date;

public interface IOperLogDomain {
    String operSuccess(OperSuccessDmo dmo, String operUserId, Date operTime);
    String operFailed(OperFailedDmo dmo, String operUserId, Date operTime);
}
