package org.anson.miniProject.tool.helper;

import org.anson.miniProject.constrant.dict.ClientEnum;
import org.anson.miniProject.framework.pojo.CommonParam;
import org.anson.miniProject.framework.shiro.ShiroHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class CommonParamHelper {

    public static CommonParam buildCommonParam(HttpServletRequest req, ClientEnum clientKey, String menuId){
        CommonParam commonParam = CommonParam.builder()
                .loginUserId(ShiroHelper.getUserId())
                .operTime(new Date())
                .clientKey(clientKey)
                .menuId(menuId)
                .ipv4(IpHelper.getRemoteHost(req))
                .build();

        return commonParam;
    }
}
