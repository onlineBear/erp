package org.anson.miniProject.model.bo.sys.log;

import cn.hutool.core.collection.IterUtil;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.model.entity.sys.log.LoginLog;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Builder
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

    public static LoginLog boToentity(LoginLogBo bo) {
        if (bo == null) {
            return null;
        }

        LoginLog entity = new LoginLog();

        bo2entityCopier.copy(bo, entity, null);

        return entity;
    }

    public List<LoginLog> boToentity(List<LoginLogBo> boList) {
        if(IterUtil.isEmpty(boList)){
            return null;
        }

        List<LoginLog> entityList = new ArrayList<>();

        for(LoginLogBo bo : boList){
            entityList.add(boToentity(bo));
        }

        return entityList;
    }

    public static LoginLogBo mapToBeginLogin(Map dtoMap, Date operTime) throws InstantiationException, IllegalAccessException {
        dtoMap.put("operTime", operTime);
        dtoMap.put("loginUserNo", "no");
        String[] keyArray = {"loginTypeKey", "operTime", "loginUserNo", "ipv4"};
        LoginLogBo bo = BeanHelper.mapToBo(dtoMap, keyArray, LoginLogBo.class);
        return bo;
    }
}
