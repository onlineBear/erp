package org.anson.miniProject.core.repository.sys.log.impl;

import org.anson.miniProject.core.mapper.sys.log.OperLogMapper;
import org.anson.miniProject.core.model.po.sys.log.OperLog;
import org.anson.miniProject.core.repository.BaseRep;
import org.anson.miniProject.core.repository.sys.log.IOperLogRep;
import org.anson.miniProject.tool.helper.IdHelper;
import org.anson.miniProject.tool.helper.InputParamHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional(rollbackFor = Exception.class)
public class OperLogRep extends BaseRep<OperLog, OperLogMapper>
                        implements IOperLogRep {
    // 接口命令(需要事务)
    @Override
    public String insert(OperLog po, String operUserId, Date operTime) throws Exception{
        // 必填检查
        Object[] valArray = {po.getOperUserId(), po.getOperMenuId(),
                            po.getClientKey(), po.getAreSuccessful(),
                            po.getMainTableName()};
        String[] errArray = {"请输入操作用户id", "请输入操作菜单id",
                            "请选择客户端key", "请输入是否操作成功",
                            "请输入主表"};
        InputParamHelper.required(valArray, errArray);

        po.setId(IdHelper.nextSnowflakeId());
        po.setOperTime(operTime);

        po.setCreateUserId(operUserId);
        po.setCreateTime(operTime);
        po.setLastUpdateTime(operTime);

        this.mapper.insert(po);

        return po.getId();
    }

    // 接口查询(只读事务)

    // 非接口命令(需要事务)

    // 非接口查询(只读事务)

    // 私有方法(没有事务)

    // 注入
    @Override
    @Autowired
    public void setMapper(OperLogMapper mapper){
        this.mapper = mapper;
    }
}
