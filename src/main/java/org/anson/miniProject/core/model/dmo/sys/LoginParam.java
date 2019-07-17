package org.anson.miniProject.core.model.dmo.sys;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.anson.miniProject.constrant.dict.ClientEnum;
import org.anson.miniProject.constrant.dict.LoginTypeEnum;

@Data
@Builder
public class LoginParam {
    private String userNo;
    private String encryptedPsd;
    private LoginTypeEnum loginTypeKey;
    private ClientEnum clientKey;
    private String ipv4;
    private Double longitude;
    private Double latitude;

    @Tolerate
    public LoginParam(){};
}
