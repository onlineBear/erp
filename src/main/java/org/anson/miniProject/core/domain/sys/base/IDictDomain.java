package org.anson.miniProject.core.domain.sys.base;

import org.anson.miniProject.core.model.param.sys.base.dict.AddDictParam;
import org.anson.miniProject.core.model.param.sys.base.dict.MdfDictParam;

import java.util.Date;
import java.util.List;

public interface IDictDomain {
    String add(AddDictParam param, String operUserId, Date operTime) throws Exception;
    void batchAdd(List<AddDictParam> paramList, String operUserId, Date operTime) throws Exception;
    void mdf(MdfDictParam param, String operUserId, Date operTime) throws Exception;
    void batchMdf(List<MdfDictParam> paramList, String operUserId, Date operTime) throws Exception;
    void del(String dictId, String operUserId, Date operTime) throws Exception;
    void delByDictTypeId(String dictTypeId, String operUserId, Date operTime) throws Exception;
}
