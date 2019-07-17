package org.anson.miniProject.core.model.bo.account.userBiz;

import lombok.Data;
import org.anson.miniProject.constrant.dict.ClientEnum;
import org.anson.miniProject.constrant.dict.LoginTypeEnum;
import org.anson.miniProject.core.model.dmo.sys.log.loginLog.LoginFailedParam;
import org.anson.miniProject.core.model.dmo.sys.log.loginLog.LoginSuccessParam;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

import java.util.Date;

@Data
public class LoginBo {
    private String no;
    private String encryptedPsd;
    private LoginTypeEnum loginTypeKey;
    private ClientEnum clientKey;
    private String ipv4;
    private Date operTime;

    private static final BeanCopier toLoginSuccessDmoCopier = BeanCopier.create(LoginBo.class, LoginSuccessParam.class, false);
    private static final BeanCopier toLoginFailedDmoCopier = BeanCopier.create(LoginBo.class, LoginFailedParam.class, false);

    public static LoginSuccessParam toLoginSuccessDo(LoginBo bo) throws InstantiationException, IllegalAccessException {
        LoginSuccessParam dmo = BeanHelper.beanToBean(bo, LoginSuccessParam.class, toLoginSuccessDmoCopier);
        dmo.setLoginUserNo(bo.getNo());
        return dmo;
    }

    public static LoginFailedParam toLoginFailedDo(LoginBo bo) throws InstantiationException, IllegalAccessException {
        LoginFailedParam dmo = BeanHelper.beanToBean(bo, LoginFailedParam.class, toLoginFailedDmoCopier);
        dmo.setLoginUserNo(bo.getNo());
        return dmo;
    }
}
