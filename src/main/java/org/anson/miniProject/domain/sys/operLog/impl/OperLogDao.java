package org.anson.miniProject.domain.sys.operLog.impl;

import org.anson.miniProject.domain.base.BaseDao;
import org.anson.miniProject.tool.helper.IdHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Component
@Repository
class OperLogDao extends BaseDao<OperLog, OperLogMapper> {
    public String insert(OperLog operLog){
        operLog.setId(IdHelper.nextSnowflakeId());
        operLog.setCreateTime(operDate);
        operLog.setUpdateTime(operDate);

        this.mapper.insert(operLog);
        return operLog.getId();
    }

    // 注入
    @Autowired
    @Override
    protected void setMapper(OperLogMapper mapper) {
        this.mapper = mapper;
    }
    private Date operDate = new Date();
}
