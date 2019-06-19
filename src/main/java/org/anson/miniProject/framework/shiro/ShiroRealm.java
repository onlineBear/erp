package org.anson.miniProject.framework.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
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

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("认证身份");
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        String username = token.getUsername();
        String psd = new String(token.getPassword());

        log.debug("userName = {}, psd = {}", username, psd);
        // 验证账户密码
        if(!"userName".equals(username) || !"psd".equals(psd)){
            throw new AuthenticationException("用户名或密码错误");
        }
        return new SimpleAuthenticationInfo(username, psd, super.getName());
    }
}
