package org.anson.miniProject.domain.internal.loginLog.cmd;

import lombok.Data;
import org.anson.miniProject.constrant.dict.ClientEnum;
import org.anson.miniProject.domain.base.BaseCMD;

@Data
public class LoginFailedCMD extends BaseCMD {
    private ClientEnum clientKey;
    private String loginUserNo;
    private String failReason;
    private String ipv4;
    private Double longitude;
    private Double latitude;
}
