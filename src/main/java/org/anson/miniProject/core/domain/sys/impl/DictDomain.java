package org.anson.miniProject.core.domain.sys.impl;

import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.anson.miniProject.core.domain.sys.IDictDomain;
import org.anson.miniProject.core.model.dmo.sys.DictDmo;
import org.anson.miniProject.core.model.po.sys.Dict;
import org.anson.miniProject.core.repository.sys.DictRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Component
@Transactional(rollbackFor = Exception.class)
public class DictDomain implements IDictDomain {
    @Autowired
    private DictRep rep;

    @Override
    public String add(DictDmo bo, String operUserId, Date operTime) {
        // 新增数据字典
        Dict entity = DictDmo.bo2entity(bo);

        return this.rep.insert(entity, operUserId, operTime);
    }

    @Override
    public void batchAdd(List<DictDmo> boList, String operUserId, Date operTime){
        if (IterUtil.isNotEmpty(boList)){
            for (DictDmo bo : boList){
                this.add(bo, operUserId, operTime);
            }
        }
    }

    @Override
    public void mdf(DictDmo bo, String operUserId, Date operTime) {
        // 修改数据字典
        Dict entity = DictDmo.bo2entity(bo);

        this.rep.update(entity, operTime);
    }

    @Override
    public void batchMdf(List<DictDmo> boList, String operUserId, Date operTime) {
        if (IterUtil.isNotEmpty(boList)){
            for (DictDmo bo : boList){
                this.mdf(bo, operUserId, operTime);
            }
        }
    }

    @Override
    public String save(DictDmo bo, String operUserId, Date operTime) {
        if (StrUtil.isEmpty(bo.getId())) {
            return this.add(bo, operUserId, operTime);
        }else {
            this.mdf(bo, operUserId, operTime);
            return bo.getId();
        }
    }

    @Override
    public void batchSave(List<DictDmo> boList, String operUserId, Date operTime) {
        if (IterUtil.isNotEmpty(boList)) {
            for (DictDmo bo : boList) {
                this.save(bo, operUserId, operTime);
            }
        }
    }

    @Override
    public void del(String dictId, String operUserId, Date operTime) throws JsonProcessingException {
        this.rep.delByDictType(dictId, operUserId, operTime);
    }

    @Override
    public void delByDictTypeId(String dictTypeId, String operUserId, Date operTime) throws JsonProcessingException {
        this.rep.delByDictType(dictTypeId, operUserId, operTime);
    }
}
