package org.anson.miniProject.service.account;

import org.anson.miniProject.domain.sys.log.ILoginLogDomain;
import org.anson.miniProject.framework.shiro.ShiroHelper;
import org.anson.miniProject.model.bo.sys.log.LoginLogBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * @ClassName UserService
 * @Description TODO
 * @Author wanganxiong
 * @Date 2019/6/20 8:47
 * @Version 1.0
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {
    @Autowired
    private ILoginLogDomain loginLogDomain;

    public void login(String no, String psd){
        ShiroHelper.login(no, psd);
    }

    public String beginLoginLog(Map dtoMap, Date operTime) throws Exception {
        return this.loginLogDomain.beginLogin(LoginLogBo.mapToBeginLogin(dtoMap, operTime), operTime);
    }

    public void loginSuccess(String loginLogId, String userId, Date operTime){
        this.loginLogDomain.loginSuccess(loginLogId, userId, operTime);
    }

    public void loginFail(String loginLogId, String failReason, Date operTime){
        this.loginLogDomain.loginFail(loginLogId, failReason, operTime);
    }
}
