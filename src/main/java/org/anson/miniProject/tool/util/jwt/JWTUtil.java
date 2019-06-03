package org.anson.miniProject.tool.util.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JWTUtil {

    public static String signHMAC256(JWTParam jwtParam, String secret){
        if(jwtParam == null) {
            throw new RuntimeException("jwtParam is null");
        }

        JWTCreator.Builder builder = JWT.create()
                                                        .withIssuer(jwtParam.getIssuer())
                                                        .withSubject(jwtParam.getSubject())
                                                        .withAudience(jwtParam.getAudience())
                                                        .withExpiresAt(jwtParam.getExpiresAt())
                                                        .withNotBefore(jwtParam.getNotBefore())
                                                        .withIssuedAt(jwtParam.getIssuedAt())
                                                        .withJWTId(jwtParam.getId());
        if(jwtParam.getClaims() != null){
            jwtParam.getClaims().forEach((k,v)->{
                v = jwtParam.getClaims().get(k);

                if(v instanceof String){
                    builder.withClaim(k, (String) v);
                }else if(v instanceof Integer){
                    builder.withClaim(k, (Integer) v);
                }else if(v instanceof Boolean){
                    builder.withClaim(k, (Boolean) v);
                }else if(v instanceof Long){
                    builder.withClaim(k, (Long) v);
                }else if(v instanceof Double){
                    builder.withClaim(k, (Double) v);
                }else if(v instanceof Date){
                    builder.withClaim(k, (Date) v);
                }else{
                    throw new RuntimeException("JWTParam.claims value 数据类型不正确");
                }

            });
        }

        Algorithm algorithmHS = Algorithm.HMAC256(secret);
        String token = builder.sign(algorithmHS);

        return token;
    }

    public static DecodedJWT decoded(String token){
        return JWT.decode(token);
    }

    public static Boolean verify(String token){
        return false;
    }
}
