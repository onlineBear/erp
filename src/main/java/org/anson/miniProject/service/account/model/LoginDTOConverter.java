package org.anson.miniProject.service.account.model;

import org.anson.miniProject.domain.account.user.cmd.LoginCMD;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class LoginDTOConverter {
    @Mapping(target = "userNo", source = "no")
    @Mapping(target = "encryptedPsd", source = "psd")
    public abstract LoginCMD toLoginCMD(LoginDTO dto);
}
