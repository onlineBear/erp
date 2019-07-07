package org.anson.miniProject.core.model.bo.account.userBiz;

import lombok.Data;
import org.anson.miniProject.core.model.dmo.sys.log.loginLog.LoginFailedDmo;
import org.anson.miniProject.core.model.dmo.sys.log.loginLog.LoginSuccessDmo;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

import java.util.Date;

@Data
public class LoginBo {
    private String no;
    private String encryptedPsd;
    private String loginTypeKey;
    private String clientKey;
    private String ipv4;
    private Date operTime;

    private static final BeanCopier toLoginSuccessDmoCopier = BeanCopier.create(LoginBo.class, LoginSuccessDmo.class, false);
    private static final BeanCopier toLoginFailedDmoCopier = BeanCopier.create(LoginBo.class, LoginFailedDmo.class, false);

    public static LoginSuccessDmo toLoginSuccessDo(LoginBo bo) throws InstantiationException, IllegalAccessException {
        LoginSuccessDmo dmo = BeanHelper.beanToBean(bo, LoginSuccessDmo.class, toLoginSuccessDmoCopier);
        dmo.setLoginUserNo(bo.getNo());
        return dmo;
    }

    public static LoginFailedDmo toLoginFailedDo(LoginBo bo) throws InstantiationException, IllegalAccessException {
        LoginFailedDmo dmo = BeanHelper.beanToBean(bo, LoginFailedDmo.class, toLoginFailedDmoCopier);
        dmo.setLoginUserNo(bo.getNo());
        return dmo;
    }
}
