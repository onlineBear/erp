package org.anson.miniProject.core.model.dto.service.account.userService;

import lombok.Data;
import org.anson.miniProject.domain.account.user.cmd.LoginCMD;
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

    private static final BeanCopier toParamCopier = BeanCopier.create(LoginDTO.class, LoginCMD.class, false);

    public static LoginCMD toCMD(LoginDTO dto) throws InstantiationException, IllegalAccessException {
        LoginCMD cmd = BeanHelper.beanToBean(dto, LoginCMD.class, toParamCopier);

        cmd.setEncryptedPsd(dto.psd);
        cmd.setUserNo(dto.no);

        return cmd;
    }
}
