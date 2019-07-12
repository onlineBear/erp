package org.anson.miniProject.core.repository.sys;

import org.anson.miniProject.core.mapper.sys.DelRecordMapper;
import org.anson.miniProject.core.model.po.sys.DeletedRecord;
import org.anson.miniProject.core.repository.BaseRep;
import org.anson.miniProject.tool.helper.IdHelper;
import org.anson.miniProject.tool.helper.InputParamHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional(rollbackFor = Exception.class)
public class DeletedRecordRep extends BaseRep<DeletedRecord, DelRecordMapper> {
    public String insert(DeletedRecord po, String operUserId, Date operTime) {
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

    // set
    @Autowired
    public void setMapper(DelRecordMapper mapper){
        this.mapper = mapper;
    }
}
