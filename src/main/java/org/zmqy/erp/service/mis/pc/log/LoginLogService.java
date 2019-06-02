package org.zmqy.erp.service.mis.pc.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zmqy.erp.constract.mis.enums.LoginClientNoEnum;
import org.zmqy.erp.mapper.mis.biz.log.LoginLogMapper;
import org.zmqy.erp.model.mis.entity.log.LoginLog;
import org.zmqy.erp.tool.helper.Ip.IpHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @Description: 记录登录登录日志
 * @Auther: lly
 * @Date: 2018-12-25 14:59
 */
@Service
public class LoginLogService {
    @Autowired
    private LoginLogMapper loginLogMapper;
    @Autowired
    private HttpServletRequest request;

    @Transactional(rollbackFor = Exception.class)
    public String beginLoginLog(String userId, LoginClientNoEnum loginClientNoEnum) {
        String ipAddress = IpHelper.getRemoteHost(request);

        Date date = new Date();
        LoginLog loginLog = new LoginLog();
        loginLog.setAreSuccess(false);
        loginLog.setCreateTime(date);
        loginLog.setUserId(userId);
        loginLog.setIp(ipAddress);
        loginLog.setLoginClientNo(loginClientNoEnum.getCode());
        loginLog.setLoginTime(date);
        loginLog.setCreateUserId(userId);
        loginLog.setLastUpdateTime(date);
        loginLogMapper.insert(loginLog);

        return loginLog.getId();

    }

    @Transactional(rollbackFor = Exception.class)
    public void successLoginLog(String id){
        LoginLog loginLog = new LoginLog();
        loginLog.setId(id);
        loginLog.setAreSuccess(true);
        loginLog.setLastUpdateTime(new Date());
        loginLogMapper.updateById(loginLog);
    }


}
