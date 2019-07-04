package org.anson.miniProject.repository.sys.log;

import org.anson.miniProject.mapper.sys.log.LoginLogMapper;
import org.anson.miniProject.model.entity.sys.log.LoginLog;
import org.anson.miniProject.repository.BaseRep;
import org.anson.miniProject.tool.helper.IdHelper;
import org.anson.miniProject.tool.helper.InputParamHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class LoginLogRep extends BaseRep<LoginLog, LoginLogMapper> {

    public String insert(LoginLog entity, String operUserId, Date operTime){
        entity.setUserId(operUserId);
        entity.setOperTime(operTime);

        // 必填检查
        Object[] valArray = {entity.getOperTime(), entity.getLoginTypeKey(), entity.getAreSuccessful()};
        String[] errArray = {"请输入操作时间", "请选择登录类型key", "请输入是否成功"};
        InputParamHelper.required(valArray, errArray);

        // 数据库不存空的字段
        InputParamHelper.nullToEmpty(entity.getUserId(), entity.getLoginUserNo(), entity.getFailReason(),
                                     entity.getIpv4());

        entity.setId(IdHelper.nextSnowflakeId());
        entity.setCreateUserId(operUserId);
        entity.setCreateTime(operTime);
        entity.setLastUpdateTime(operTime);

        this.mapper.insert(entity);
        return entity.getId();
    }

    // 注入
    @Autowired
    public void setMapper(LoginLogMapper mapper){
        this.mapper = mapper;
    }
}
