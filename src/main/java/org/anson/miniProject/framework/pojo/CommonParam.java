package org.anson.miniProject.framework.pojo;

import lombok.Builder;
import lombok.Data;
import org.anson.miniProject.constrant.dict.ClientEnum;

import java.util.Date;

@Data
@Builder
public class CommonParam {
    private String loginUserId;
    private Date operTime;
    private ClientEnum clientKey;
    private String menuId;
    private String ipv4;
}
