package org.anson.miniProject.core.repository.sys.log;

import org.anson.miniProject.core.mapper.sys.log.OperLogMapper;
import org.anson.miniProject.core.model.po.sys.log.OperLog;
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

    public String insert(OperLog po, String operUserId, Date operTime){
        po.setOperTime(operTime);
        // 必填检查
        Object[] valArray = {po.getOperUserId(), po.getOperMenuId(), po.getOperTypeKey(),
                            po.getClientKey(), po.getOperTime(), po.getAreSuccessful(),
                            po.getMainTableName()};
        String[] errArray = {"请输入操作用户id", "请输入操作菜单id", "请选择操作类型key",
                            "请选择客户端key", "请输入操作时间", "请输入是否操作成功",
                            "请输入主表"};
        InputParamHelper.required(valArray, errArray);

        // 伪外键检查
        {
            // 检查 operUserId

            // 检查 operMenuId

            // 检查 toMenuId
        }

        po.setId(IdHelper.nextSnowflakeId());
        po.setCreateUserId(operUserId);
        po.setCreateTime(operTime);
        po.setLastUpdateTime(operTime);

        this.mapper.insert(po);

        return po.getId();
    }

    // 注入
    @Autowired
    public void setMapper(OperLogMapper mapper){
        this.mapper = mapper;
    }
}
