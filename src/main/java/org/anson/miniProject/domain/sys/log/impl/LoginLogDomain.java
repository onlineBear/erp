package org.anson.miniProject.domain.sys.log.impl;

import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.domain.sys.log.ILoginLogDomain;
import org.anson.miniProject.model.bo.sys.log.BeginLoginDto;
import org.anson.miniProject.model.bo.sys.log.LoginLogBo;
import org.anson.miniProject.model.entity.sys.log.LoginLog;
import org.anson.miniProject.repository.sys.log.LoginLogRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class LoginLogDomain implements ILoginLogDomain {
    @Autowired
    private LoginLogRep rep;

    @Override
    // 事务传播属性为 Propagation.REQUIRES_NEW, 新建事务，如果当前存在事务，把当前事务挂起。
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public String beginLogin(BeginLoginDto dto) {
        LoginLog entity = BeginLoginDto.toEntity(dto);
        entity.setAreSuccessful(false);
        return this.rep.insert(entity, "", dto.getOperTime());
    }

    @Override
    // 事务传播属性为 Propagation.REQUIRES_NEW, 新建事务，如果当前存在事务，把当前事务挂起。
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void loginSuccess(String loginLogId, String userId, Date operTime) {
        LoginLog entity = new LoginLog();
        entity.setId(loginLogId);
        entity.setAreSuccessful(true);
        entity.setUserId(userId);

        this.rep.update(entity, operTime);
    }

    @Override
    // 事务传播属性为 Propagation.REQUIRES_NEW, 新建事务，如果当前存在事务，把当前事务挂起。
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void loginFail(String loginLogId, String failReason, Date operTime) {
        LoginLog entity = new LoginLog();
        entity.setId(loginLogId);
        entity.setAreSuccessful(false);
        entity.setFailReason(failReason);

        this.rep.update(entity, operTime);
    }
}
