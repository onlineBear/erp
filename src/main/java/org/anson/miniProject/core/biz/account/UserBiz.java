package org.anson.miniProject.core.biz.account;

import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.core.domain.sys.log.ILoginLogDomain;
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
        // 1. 记录登录日志
        String loginLogId = this.loginLogDomain.beginLogin(LoginBo.toBeginLoginDo(bo));

        try {
            // 2. 验证身份
            ShiroHelper.login(bo.getNo(), bo.getEncryptedPsd());
            String userId = ShiroHelper.getUserId();
            log.error("userId : {}", userId);
            // 3. 记录登录成功日志
            this.loginLogDomain.loginSuccess(loginLogId, userId, bo.getOperTime());
        }catch (Exception e){
            // 4. 记录登录失败日志
            this.loginLogDomain.loginFail(loginLogId, e.getMessage(), bo.getOperTime());
            throw e;
        }
    }
}
