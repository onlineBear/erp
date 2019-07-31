package org.anson.miniProject.domain.account.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.domain.account.user.IUserDMService;
import org.anson.miniProject.domain.account.user.cmd.LoginCMD;
import org.anson.miniProject.domain.account.user.cmd.LogoutCMD;
import org.anson.miniProject.domain.internal.loginLog.ILoginLogDMService;
import org.anson.miniProject.domain.internal.loginLog.cmd.LoginFailedCMD;
import org.anson.miniProject.domain.internal.loginLog.cmd.LoginSuccessCMD;
import org.anson.miniProject.framework.shiro.ShiroHelper;
import org.anson.miniProject.tool.helper.ExceptionHelper;
import org.anson.miniProject.tool.helper.SecurityHelper;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(rollbackFor = Exception.class)
@Slf4j
class UserDMService implements IUserDMService {
    @Override
    public String authentication(String userNo, String encryptedPsd) throws AuthenticationException {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq(User.NO, userNo)
            .select(User.PASSWORD, User.REGISTEREDTIME, User.ID);

        User user = mapper.selectOne(qw);

        if (user == null) {
            return null;
        }

        // 计算密码
        log.debug("注册时间 : {}, 输入密码: {}", user.getRegisteredTime(), encryptedPsd);
        String secondEncryptedPsd = SecurityHelper.calcPsd(user.getRegisteredTime(), encryptedPsd);

        if (secondEncryptedPsd.equals(user.getPassword())) {
            return user.getId();
        }

        return null;
    }

    @Override
    public void login(LoginCMD cmd) throws Exception{
        try{
            // 若已经登录, 直接返回成功
            if (ShiroHelper.getUserId() != null){
                return;
            }

            // 交由 shiro 去做登录校验
            ShiroHelper.login(cmd.getUserNo(), cmd.getEncryptedPsd());

            // 登录成功日志
            LoginSuccessCMD loginSuccessCMD = new LoginSuccessCMD();
            loginSuccessCMD.setClientKey(cmd.getClientKey());
            loginSuccessCMD.setUserId(ShiroHelper.getUserId());
            loginSuccessCMD.setLoginUserNo(cmd.getUserNo());
            loginSuccessCMD.setIpv4(cmd.getIpv4());
            loginSuccessCMD.setLongitude(cmd.getLongitude());
            loginSuccessCMD.setLatitude(cmd.getLatitude());

            this.loginLogDMService.logLoginSuccess(loginSuccessCMD);
        }catch (Exception e){
            // 登录失败日志
            LoginFailedCMD loginFailedCMD = new LoginFailedCMD();
            loginFailedCMD.setClientKey(cmd.getClientKey());
            loginFailedCMD.setLoginUserNo(cmd.getUserNo());
            loginFailedCMD.setFailReason(ExceptionHelper.getMsg(e));
            loginFailedCMD.setIpv4(cmd.getIpv4());
            loginFailedCMD.setLongitude(cmd.getLongitude());
            loginFailedCMD.setLatitude(cmd.getLatitude());

            this.loginLogDMService.logLoginFailed(loginFailedCMD);
            throw e;
        }

    }

    @Override
    public void logout(LogoutCMD cmd) throws Exception {
        // 交由 shiro 去做登出
        ShiroHelper.logout();
    }

    // 注入
    @Autowired
    private UserMapper mapper;
    @Autowired
    private ILoginLogDMService loginLogDMService;
}
