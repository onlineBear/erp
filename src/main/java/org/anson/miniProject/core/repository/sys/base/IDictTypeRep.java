package org.anson.miniProject.core.repository.sys.base;

import org.anson.miniProject.core.mapper.sys.DictTypeMapper;
import org.anson.miniProject.core.model.po.sys.base.DictType;
import org.anson.miniProject.core.repository.IBaseRep;

import java.util.Date;

public interface IDictTypeRep extends IBaseRep<DictType, DictTypeMapper> {
    String insert(DictType entity, String operUserId, Date operTime) throws Exception;
    void update(DictType entity, String operUserId, Date operTime) throws Exception;
    void del(String dictTypeId, String operUserId, Date operTime) throws Exception;
}
