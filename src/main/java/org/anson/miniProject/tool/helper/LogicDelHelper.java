package org.anson.miniProject.tool.helper;

import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.core.model.po.BasePO;
import org.anson.miniProject.core.model.po.sys.log.DeletedRecord;
import org.anson.miniProject.core.repository.sys.log.impl.DeletedRecordRep;
import org.anson.miniProject.framework.jackson.Jackson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 逻辑删除 helper
 */
@Component
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class LogicDelHelper {
    @Autowired
    private DeletedRecordRep rep;
    @Autowired
    private Jackson jackson;

    public <P extends BasePO> String recordDelData(P po, String operUserId, Date operTime) throws Exception {
        DeletedRecord record = DeletedRecord.builder()
                                            .tableName(po.TABLENAME())
                                            .pk(po.getId())
                                            .record(jackson.toJson(po))
                                            .build();

        return this.rep.insert(record, operUserId, operTime);
    }
}
