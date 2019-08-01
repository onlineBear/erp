package org.anson.miniProject.core.model.dto.service.account.userService;

import lombok.Data;
import org.anson.miniProject.domain.account.user.cmd.LoginCMD;
import org.anson.miniProject.tool.bean.BeanUtils;

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

    public static LoginCMD toCMD(LoginDTO dto) throws InstantiationException, IllegalAccessException {
        LoginCMD cmd = BeanUtils.beanToBean(dto, LoginCMD.class);

        cmd.setEncryptedPsd(dto.psd);
        cmd.setUserNo(dto.no);

        return cmd;
    }
}
