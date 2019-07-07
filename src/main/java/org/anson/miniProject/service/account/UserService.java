package org.anson.miniProject.service.account;

import org.anson.miniProject.core.biz.account.UserBiz;
import org.anson.miniProject.core.model.bo.account.userBiz.LoginBo;
import org.anson.miniProject.core.model.dto.service.account.userService.LoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {
    @Autowired
    private UserBiz biz;

    public void pcLogin(LoginDto dto, String ipv4, Date operTime) throws Exception{
        LoginBo loginBo = LoginDto.toLoginBo(dto);
        loginBo.setClientKey("pc");
        loginBo.setIpv4(ipv4);
        loginBo.setOperTime(operTime);
        this.biz.login(loginBo);
    }
}
