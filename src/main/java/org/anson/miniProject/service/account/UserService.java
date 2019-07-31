package org.anson.miniProject.service.account;

import org.anson.miniProject.core.model.dto.service.account.userService.LoginDTO;
import org.anson.miniProject.core.model.dto.service.account.userService.LogoutDTO;
import org.anson.miniProject.domain.account.user.IUserDMService;
import org.anson.miniProject.domain.account.user.cmd.LoginCMD;
import org.anson.miniProject.framework.pojo.CommonParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {
    @Autowired
    private IUserDMService userDMService;

    public void pcLogin(LoginDTO dto, CommonParam cmParam) throws Exception{
        LoginCMD cmd = LoginDTO.toCMD(dto);

        cmd.setClientKey(cmParam.getClientKey());
        cmd.setIpv4(cmParam.getIpv4());

        this.userDMService.login(cmd);
    }

    public void pcLogout(LogoutDTO dto, CommonParam cmParam) throws Exception{
        /*LogoutParam param = dto.toParam();

        param.setClientKey(cmParam.getClientKey());
        param.setIpv4(cmParam.getIpv4());

        this.domain.logout(param, cmParam.getLoginUserId(), cmParam.getOperTime());
         */
    }
}
