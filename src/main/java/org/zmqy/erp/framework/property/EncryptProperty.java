package org.zmqy.erp.framework.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 加密配置
 */
@Component
public class EncryptProperty {

    /**
     * 密钥
     */
    private static String KEY;

    /**
     * 盐值
     */
    private static String SALT;

    public static String getKey(){
        return EncryptProperty.KEY;
    }

    public static String getSalt() {
        return EncryptProperty.SALT;
    }

    @Value("${comstom.encrypt.salt}")
    private void setSalt(String salt) {
        EncryptProperty.SALT = salt;
    }

    @Value("${comstom.encrypt.key}")
    private void setKey(String key) {
        EncryptProperty.KEY = key;
    }
}
