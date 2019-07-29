package org.anson.miniProject.domain.base.deletedRecord;

import org.anson.miniProject.domain.base.BaseDao;
import org.anson.miniProject.tool.helper.IdHelper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional(rollbackFor = Exception.class)
class DeletedRecordDao extends BaseDao<DeletedRecord, DeletedRecordMapper> {
    public String insert(DeletedRecord deletedRecord) throws Exception{
        deletedRecord.setDeletedUserId(operUserId);
        deletedRecord.setDeletedTime(operTime);

        deletedRecord.setId(IdHelper.nextSnowflakeId());

        deletedRecord.setCreateUserId(operUserId);
        deletedRecord.setCreateTime(operTime);
        deletedRecord.setLastUpdateTime(operTime);

        this.mapper.insert(deletedRecord);

        return deletedRecord.getId();
    }

    // 注入
    @Override
    protected void setMapper(DeletedRecordMapper mapper) {
        this.mapper = mapper;
    }

    private String operUserId = "operUserId";
    private Date operTime = new Date();
}
