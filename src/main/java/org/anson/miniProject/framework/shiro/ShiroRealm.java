package org.anson.miniProject.framework.shiro;

import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.core.domain.account.IUserDomain;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class ShiroRealm extends AuthorizingRealm {
    @Autowired
    private IUserDomain userDomain;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("doGetAuthorizationInfo");

        String userId = principalCollection.toString();

        Set<String> permission = new HashSet<>();
        permission.addAll(new ArrayList<>());

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addStringPermissions(permission);
        //simpleAuthorizationInfo.addRole(user.getRole());
        return simpleAuthorizationInfo;
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        String userNo = token.getUsername();
        String psd = new String(token.getPassword());

        log.debug("认证身份, no : {}, psd : {}", userNo, psd);

        // 验证账户密码
        String userId = this.userDomain.authentication(userNo, psd);

        if (userId == null) {
            throw new AuthenticationException("用户编码或密码不正确");
        }

        // 存储 userId
        return new SimpleAuthenticationInfo(userId, psd, super.getName());
    }
}
