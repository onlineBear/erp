package org.anson.miniProject.domain.internal.loginLog.impl;

import org.anson.miniProject.domain.base.BaseDao;
import org.anson.miniProject.tool.helper.IdHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional(rollbackFor = Exception.class)
class LoginLogDao extends BaseDao<LoginLog, LoginLogMapper> {

    public String insert(LoginLog loginLog){
        loginLog.setId(IdHelper.nextSnowflakeId());

        loginLog.setCreateUserId(operUserId); // operUserId 在这里, 有可能是没有的(登录前是没有的)
        loginLog.setCreateTime(operTime);
        loginLog.setLastUpdateTime(operTime);

        this.mapper.insert(loginLog);
        return loginLog.getId();
    }

    // 注入
    @Autowired
    @Override
    protected void setMapper(LoginLogMapper mapper) {
        this.mapper = mapper;
    }

    private String operUserId = "operUserId";
    private Date operTime = new Date();
}
