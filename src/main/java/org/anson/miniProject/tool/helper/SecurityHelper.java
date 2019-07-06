package org.anson.miniProject.tool.helper;

import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class SecurityHelper {
    public static String calcPsd(Date registrationTime, String psd){
        String lowerPsd = psd.toLowerCase();
        HMac mac = new HMac(HmacAlgorithm.HmacSHA1, DateUtil.formatDateTime(registrationTime).getBytes());
        return mac.digestHex(lowerPsd).toLowerCase();
    }
}
