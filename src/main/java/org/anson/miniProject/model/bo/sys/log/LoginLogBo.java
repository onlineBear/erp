package org.anson.miniProject.model.bo.sys.log;

import cn.hutool.core.collection.IterUtil;
import lombok.Data;
import org.anson.miniProject.model.entity.sys.log.LoginLog;
import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class LoginLogBo {
    private String id;

    private String userId;

    private String loginTypeKey;

    private Date operTime;
    private String loginUserNo;
    private Boolean areSuccessful;
    private String failReason;
    private String ipv4;
    private Double longitude;
    private Double latitude;

    private String createUserId;
    private Date createTime;
    private Date lastUpdateTime;

    private static final BeanCopier bo2entityCopier = BeanCopier.create(LoginLogBo.class, LoginLog.class, false);

    public static LoginLog bo2entity(LoginLogBo bo){
        if(bo == null){
            return null;
        }

        LoginLog entity = new LoginLog();

        bo2entityCopier.copy(bo, entity, null);

        return entity;
    }

    public static List<LoginLog> bo2entity(List<LoginLogBo> boList){
        if(IterUtil.isEmpty(boList)){
            return null;
        }

        List<LoginLog> entityList = new ArrayList<>();

        for(LoginLogBo bo : boList){
            entityList.add(bo2entity(bo));
        }

        return entityList;
    }
}
