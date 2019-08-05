package org.anson.miniProject.domain.sys.operLog.cmd;

import lombok.Data;
import org.anson.miniProject.constrant.dict.ClientEnum;

@Data
public class OperSuccessCMD {
    private String operMenuId;

    // private String operTypeKey;
    private ClientEnum clientKey;

    private String description;
    private String pk;
    private String ipv4;
    private Double longitude;
    private Double latitude;
}
