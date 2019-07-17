package org.anson.miniProject.service.account;

import org.anson.miniProject.core.domain.account.IUserDomain;
import org.anson.miniProject.core.model.param.sys.LoginParam;
import org.anson.miniProject.core.model.dto.service.account.userService.LoginDTO;
import org.anson.miniProject.framework.pojo.CommonParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {
    @Autowired
    private IUserDomain domain;

    public void pcLogin(LoginDTO dto, CommonParam cmParam) throws Exception{
        LoginParam param = LoginDTO.toParam(dto);

        param.setClientKey(cmParam.getClientKey());
        param.setIpv4(cmParam.getIpv4());

        this.domain.login(param, cmParam.getOperTime());
    }
}
