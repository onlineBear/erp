package org.anson.miniProject.domain.internal.deletedRecord;

import org.anson.miniProject.domain.base.BaseDao;
import org.anson.miniProject.tool.helper.IdHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional(rollbackFor = Exception.class)
class DeletedRecordDao extends BaseDao<DeletedRecord, DeletedRecordMapper> {
    public String insert(DeletedRecord deletedRecord){
        deletedRecord.setDeletedTime(operTime);

        deletedRecord.setId(IdHelper.nextSnowflakeId());

        deletedRecord.setCreateTime(operTime);
        deletedRecord.setUpdateTime(operTime);

        this.mapper.insert(deletedRecord);

        return deletedRecord.getId();
    }

    // 注入
    @Autowired
    @Override
    protected void setMapper(DeletedRecordMapper mapper) {
        this.mapper = mapper;
    }

    private Date operTime = new Date();
}
