package org.anson.miniProject.core.repository.sys.log;

import org.anson.miniProject.core.mapper.sys.log.OperLogMapper;
import org.anson.miniProject.core.model.po.sys.log.OperLog;
import org.anson.miniProject.core.repository.IBaseRep;

import java.util.Date;

public interface IOperLogRep extends IBaseRep<OperLog, OperLogMapper> {
    String insert(OperLog po, String operUserId, Date operTime);
}
