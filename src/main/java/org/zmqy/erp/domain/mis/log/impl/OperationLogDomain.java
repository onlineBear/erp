package org.zmqy.erp.domain.mis.log.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.zmqy.erp.domain.mis.log.IOperationLogDomain;
import org.zmqy.erp.mapper.mis.biz.log.OperationLogMapper;
import org.zmqy.erp.model.mis.bo.log.OperationLogBo;
import org.zmqy.erp.model.mis.entity.log.OperationLog;

import java.util.Date;

@Component
public class OperationLogDomain implements IOperationLogDomain {
    @Autowired
    private OperationLogMapper mapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String add(OperationLogBo bo, String operUserId, Date operTime) throws Exception{
        if(bo == null){
            throw new RuntimeException("请输入操作日志");
        }

        OperationLog operationLog = OperationLogBo.bo2entity(bo);

        String id = this.add(operationLog, operUserId, operTime);

        return id;
    }

    // 类似私有方法
    @Transactional(rollbackFor = Exception.class)
    public String add(OperationLog entity, String operUserId, Date operTime) throws Exception{
        if(StringUtils.isEmpty(entity.getRecordId())){
            throw new RuntimeException("请输入记录id");
        }

        if(entity.getOperTime() == null){
            throw new RuntimeException("请输入操作时间");
        }

        if(StringUtils.isEmpty(operUserId)){
            throw new RuntimeException("请输入操作用户id");
        }

        if(StringUtils.isEmpty(entity.getOperTypeNo())){
            throw new RuntimeException("请输入操作类型编码");
        }

        if(StringUtils.isEmpty(entity.getOperMenuNo())){
            throw new RuntimeException("请输入操作菜单id");
        }

        if(StringUtils.isEmpty(entity.getUrl())){
            throw new RuntimeException("请输入url");
        }

        entity.setCreateUserId(operUserId);
        entity.setCreateTime(operTime);
        entity.setLastUpdateTime(operTime);

        this.mapper.insert(entity);

        return entity.getId();
    }
}
