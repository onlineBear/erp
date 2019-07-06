package org.anson.miniProject.biz.account;

import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.domain.sys.log.ILoginLogDomain;
import org.anson.miniProject.framework.shiro.ShiroHelper;
import org.anson.miniProject.model.dto.biz.account.userBiz.LoginBDto;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class UserBiz {
    @Autowired
    private ILoginLogDomain loginLogDomain;

    public void login(LoginBDto dto){
        // 1. 记录登录日志
        String loginLogId = this.loginLogDomain.beginLogin(LoginBDto.toBeginLoginDto(dto));

        try {
            // 2. 验证身份
            ShiroHelper.login(dto.getNo(), dto.getEncryptedPsd());
            String userId = ShiroHelper.getUserId();
            // 3. 记录登录成功日志
            this.loginLogDomain.loginSuccess(loginLogId, userId, dto.getOperTime());
        }catch (Exception e){
            // 4. 记录登录失败日志
            this.loginLogDomain.loginFail(loginLogId, e.getMessage(), dto.getOperTime());
            throw e;
        }
    }
}
