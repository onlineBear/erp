package org.anson.miniProject.model.bo.account.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginBo {
    private String userNo;
    private String encryptedPsd;
    private String ipv4;
}
