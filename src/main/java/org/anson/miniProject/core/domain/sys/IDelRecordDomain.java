package org.anson.miniProject.core.domain.sys;

import org.anson.miniProject.core.model.dmo.sys.DelRecordDMO;

import java.util.Date;
import java.util.List;

public interface IDelRecordDomain {
    String record(DelRecordDMO bo, String operUserId, Date operTime);
    void record(List<DelRecordDMO> boList, String operUserId, Date operTime);
}
