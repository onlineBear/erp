package org.anson.miniProject.domain.sys.operLog;

import org.anson.miniProject.domain.sys.operLog.cmd.OperFailedCMD;
import org.anson.miniProject.domain.sys.operLog.cmd.OperSuccessCMD;

public interface IOperLogDMService {
    String operSuccess(OperSuccessCMD cmd) throws Exception;
    String operFailed(OperFailedCMD cmd) throws Exception;
}
