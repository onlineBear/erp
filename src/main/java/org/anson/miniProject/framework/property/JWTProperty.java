package org.anson.miniProject.framework.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * jwt 属性配置
 */
@Component
public class JWTProperty {
    /**
     * token 的发行者
     */
    private static String issuer;

    /**
     * token 的主题
     */
    private static String subject;

    /**
     * token 的客户
     */
    private static String audience;

    /**
     * token 的时长 (单位: 秒 s)
     */
    private static Long duration;

    /**
     * 刷新 token 的时长 (单位: 秒 s)
     */
    private static Long refreshDuration;

    /**
     * token 密钥
     */
    private static String secret;

    public static String getIssuer() {
        return issuer;
    }

    @Value("${jwt.issuer}")
    private void setIssuer(String issuer) {
        JWTProperty.issuer = issuer;
    }

    public static String getSubject() {
        return subject;
    }

    @Value("${jwt.subject}")
    private void setSubject(String subject) {
        JWTProperty.subject = subject;
    }

    public static String getAudience() {
        return audience;
    }

    @Value("${jwt.audience}")
    private void setAudience(String audience) {
        JWTProperty.audience = audience;
    }

    public static Long getDuration() {
        return duration;
    }

    @Value("${jwt.duration}")
    private void setDuration(Long duration) {
        JWTProperty.duration = duration;
    }

    public static Long getRefreshDuration() {
        return refreshDuration;
    }

    @Value("${jwt.refreshDuration}")
    private void setRefreshDuration(Long refreshDuration) {
        JWTProperty.refreshDuration = refreshDuration;
    }

    public static String getSecret() {
        return secret;
    }

    @Value("${jwt.secret}")
    private void setSecret(String secret) {
        JWTProperty.secret = secret;
    }
}
