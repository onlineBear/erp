package org.anson.miniProject.core.domain.sys;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.anson.miniProject.core.model.dmo.sys.DictDmo;

import java.util.Date;
import java.util.List;

public interface IDictDomain {
    String add(DictDmo bo, String operUserId, Date operTime);
    void batchAdd(List<DictDmo> boList, String operUserId, Date operTime);
    void mdf(DictDmo bo, String operUserId, Date operTime);
    void batchMdf(List<DictDmo> boList, String operUserId, Date operTime);
    String save(DictDmo bo, String operUserId, Date operTime);
    void batchSave(List<DictDmo> boList, String operUserId, Date operTime);
    void del(String dictId, String operUserId, Date operTime) throws JsonProcessingException;
    void delByDictTypeId(String dictTypeId, String operUserId, Date operTime) throws JsonProcessingException;
}
