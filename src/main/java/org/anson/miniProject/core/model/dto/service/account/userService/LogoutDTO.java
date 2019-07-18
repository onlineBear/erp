package org.anson.miniProject.core.model.dto.service.account.userService;

import lombok.Data;
import org.anson.miniProject.core.model.param.account.user.LogoutParam;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

@Data
public class LogoutDTO {
    // 通用字段
    /**
     * 经度
     */
    private Double longitude;
    /**
     * 纬度
     */
    private Double latitude;

    private static final BeanCopier toParamCopier = BeanCopier.create(LogoutDTO.class, LogoutParam.class, false);

    public LogoutParam toParam() throws InstantiationException, IllegalAccessException {
        LogoutParam param = BeanHelper.beanToBean(this, LogoutParam.class, toParamCopier);

        return param;
    }
}
