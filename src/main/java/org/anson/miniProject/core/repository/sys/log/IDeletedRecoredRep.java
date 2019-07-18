package org.anson.miniProject.core.repository.sys.log;

import org.anson.miniProject.core.mapper.sys.DeletedRecordMapper;
import org.anson.miniProject.core.model.po.sys.log.DeletedRecord;
import org.anson.miniProject.core.repository.IBaseRep;

import java.util.Date;

public interface IDeletedRecoredRep extends IBaseRep<DeletedRecord, DeletedRecordMapper> {
    String insert(DeletedRecord po, String operUserId, Date operTime) throws Exception;
}