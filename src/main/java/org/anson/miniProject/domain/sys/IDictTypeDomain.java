package org.anson.miniProject.domain.sys;

import org.anson.miniProject.model.bo.sys.DictTypeBo;

import java.util.Date;

public interface IDictTypeDomain {
    String add(DictTypeBo bo, String operUserId, Date operTime);
    void mdf(DictTypeBo bo, String operUserId, Date operTime);
    void del(String dictTypeId, String operUserId, Date operTime);
}
