package org.anson.miniProject.service.account;

import org.anson.miniProject.constrant.dict.ClientEnum;
import org.anson.miniProject.service.account.model.LoginDTO;
import org.anson.miniProject.service.account.model.LoginDTOConverter;
import org.anson.miniProject.service.account.model.LogoutDTO;
import org.anson.miniProject.domain.account.user.IUserDMService;
import org.anson.miniProject.domain.account.user.cmd.LoginCMD;
import org.anson.miniProject.framework.pojo.CommonParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {
    public void pcLogin(LoginDTO dto, CommonParam cmParam) throws Exception{
        LoginCMD cmd = loginDTOConverter.toLoginCMD(dto);
        cmd.setClientKey(ClientEnum.PC);    // pc 端

        this.userDMService.login(cmd);
    }

    public void pcLogout(LogoutDTO dto, CommonParam cmParam) throws Exception{
        /*LogoutParam param = dto.toParam();

        param.setClientKey(cmParam.getClientKey());
        param.setIpv4(cmParam.getIpv4());

        this.domain.logout(param, cmParam.getLoginUserId(), cmParam.getOperTime());
         */
    }

    // 注入
    @Autowired
    private IUserDMService userDMService;
    @Autowired
    private LoginDTOConverter loginDTOConverter;
}
