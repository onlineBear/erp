package org.anson.miniProject.tool.util.jwt;

import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class JWTParam {
    private String issuer;
    private String subject;
    private String audience;
    private Date expiresAt;
    private Date notBefore;
    private Date issuedAt;
    private String id;
    private Map<String, Object> claims;
}
