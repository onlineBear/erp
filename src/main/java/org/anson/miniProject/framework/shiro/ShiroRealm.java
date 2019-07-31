package org.anson.miniProject.framework.shiro;

import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.domain.account.user.IUserDMService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class ShiroRealm extends AuthorizingRealm {
    @Autowired
    private IUserDMService userDMService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("权限认证");

        String userId = principalCollection.toString();

        Set<String> permission = new HashSet<>();
        permission.addAll(new ArrayList<>());

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addStringPermissions(permission);
        //simpleAuthorizationInfo.add(user.getRole());
        return simpleAuthorizationInfo;
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        String userNo = token.getUsername();
        String encryptedPsd = new String(token.getPassword());

        log.debug("认证身份, userNo : {}, psd : {}", userNo, encryptedPsd);

        // 验证账户密码
        String userId = this.userDMService.authentication(userNo, encryptedPsd);

        if (userId == null) {
            throw new AuthenticationException("用户编码或密码不正确");
        }

        // 存储 userId
        return new SimpleAuthenticationInfo(userId, encryptedPsd, super.getName());
    }
}
