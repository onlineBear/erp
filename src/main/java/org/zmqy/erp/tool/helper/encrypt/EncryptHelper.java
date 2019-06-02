package org.zmqy.erp.tool.helper.encrypt;

import lombok.extern.slf4j.Slf4j;
import org.zmqy.erp.framework.property.EncryptProperty;
import org.zmqy.erp.tool.util.encrypt.EncryptUtil;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class EncryptHelper {

    /**
     * 获取加密后的密码
     * @param password
     * @return
     */
    public static String getEncryptPsd(String password) throws InvalidKeyException, NoSuchAlgorithmException{
        //log.debug("salt : {}, key : {}", EncryptProperty.getSalt(), EncryptProperty.getKey());
        return EncryptUtil.HMACSHA256((password + EncryptProperty.getSalt()).getBytes(), EncryptProperty.getKey().getBytes());
    }
}
