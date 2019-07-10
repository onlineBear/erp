package org.anson.miniProject.core.domain.sys;

import org.anson.miniProject.core.model.dmo.sys.dictType.AddDictTypeDmo;
import org.anson.miniProject.core.model.dmo.sys.dictType.MdfDictTypeDmo;

import java.util.Date;

public interface IDictTypeDomain {
    String add(AddDictTypeDmo dmo, String operUserId, Date operTime) throws Exception;
    void mdf(MdfDictTypeDmo dmo, String operUserId, Date operTime) throws Exception;
    void del(String dictTypeId, String operUserId, Date operTime) throws Exception;
}
