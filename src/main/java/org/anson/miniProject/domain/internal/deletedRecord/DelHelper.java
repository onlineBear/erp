package org.anson.miniProject.domain.internal.deletedRecord;

import org.anson.miniProject.domain.base.BasePO;
import org.anson.miniProject.framework.jackson.Jackson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional(rollbackFor = Exception.class)
public class DelHelper {

    public <P extends BasePO> String recordDelData(P po) throws Exception {
        DeletedRecord record = new DeletedRecord();
        record.setTableName(po.TABLENAME());
        record.setPk(po.getId());
        record.setRecord(jackson.toJson(po));
        record.setDeletedTime(operTime);
        record.setDeletedUserId(operUserId);

        return this.dao.insert(record);
    }

    // 注入
    @Autowired
    private Jackson jackson;
    @Autowired
    private DeletedRecordDao dao;
    private String operUserId = "operUserId";
    private Date operTime = new Date();
}
