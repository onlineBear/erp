package org.anson.miniProject.domain.sys;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.anson.miniProject.model.bo.sys.DictBo;

import java.util.Date;
import java.util.List;

public interface IDictDomain {
    String add(DictBo bo, String operUserId, Date operTime);
    void batchAdd(List<DictBo> boList, String operUserId, Date operTime);
    void mdf(DictBo bo, String operUserId, Date operTime);
    void batchMdf(List<DictBo> boList, String operUserId, Date operTime);
    String save(DictBo bo, String operUserId, Date operTime);
    void batchSave(List<DictBo> boList, String operUserId, Date operTime);
    void del(String dictId, String operUserId, Date operTime) throws JsonProcessingException;
    void delByDictTypeId(String dictTypeId, String operUserId, Date operTime) throws JsonProcessingException;
}
