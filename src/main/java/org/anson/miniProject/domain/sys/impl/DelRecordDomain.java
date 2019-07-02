package org.anson.miniProject.domain.sys.impl;

import cn.hutool.core.collection.IterUtil;
import org.anson.miniProject.domain.sys.IDelRecordDomain;
import org.anson.miniProject.model.bo.sys.DelRecordBo;
import org.anson.miniProject.model.entity.sys.DelRecord;
import org.anson.miniProject.repository.sys.DelRecordRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Component
@Transactional(rollbackFor = Exception.class)
public class DelRecordDomain implements IDelRecordDomain {
    @Autowired
    private DelRecordRep rep;

    @Override
    public String record(DelRecordBo bo, String operUserId, Date operTime) {
        DelRecord delRecord = DelRecordBo.bo2entity(bo);
        return this.rep.insert(delRecord, operUserId, operTime);
    }

    @Override
    public void record(List<DelRecordBo> boList, String operUserId, Date operTime) {
        if (IterUtil.isNotEmpty(boList)){
            for (DelRecordBo bo : boList){
                this.record(bo, operUserId, operTime);
            }
        }
    }
}
