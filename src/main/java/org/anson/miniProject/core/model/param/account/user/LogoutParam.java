package org.anson.miniProject.core.model.param.account.user;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.anson.miniProject.constrant.dict.ClientEnum;

@Data
@Builder
public class LogoutParam {
    private ClientEnum clientKey;

    private String ipv4;
    private Double longitude;
    private Double latitude;

    @Tolerate
    public LogoutParam(){};
}
