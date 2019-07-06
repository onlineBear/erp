package org.anson.miniProject.core.domain.sys;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.anson.miniProject.core.model.dmo.sys.DictTypeBo;

import java.util.Date;

public interface IDictTypeDomain {
    String add(DictTypeBo bo, String operUserId, Date operTime);
    void mdf(DictTypeBo bo, String operUserId, Date operTime);
    void del(String dictTypeId, String operUserId, Date operTime) throws JsonProcessingException;
}
