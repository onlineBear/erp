package org.anson.miniProject.core.model.dmo.sys.log.loginLog;

import lombok.Data;
import org.anson.miniProject.constrant.dict.ClientEnum;
import org.anson.miniProject.core.model.po.sys.log.LoginLog;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

@Data
public class LoginSuccessDmo {
    private String userId;
    private ClientEnum clientKey;
    private String loginUserNo;
    private String ipv4;
    private Double longitude;
    private Double latitude;

    private static final BeanCopier toLoginLogCopier = BeanCopier.create(LoginSuccessDmo.class, LoginLog.class, false);

    public static LoginLog toLoginLog(LoginSuccessDmo dmo) throws InstantiationException, IllegalAccessException {
        LoginLog po = BeanHelper.beanToBean(dmo, LoginLog.class, toLoginLogCopier);

        if (dmo.getClientKey() != null){
            po.setClientKey(dmo.getClientKey().getKey());
        }

        return po;
    }
}
