package org.anson.miniProject.core.biz.account;

import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.core.domain.sys.log.ILoginLogDomain;
import org.anson.miniProject.core.model.dmo.sys.log.LoginFailedDo;
import org.anson.miniProject.framework.shiro.ShiroHelper;
import org.anson.miniProject.core.model.bo.account.userBiz.LoginBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class UserBiz {
    @Autowired
    private ILoginLogDomain loginLogDomain;

    public void login(LoginBo bo) throws Exception{
        try {
            // 1. 验证身份
            ShiroHelper.login(bo.getNo(), bo.getEncryptedPsd());
            String userId = ShiroHelper.getUserId();
            // 2. 记录登录成功日志
            this.loginLogDomain.loginSuccess(LoginBo.toLoginSuccessDo(bo), userId, bo.getOperTime());
        }catch (Exception e){
            // 3. 记录登录失败日志
            LoginFailedDo loginFailedDo = LoginBo.toLoginFailedDo(bo);
            loginFailedDo.setFailReason(e.getMessage());
            this.loginLogDomain.loginFailed(loginFailedDo, bo.getOperTime());
            throw e;
        }
    }
}
