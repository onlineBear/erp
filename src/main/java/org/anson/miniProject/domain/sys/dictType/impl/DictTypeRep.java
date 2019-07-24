package org.anson.miniProject.domain.sys.dictType.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(rollbackFor = Exception.class)
class DictTypeRep {
    public String insert(DictTypeEntity entity) throws Exception{
        DictType po = Translator.toDictTypePO(entity);
        String id = this.dao.insert(po);
        return id;
    }

    @Autowired
    private DictTypeDao dao;
}
