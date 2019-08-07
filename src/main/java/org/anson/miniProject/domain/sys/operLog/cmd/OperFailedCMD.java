package org.anson.miniProject.domain.sys.operLog.cmd;

import lombok.Data;
import org.anson.miniProject.constrant.dict.ClientEnum;

@Data
public class OperFailedCMD {
    private String operUserId;
    private String operMenuId;

    private ClientEnum clientKey;

    private String description;
    private String failReason;
    private String ipv4;
    private Double longitude;
    private Double latitude;
}
