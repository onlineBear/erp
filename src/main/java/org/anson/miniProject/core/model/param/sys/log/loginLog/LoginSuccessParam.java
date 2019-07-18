package org.anson.miniProject.core.model.param.sys.log.loginLog;

import lombok.Builder;
import lombok.Data;
import org.anson.miniProject.constrant.dict.ClientEnum;
import org.anson.miniProject.core.model.po.sys.log.LoginLog;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

@Data
@Builder
public class LoginSuccessParam {
    private String userId;
    private ClientEnum clientKey;
    private String loginUserNo;
    private String ipv4;
    private Double longitude;
    private Double latitude;

    private static final BeanCopier toLoginLogCopier = BeanCopier.create(LoginSuccessParam.class, LoginLog.class, false);

    public LoginLog toLoginLog() throws InstantiationException, IllegalAccessException {
        LoginLog po = BeanHelper.beanToBean(this, LoginLog.class, toLoginLogCopier);

        if (this.clientKey != null){
            po.setClientKey(this.clientKey.getKey());
        }

        return po;
    }
}
