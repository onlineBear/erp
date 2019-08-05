package org.anson.miniProject.domain.sys.operLog.impl;

import org.anson.miniProject.domain.sys.operLog.IOperLogDMService;
import org.anson.miniProject.domain.sys.operLog.cmd.OperFailedCMD;
import org.anson.miniProject.domain.sys.operLog.cmd.OperSuccessCMD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional(rollbackFor = Exception.class)
class OperLogDMService implements IOperLogDMService {
    @Override
    public String operSuccess(OperSuccessCMD cmd) throws Exception {
        // 检查 cmd

        OperLog operLog = OperSuccessCMDTranslator.toOperLog(cmd);
        operLog.setAreSuccessful(true);

        String id = this.dao.insert(operLog);
        return id;
    }

    @Override
    public String operFailed(OperFailedCMD cmd) throws Exception {
        // 检查 cmd
        OperLog operLog = OperFailedCMDTranslator.toOperLog(cmd);
        operLog.setAreSuccessful(false);

        String id = this.dao.insert(operLog);
        return id;
    }

    // 注入
    @Autowired
    private OperLogDao dao;
}
