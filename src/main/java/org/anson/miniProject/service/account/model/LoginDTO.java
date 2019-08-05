package org.anson.miniProject.service.account.model;

import lombok.Data;

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
}
