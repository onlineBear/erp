package org.anson.miniProject.domain.sys;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.anson.miniProject.model.bo.sys.DictBo;

import java.util.Date;

public interface IDictDomain {
    String add(DictBo bo, String operUserId, Date operTime);
    void mdf(DictBo bo, String operUserId, Date operTime);
    void del(String dictId, String operUserId, Date operTime) throws JsonProcessingException;
}
