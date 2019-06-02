package org.zmqy.erp.service.mis.pc.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zmqy.erp.domain.mis.flow.IFlowLogDomain;
import org.zmqy.erp.model.mis.bo.sys.flow.FlowLogBo;

import java.util.Date;

@Service
@Transactional(rollbackFor = Exception.class)
public class FlowLogService {
    @Autowired
    private IFlowLogDomain domain;

    public void log(String userId, Date checkTime, String flowId, String billId, Integer level, String operNo) throws Exception{
        FlowLogBo bo = new FlowLogBo();
        bo.setUserId(userId);
        bo.setCheckTime(checkTime);
        bo.setFlowId(flowId);
        bo.setBillId(billId);
        bo.setLevel(level);
        bo.setOperNo(operNo);

        this.domain.add(bo, userId, checkTime);
    }
}
