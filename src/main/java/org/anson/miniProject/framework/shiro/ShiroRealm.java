package org.anson.miniProject.framework.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName ShiroRealm
 * @Description TODO
 * @Author wanganxiong
 * @Date 2019/6/19 14:44
 * @Version 1.0
 **/
@Component
@Slf4j
public class ShiroRealm extends AuthorizingRealm {

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

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("doGetAuthenticationInfo");
        String token = (String) authenticationToken.getPrincipal();
        String password = (String) authenticationToken.getCredentials();
        // 验证账户密码
        return new SimpleAuthenticationInfo(token, password, super.getName());
    }
}
