package org.anson.miniProject.domain.internal.loginLog.cmd;

import lombok.Data;
import org.anson.miniProject.constrant.dict.ClientEnum;

@Data
public class LoginSuccessCMD {
    private String userId;
    private ClientEnum clientKey;
    private String loginUserNo;
    private String ipv4;
    private Double longitude;
    private Double latitude;
}
