package org.zmqy.erp.domain.mis.log;

import org.zmqy.erp.model.mis.bo.log.OperationLogBo;

import java.util.Date;

public interface IOperationLogDomain {
    // recordId
    public String add(OperationLogBo bo, String operUserId, Date operTime) throws Exception;
}
