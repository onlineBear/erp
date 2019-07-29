package org.anson.miniProject.domain.account.user.cmd;

import lombok.Data;
import org.anson.miniProject.constrant.dict.ClientEnum;
import org.anson.miniProject.constrant.dict.LoginTypeEnum;

@Data
public class LoginCMD {
    private String userNo;
    private String encryptedPsd;
    private LoginTypeEnum loginTypeKey;
    private ClientEnum clientKey;
}
