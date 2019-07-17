package org.anson.miniProject.core.domain.sys.log.impl;

import org.anson.miniProject.core.domain.sys.log.IOperLogDomain;
import org.anson.miniProject.core.model.param.sys.log.operLog.OperFailedDmo;
import org.anson.miniProject.core.model.param.sys.log.operLog.OperSuccessDmo;
import org.anson.miniProject.core.model.po.sys.log.OperLog;
import org.anson.miniProject.core.repository.sys.log.impl.OperLogRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional(rollbackFor = Exception.class)
public class OperLogDomain implements IOperLogDomain {
    @Autowired
    private OperLogRep rep;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public String operSuccess(OperSuccessDmo dmo, String operUserId, Date operTime) throws Exception{
        OperLog po = OperSuccessDmo.toOperLog(dmo);
        po.setOperUserId(operUserId);
        po.setOperTime(operTime);

        po.setAreSuccessful(true);
        return this.rep.insert(po, operUserId, operTime);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public String operFailed(OperFailedDmo dmo, String operUserId, Date operTime) throws Exception{
        OperLog po = OperFailedDmo.toOperLog(dmo);
        po.setOperUserId(operUserId);
        po.setOperTime(operTime);

        po.setAreSuccessful(false);
        return this.rep.insert(po, operUserId, operTime);
    }
}
