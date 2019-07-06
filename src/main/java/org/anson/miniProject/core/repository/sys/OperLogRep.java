package org.anson.miniProject.core.repository.sys;

import org.anson.miniProject.core.mapper.sys.OperLogMapper;
import org.anson.miniProject.core.model.po.sys.OperLog;
import org.anson.miniProject.core.repository.BaseRep;
import org.anson.miniProject.tool.helper.IdHelper;
import org.anson.miniProject.tool.helper.InputParamHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional(rollbackFor = Exception.class)
public class OperLogRep extends BaseRep<OperLog, OperLogMapper> {

    public String insert(OperLog entity, String operUserId, Date operTime){
        // 必填检查
        Object[] valArray = {entity.getOperUserId(), entity.getOperTypeKey(), entity.getClientKey(),
                             entity.getAreSuccessful()};
        String[] errArray = {"请输入操作用户id", "请选择操作类型key", "请选择客户端key",
                             "请输入是否操作成功"};
        InputParamHelper.required(valArray, errArray);

        // 数据库不存空的字段
        InputParamHelper.nullToEmpty(entity.getOperMenuId(), entity.getToMenuId(), entity.getIpv4(),
                                     entity.getPk(), entity.getMainTableName(), entity.getDescription(),
                                     entity.getFailReason());

        // 伪外键检查
        {
            // 检查 operUserId

            // 检查 operMenuId

            // 检查 toMenuId
        }

        entity.setOperTime(operTime);

        entity.setId(IdHelper.nextSnowflakeId());
        entity.setCreateUserId(operUserId);
        entity.setCreateTime(operTime);
        entity.setLastUpdateTime(operTime);

        this.mapper.insert(entity);

        return entity.getId();
    }

    // 注入
    @Autowired
    public void setMapper(OperLogMapper mapper){
        this.mapper = mapper;
    }
}
