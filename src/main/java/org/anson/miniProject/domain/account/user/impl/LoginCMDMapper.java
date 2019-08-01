package org.anson.miniProject.domain.account.user.impl;

import org.anson.miniProject.domain.account.user.cmd.LoginCMD;
import org.anson.miniProject.domain.internal.loginLog.cmd.LoginSuccessCMD;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface LoginCMDMapper {
    @Mappings({
            @Mapping(source = "userNo", target = "loginUserNo")
    })
    LoginSuccessCMD toLoginSuccessCMD(LoginCMD cmd);
}
