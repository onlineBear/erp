package org.anson.miniProject.domain.sys.impl;

import org.anson.miniProject.domain.sys.IDictDomain;
import org.anson.miniProject.model.bo.sys.DictBo;
import org.anson.miniProject.model.entity.sys.Dict;
import org.anson.miniProject.repository.sys.DictRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional(rollbackFor = Exception.class)
public class DictDomain implements IDictDomain {
    @Autowired
    private DictRep dictRep;

    @Override
    public String add(DictBo bo, String operUserId, Date operTime) {
        // 新增数据字典
        Dict entity = DictBo.bo2entity(bo);

        return this.dictRep.insert(entity, operUserId, operTime);
    }

    @Override
    public void mdf(DictBo bo, String operUserId, Date operTime) {
        // 修改数据字典
        Dict entity = DictBo.bo2entity(bo);

        this.dictRep.update(entity, operUserId, operTime);
    }

    @Override
    public void del(String dictId) {
        // 删除数据字典
        this.dictRep.del(dictId);
    }
}
