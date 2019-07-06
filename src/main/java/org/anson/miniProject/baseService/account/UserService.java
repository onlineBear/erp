package org.anson.miniProject.baseService.account;

import org.anson.miniProject.biz.account.UserBiz;
import org.anson.miniProject.model.dto.biz.account.userBiz.LoginBDto;
import org.anson.miniProject.model.dto.service.account.userService.LoginSDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {
    @Autowired
    private UserBiz biz;

    public void pcLogin(LoginSDto dto, String ipv4, Date operTime){
        LoginBDto loginBDto = LoginSDto.toLoginBDto(dto);
        loginBDto.setLoginTypeKey("pc");
        loginBDto.setIpv4(ipv4);
        loginBDto.setOperTime(operTime);
        this.biz.login(loginBDto);
    }
}
