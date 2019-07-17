package org.anson.miniProject.core.domain.sys.base;

import org.anson.miniProject.core.model.param.sys.dictType.AddDictTypeParam;
import org.anson.miniProject.core.model.param.sys.dictType.MdfDictTypeParam;

import java.util.Date;

public interface IDictTypeDomain {
    String add(AddDictTypeParam dmo, String operUserId, Date operTime) throws Exception;
    void mdf(MdfDictTypeParam dmo, String operUserId, Date operTime) throws Exception;
    void del(String dictTypeId, String operUserId, Date operTime) throws Exception;
}
