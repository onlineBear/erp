package org.anson.miniProject.domain.sys.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.anson.miniProject.domain.sys.IDelRecordDomain;
import org.anson.miniProject.domain.sys.IDictDomain;
import org.anson.miniProject.framework.jackson.Jackson;
import org.anson.miniProject.model.bo.sys.DelRecordBo;
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
    @Autowired
    private IDelRecordDomain delRecordDomain;
    @Autowired
    private Jackson jackson;

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
    public void del(String dictId, String operUserId, Date operTime) throws JsonProcessingException {
        // 检查是否有这个数据字典
        Dict dict = this.dictRep.selectById(dictId);

        if (dict == null){
            return;
        }

        // 记录删除的数据
        DelRecordBo delRecordBo = new DelRecordBo(Dict.__TABLENAME, dictId, jackson.toJson(dict));
        this.delRecordDomain.record(delRecordBo, operUserId, operTime);

        // 删除数据字典
        this.dictRep.del(dictId);
    }
}
