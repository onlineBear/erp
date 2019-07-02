package org.anson.miniProject.model.bo.sys;

import cn.hutool.core.collection.IterUtil;
import lombok.Data;
import org.anson.miniProject.model.entity.sys.DelRecord;
import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class DelRecordBo {
    private String id;

    private String tableName;
    private String pk;
    private String record;
    private String deletedUserId;
    private Date deletedTime;

    private String createUserId;
    private Date createTime;
    private Date lastUpdateTime;

    public DelRecordBo(){

    }

    public DelRecordBo(String tableName, String pk, String record){
        this.tableName = tableName;
        this.pk = pk;
        this.record = record;
    }

    private static final BeanCopier bo2entityCopier = BeanCopier.create(DelRecordBo.class, DelRecord.class, false);

    public static DelRecord bo2entity(DelRecordBo bo){
        if(bo == null){
            return null;
        }

        DelRecord entity = new DelRecord();

        bo2entityCopier.copy(bo, entity, null);

        return entity;
    }

    public static List<DelRecord> bo2entity(List<DelRecordBo> boList){
        if(IterUtil.isEmpty(boList)){
            return null;
        }

        List<DelRecord> entityList = new ArrayList<>();

        for(DelRecordBo bo : boList){
            entityList.add(bo2entity(bo));
        }

        return entityList;
    }
}
