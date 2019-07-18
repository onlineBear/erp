package org.anson.miniProject.core.repository.sys.base;

import org.anson.miniProject.core.mapper.sys.DictMapper;
import org.anson.miniProject.core.model.po.sys.base.Dict;
import org.anson.miniProject.core.repository.IBaseRep;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface IDictRep extends IBaseRep<Dict, DictMapper> {
    String insert(Dict entity, String operUserId, Date operTime) throws Exception;
    void insert(List<Dict> entityList, String operUserId, Date operTime) throws Exception;
    void update(Dict entity, Date operTime) throws Exception;
    String save(Dict entity, String operUserId, Date operTime) throws Exception;
    void save(List<Dict> entityList, String operUserId, Date operTime) throws Exception;
    void del(String dictId, String operUserId, Date operTime) throws Exception;
    void del(Collection<? extends String> idList, String operUserId, Date operTime) throws Exception;
    void delByDictType(String dictTypeId, String operUserId, Date operTime) throws Exception;

    List<Dict> selByDictTypeId(String dictTypeId);
}
