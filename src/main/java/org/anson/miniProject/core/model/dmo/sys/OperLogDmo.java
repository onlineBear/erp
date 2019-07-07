package org.anson.miniProject.core.model.dmo.sys;

import cn.hutool.core.collection.IterUtil;
import lombok.Data;
import org.anson.miniProject.core.model.po.sys.OperLog;
import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class OperLogDmo {
    private String id;

    private String operUserId;
    private String operMenuId;
    private String toMenuId;

    private String operTypeKey;
    private String clientKey;

    private Date operTime;
    private String ipv4;
    private String pk;
    private String mainTableName;
    private String description;
    private Boolean areSuccessful;
    private String failReason;
    private Double longitude;
    private Double latitude;

    private String createUserId;
    private Date createTime;
    private Date lastUpdateTime;

    private static final BeanCopier bo2entityCopier = BeanCopier.create(OperLogDmo.class, OperLog.class, false);

    public static OperLog bo2entity(OperLogDmo bo){
        if(bo == null){
            return null;
        }

        OperLog entity = new OperLog();

        bo2entityCopier.copy(bo, entity, null);

        return entity;
    }

    public static List<OperLog> bo2entity(List<OperLogDmo> boList){
        if(IterUtil.isEmpty(boList)){
            return null;
        }

        List<OperLog> entityList = new ArrayList<>();

        for(OperLogDmo bo : boList){
            entityList.add(bo2entity(bo));
        }

        return entityList;
    }
}
