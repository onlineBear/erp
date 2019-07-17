package org.anson.miniProject.core.repository.sys.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.anson.miniProject.core.mapper.sys.DictTypeMapper;
import org.anson.miniProject.core.model.po.sys.DictType;
import org.anson.miniProject.core.repository.IBaseRep;

import java.util.Date;

public interface IDictTypeRep extends IBaseRep<DictType, DictTypeMapper> {
    String insert(DictType entity, String operUserId, Date operTime);
    void update(DictType entity, String operUserId, Date operTime);
    String save(DictType entity, String operUserId, Date operTime);
    void del(String dictTypeId, String operUserId, Date operTime) throws JsonProcessingException;
}
