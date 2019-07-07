package org.anson.miniProject.core.model.dmo.sys.log;

import lombok.Data;
import org.anson.miniProject.core.model.po.sys.log.LoginLog;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

import java.util.List;

@Data
public class LoginFailedDo {
    private String loginTypeKey;
    private String clientKey;
    private String loginUserNo;
    private String failReason;
    private String ipv4;
    private Double longitude;
    private Double latitude;

    private static final BeanCopier toLoginLogCopier = BeanCopier.create(LoginFailedDo.class, LoginLog.class, false);

    public static LoginLog toLoginLog(LoginFailedDo dmo) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(dmo, LoginLog.class, toLoginLogCopier);
    }

    public List<LoginLog> toLoginLog(List<LoginFailedDo> doList) throws IllegalAccessException, InstantiationException {
        return BeanHelper.beansToBeans(doList, LoginLog.class, toLoginLogCopier);
    }
}
