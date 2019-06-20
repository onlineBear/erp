package org.anson.miniProject.framework.shiro;

import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.domain.account.IUserDomain;
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

/**
 * @ClassName ShiroRealm
 * @Description TODO
 * @Author wanganxiong
 * @Date 2019/6/19 14:44
 * @Version 1.0
 **/
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
        String no = token.getUsername();
        String psd = new String(token.getPassword());

        log.debug("认证身份, no = {}, psd = {}", no, psd);

        // 验证账户密码
        this.userDomain.login(no, psd);
        return new SimpleAuthenticationInfo(no, psd, super.getName());
    }
}
