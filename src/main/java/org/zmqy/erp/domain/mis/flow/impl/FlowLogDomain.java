package org.zmqy.erp.domain.mis.flow.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.zmqy.erp.domain.mis.flow.IFlowLogDomain;
import org.zmqy.erp.framework.exception.model.unI18n.InternalException;
import org.zmqy.erp.mapper.mis.pc.module.sys.flow.FlowLogMapper;
import org.zmqy.erp.model.mis.bo.sys.flow.FlowLogBo;
import org.zmqy.erp.model.mis.flow.FlowLog;
import org.zmqy.erp.tool.helper.Ip.IpHelper;
import org.zmqy.erp.tool.util.common.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@Transactional(rollbackFor = Exception.class)
public class FlowLogDomain implements IFlowLogDomain {
    @Autowired
    private FlowLogMapper mapper;
    @Autowired
    private HttpServletRequest request;

    @Override
    public String add(FlowLogBo bo, String operUserId, Date operTime) throws Exception {
        if(bo == null){
            throw new InternalException("请输入审核日志");
        }

        FlowLog flowLog = FlowLogBo.bo2entity(bo);
        flowLog.setIp(IpHelper.getRemoteHost(request));

        this.add(flowLog, operUserId, operTime);

        return flowLog.getId();
    }

    //
    public String add(FlowLog entity, String operUserId, Date operTime){
        if(entity.getCheckTime() == null){
            throw new InternalException("请输入审核时间");
        }

        if(StringUtil.isEmpty(entity.getFlowId())){
            throw new InternalException("请输入流程id");
        }

        if(StringUtil.isEmpty(entity.getBillId())){
            throw new InternalException("请输入单据id");
        }

        if(entity.getLevel() == null || entity.getLevel() < 0){
            throw new InternalException("层级错误");
        }

        entity.setCreateUserId(operUserId);
        entity.setCreateUserTime(operTime);
        entity.setLastUpdateTime(operTime);

        this.mapper.insert(entity);

        return entity.getId();
    }
}
