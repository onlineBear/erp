package org.anson.miniProject.core.model.dmo.sys;

import cn.hutool.core.collection.IterUtil;
import lombok.Data;
import org.anson.miniProject.core.model.po.sys.OperLog;
import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class OperLogBo {
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

    private static final BeanCopier bo2entityCopier = BeanCopier.create(OperLogBo.class, OperLog.class, false);

    public static OperLog bo2entity(OperLogBo bo){
        if(bo == null){
            return null;
        }

        OperLog entity = new OperLog();

        bo2entityCopier.copy(bo, entity, null);

        return entity;
    }

    public static List<OperLog> bo2entity(List<OperLogBo> boList){
        if(IterUtil.isEmpty(boList)){
            return null;
        }

        List<OperLog> entityList = new ArrayList<>();

        for(OperLogBo bo : boList){
            entityList.add(bo2entity(bo));
        }

        return entityList;
    }
}
