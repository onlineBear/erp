package org.anson.miniProject.core.model.dmo.sys;

import cn.hutool.core.collection.IterUtil;
import lombok.Builder;
import lombok.Data;
import org.anson.miniProject.core.model.po.sys.DelRecord;
import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class DelRecordDMO {
    private String id;

    private String tableName;
    private String pk;
    private String record;
    private String deletedUserId;
    private Date deletedTime;

    private String createUserId;
    private Date createTime;
    private Date lastUpdateTime;

    private static final BeanCopier bo2entityCopier = BeanCopier.create(DelRecordDMO.class, DelRecord.class, false);

    public static DelRecord bo2entity(DelRecordDMO bo){
        if(bo == null){
            return null;
        }

        DelRecord entity = new DelRecord();

        bo2entityCopier.copy(bo, entity, null);

        return entity;
    }

    public static List<DelRecord> bo2entity(List<DelRecordDMO> boList){
        if(IterUtil.isEmpty(boList)){
            return null;
        }

        List<DelRecord> entityList = new ArrayList<>();

        for(DelRecordDMO bo : boList){
            entityList.add(bo2entity(bo));
        }

        return entityList;
    }
}
