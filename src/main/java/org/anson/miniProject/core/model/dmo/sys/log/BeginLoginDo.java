package org.anson.miniProject.core.model.dmo.sys.log;

import lombok.Data;
import org.anson.miniProject.core.model.po.sys.log.LoginLog;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

import java.util.Date;
import java.util.List;

@Data
public class BeginLoginDo {
    private String loginTypeKey;
    private Date operTime;
    private String loginUserNo;
    private String ipv4;
    private Double longitude;
    private Double latitude;

    private static final BeanCopier toLoginLogCopier = BeanCopier.create(BeginLoginDo.class, LoginLog.class, false);

    public static LoginLog toLoginLog(BeginLoginDo dmo) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(dmo, LoginLog.class, toLoginLogCopier);
    }

    public List<LoginLog> toLoginLog(List<BeginLoginDo> doList) throws IllegalAccessException, InstantiationException {
        return BeanHelper.beansToBeans(doList, LoginLog.class, toLoginLogCopier);
    }
}
