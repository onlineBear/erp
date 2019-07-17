package org.anson.miniProject.framework.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

public class ShiroHelper {

    public static void login(String userNo, String psd){
        UsernamePasswordToken token = new UsernamePasswordToken(userNo, psd);
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
    }

    public static String getUserId(){
        Subject subject = SecurityUtils.getSubject();

        if(subject == null){
            throw new RuntimeException("未登录");
        }

        String userId = (String) subject.getPrincipal();

        return userId;
    }
}
