package org.anson.miniProject.core.domain.sys;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.anson.miniProject.core.model.dmo.sys.DictTypeDmo;

import java.util.Date;

public interface IDictTypeDomain {
    String add(DictTypeDmo bo, String operUserId, Date operTime);
    void mdf(DictTypeDmo bo, String operUserId, Date operTime);
    void del(String dictTypeId, String operUserId, Date operTime) throws JsonProcessingException;
}
