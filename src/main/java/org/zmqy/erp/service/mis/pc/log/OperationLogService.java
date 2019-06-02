package org.zmqy.erp.service.mis.pc.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zmqy.erp.constract.mis.enums.OperationLogEmum;
import org.zmqy.erp.domain.mis.log.IOperationLogDomain;
import org.zmqy.erp.model.mis.bo.log.OperationLogBo;
import org.zmqy.erp.tool.helper.Ip.IpHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class OperationLogService {
    @Autowired
    private IOperationLogDomain domain;
    @Autowired
    private HttpServletRequest request;

    @Transactional(rollbackFor = Exception.class)
    public String logging(String recordId, Date operTime, String operUserId,
                          OperationLogEmum operationLogEmum, String operMenuNo) throws Exception{
        OperationLogBo logBo = new OperationLogBo();

        logBo.setRecordId(recordId);
        logBo.setOperTime(operTime);
        logBo.setOperUserId(operUserId);
        logBo.setOperTypeNo(operationLogEmum.getOperationLogEmum());
        logBo.setOperMenuNo(operMenuNo);
        logBo.setUrl(request.getRequestURL().toString());
        logBo.setIpv4(IpHelper.getRemoteHost(request));

        return this.domain.add(logBo, operUserId, operTime);
    }
}
