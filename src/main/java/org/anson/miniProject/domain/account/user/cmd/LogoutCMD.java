package org.anson.miniProject.domain.account.user.cmd;

import lombok.Data;
import org.anson.miniProject.constrant.dict.ClientEnum;

@Data
public class LogoutCMD {
    private ClientEnum clientKey;
}
