package org.anson.miniProject.core.domain.sys;

import org.anson.miniProject.core.model.dmo.sys.DelRecordDmo;

import java.util.Date;
import java.util.List;

public interface IDelRecordDomain {
    String record(DelRecordDmo bo, String operUserId, Date operTime);
    void record(List<DelRecordDmo> boList, String operUserId, Date operTime);
}
