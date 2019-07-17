package org.anson.miniProject.core.repository.sys.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.anson.miniProject.core.mapper.sys.DictMapper;
import org.anson.miniProject.core.model.po.sys.Dict;
import org.anson.miniProject.core.repository.IBaseRep;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface IDictRep extends IBaseRep<Dict, DictMapper> {
    String insert(Dict entity, String operUserId, Date operTime);
    void insert(List<Dict> entityList, String operUserId, Date operTime);
    void update(Dict entity, Date operTime);
    String save(Dict entity, String operUserId, Date operTime);
    void save(List<Dict> entityList, String operUserId, Date operTime);
    void del(String dictId, String operUserId, Date operTime) throws JsonProcessingException;
    void del(Collection<? extends String> idList, String operUserId, Date operTime) throws JsonProcessingException;
    void delByDictType(String dictTypeId, String operUserId, Date operTime) throws JsonProcessingException;

    Boolean isExistsById(Serializable id);
    List<Dict> selByDictTypeId(String dictTypeId);
}
