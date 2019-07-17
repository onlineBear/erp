package org.anson.miniProject.core.domain.account.impl;

import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.core.domain.account.IUserDomain;
import org.anson.miniProject.core.domain.sys.log.ILoginLogDomain;
import org.anson.miniProject.core.model.dmo.sys.LoginParam;
import org.anson.miniProject.core.model.dmo.sys.log.loginLog.LoginFailedParam;
import org.anson.miniProject.core.model.dmo.sys.log.loginLog.LoginSuccessParam;
import org.anson.miniProject.core.repository.sys.UserRep;
import org.anson.miniProject.framework.shiro.ShiroHelper;
import org.anson.miniProject.tool.helper.ExceptionHelper;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class UserDomain implements IUserDomain {
    @Autowired
    private UserRep rep;

    @Autowired
    private ILoginLogDomain loginLogDomain;

    @Override
    // 成功返回 userId; 验证失败返回 null
    public String authentication(String userNo, String encryptedPsd) throws AuthenticationException {
        return this.rep.authentication(userNo, encryptedPsd);
    }

    @Override
    public void login(LoginParam param, Date loginTime) throws Exception {
        try {
            // 1. 验证身份
            ShiroHelper.login(param.getUserNo(), param.getEncryptedPsd());
            String userId = ShiroHelper.getUserId();
            // 2. 记录登录成功日志
            LoginSuccessParam loginSuccessParam = LoginSuccessParam.builder()
                    .userId(userId)
                    .loginUserNo(param.getUserNo())
                    .clientKey(param.getClientKey())
                    .ipv4(param.getIpv4())
                    .longitude(param.getLongitude())
                    .latitude(param.getLatitude())
                    .build();
            this.loginLogDomain.loginSuccess(loginSuccessParam, userId, loginTime);
        }catch (Exception e){
            // 3. 记录登录失败日志
            LoginFailedParam loginFailedParam = LoginFailedParam.builder()
                    .clientKey(param.getClientKey())
                    .loginUserNo(param.getUserNo())
                    .failReason(ExceptionHelper.getMsg(e))
                    .ipv4(param.getIpv4())
                    .longitude(param.getLongitude())
                    .latitude(param.getLatitude())
                    .build();

            this.loginLogDomain.loginFailed(loginFailedParam, loginTime);
            throw e;
        }
    }
}
