package org.anson.miniProject.core.domain.sys;

import org.anson.miniProject.core.model.dmo.sys.DelRecordBo;

import java.util.Date;
import java.util.List;

public interface IDelRecordDomain {
    String record(DelRecordBo bo, String operUserId, Date operTime);
    void record(List<DelRecordBo> boList, String operUserId, Date operTime);
}
