package org.anson.miniProject.domain.sys.impl;

import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public void batchAdd(List<DictBo> boList, String operUserId, Date operTime){
        if (IterUtil.isNotEmpty(boList)){
            for (DictBo bo : boList){
                this.add(bo, operUserId, operTime);
            }
        }
    }

    @Override
    public void mdf(DictBo bo, String operUserId, Date operTime) {
        // 修改数据字典
        Dict entity = DictBo.bo2entity(bo);

        this.dictRep.update(entity, operTime);
    }

    @Override
    public void batchMdf(List<DictBo> boList, String operUserId, Date operTime) {
        if (IterUtil.isNotEmpty(boList)){
            for (DictBo bo : boList){
                this.mdf(bo, operUserId, operTime);
            }
        }
    }

    @Override
    public String save(DictBo bo, String operUserId, Date operTime) {
        if (StrUtil.isEmpty(bo.getId())) {
            return this.add(bo, operUserId, operTime);
        }else {
            this.mdf(bo, operUserId, operTime);
            return bo.getId();
        }
    }

    @Override
    public void batchSave(List<DictBo> boList, String operUserId, Date operTime) {
        if (IterUtil.isNotEmpty(boList)) {
            for (DictBo bo : boList) {
                this.save(bo, operUserId, operTime);
            }
        }
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
        this.dictRep.delByDictType(dictId);
    }

    @Override
    public void delByDictTypeId(String dictTypeId, String operUserId, Date operTime) throws JsonProcessingException {
        List<Dict> dictList = this.dictRep.selByDictTypeId(dictTypeId);

        if (IterUtil.isEmpty(dictList)) {
            return;
        }

        // 记录删除的数据
        List<String> dictIdList = new ArrayList<>();
        for (Dict dict : dictList) {
            DelRecordBo delRecordBo = new DelRecordBo(Dict.__TABLENAME, dict.getId(), jackson.toJson(dict));
            this.delRecordDomain.record(delRecordBo, operUserId, operTime);
            dictIdList.add(dict.getId());
        }

        // 删除数据字典
        this.dictRep.del(dictIdList);
    }
}