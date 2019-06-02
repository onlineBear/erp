package org.zmqy.erp.domain.mis.flow;

import org.zmqy.erp.model.mis.bo.sys.flow.FlowLogBo;

import java.util.Date;

public interface IFlowLogDomain {
    String add(FlowLogBo bo, String operUserId, Date operTime) throws Exception;
}
