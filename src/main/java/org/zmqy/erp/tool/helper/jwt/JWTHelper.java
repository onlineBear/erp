package org.zmqy.erp.tool.helper.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.zmqy.erp.framework.exception.model.unI18n.jwt.RefreshTokenExpireException;
import org.zmqy.erp.framework.exception.model.unI18n.jwt.TokenExpireException;
import org.zmqy.erp.framework.property.JWTProperty;
import org.zmqy.erp.framework.exception.model.unI18n.InternalException;
import org.zmqy.erp.tool.util.common.StringUtil;
import org.zmqy.erp.tool.util.jwt.JWTParam;
import org.zmqy.erp.tool.util.jwt.JWTUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JWTHelper {

    private static final String CLAIMS_USERID = "userId";
    private static final String CLAIMS_RFEXP = "rfExp";

    public static String createToken(String userId){
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        long expMillis = nowMillis + JWTProperty.getDuration() * 1000;
        Date expiresAt = new Date(expMillis);
        long rfExpMillis = nowMillis + JWTProperty.getRefreshDuration() * 1000;
        Date rfExp = new Date(rfExpMillis);

        return createToken(userId, expiresAt, now, rfExp);
    }

    private static String createToken(String userId, Date expiresAt, Date issuedAt, Date rfExp){
        JWTParam jwtParam = new JWTParam();
        jwtParam.setIssuer(JWTProperty.getIssuer());
        jwtParam.setSubject(JWTProperty.getSubject());
        jwtParam.setAudience(JWTProperty.getAudience());
        jwtParam.setExpiresAt(expiresAt);
        jwtParam.setIssuedAt(issuedAt);

        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIMS_USERID, userId);
        claims.put(CLAIMS_RFEXP, rfExp);

        jwtParam.setClaims(claims);

        return JWTUtil.signHMAC256(jwtParam, JWTProperty.getSecret());
    }

    public static Boolean verify(String token){
        if(StringUtil.isEmpty(token)){
            throw new RuntimeException("token is empty");
        }


        log.debug("token : {}", token);

        DecodedJWT decodedJWT = JWTUtil.decoded(token);

        // 解码
        String userId = decodedJWT.getClaim(CLAIMS_USERID).asString();
        Date expiresAt = decodedJWT.getExpiresAt();
        Date issuedAt = decodedJWT.getIssuedAt();
        Date rfExp = decodedJWT.getClaim(CLAIMS_RFEXP).asDate();

        String calcToken = createToken(userId, expiresAt, issuedAt, rfExp);

        // 签名是否正确
        if(!token.equals(calcToken)){
            throw new InternalException("token签名错误");
        }

        // token 是否已过期
        Date nowTime = new Date();
        if(expiresAt.getTime() < nowTime.getTime()){
            // 刷新 roken 是否已过期
            if(rfExp.getTime() < nowTime.getTime()){
                throw new RefreshTokenExpireException();
            }
            throw new TokenExpireException();
        }

        return true;
    }

    public static String getUserId(String token){
        if(StringUtils.isEmpty(token)){
            throw new RuntimeException("token is empty");
        }

        DecodedJWT decodedJWT = JWTUtil.decoded(token);

        return decodedJWT.getClaim(CLAIMS_USERID).asString();
    }
}
