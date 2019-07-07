package org.anson.miniProject.core.domain.sys.impl;

import cn.hutool.core.collection.IterUtil;
import org.anson.miniProject.core.domain.sys.IDelRecordDomain;
import org.anson.miniProject.core.model.dmo.sys.DelRecordDmo;
import org.anson.miniProject.core.model.po.sys.DelRecord;
import org.anson.miniProject.core.repository.sys.DelRecordRep;
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
    public String record(DelRecordDmo bo, String operUserId, Date operTime) {
        DelRecord delRecord = DelRecordDmo.bo2entity(bo);
        return this.rep.insert(delRecord, operUserId, operTime);
    }

    @Override
    public void record(List<DelRecordDmo> boList, String operUserId, Date operTime) {
        if (IterUtil.isNotEmpty(boList)){
            for (DelRecordDmo bo : boList){
                this.record(bo, operUserId, operTime);
            }
        }
    }
}
