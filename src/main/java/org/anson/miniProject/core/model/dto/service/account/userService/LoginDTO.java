package org.anson.miniProject.core.model.dto.service.account.userService;

import lombok.Data;
import org.anson.miniProject.core.model.param.account.user.LoginParam;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

import javax.validation.constraints.NotBlank;

@Data
public class LoginDTO {
    @NotBlank(message = "请输入用户编码")
    private String no;
    @NotBlank(message = "请输入密码")
    private String psd;

    // 通用字段
    /**
     * 经度
     */
    private Double longitude;
    /**
     * 纬度
     */
    private Double latitude;

    private static final BeanCopier toParamCopier = BeanCopier.create(LoginDTO.class, LoginParam.class, false);

    public static LoginParam toParam(LoginDTO dto) throws InstantiationException, IllegalAccessException {
        LoginParam param = BeanHelper.beanToBean(dto, LoginParam.class, toParamCopier);

        param.setUserNo(dto.getNo());
        param.setEncryptedPsd(dto.getPsd());

        return param;
    }
}
