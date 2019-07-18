package org.anson.miniProject.core.repository.sys.log.impl;

import org.anson.miniProject.core.mapper.sys.DeletedRecordMapper;
import org.anson.miniProject.core.model.po.sys.log.DeletedRecord;
import org.anson.miniProject.core.repository.BaseRep;
import org.anson.miniProject.core.repository.sys.log.IDeletedRecoredRep;
import org.anson.miniProject.tool.helper.IdHelper;
import org.anson.miniProject.tool.helper.InputParamHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional(rollbackFor = Exception.class)
public class DeletedRecordRep extends BaseRep<DeletedRecord, DeletedRecordMapper>
                              implements IDeletedRecoredRep {
    // 接口命令(需要事务)
    @Override
    public String insert(DeletedRecord po, String operUserId, Date operTime) throws Exception{
        // 必填检查
        Object[] valArray = {po.getRecord(), po.getTableName()};
        String[] errArray = {"请输入删除的数据", "请输入表名"};
        InputParamHelper.required(valArray, errArray);

        po.setDeletedUserId(operUserId);
        po.setDeletedTime(operTime);

        po.setId(IdHelper.nextSnowflakeId());

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
    public void setMapper(DeletedRecordMapper mapper){
        this.mapper = mapper;
    }
}
