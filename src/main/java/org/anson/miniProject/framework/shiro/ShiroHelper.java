package org.anson.miniProject.framework.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

/**
 * @ClassName ShiroHelper
 * @Description TODO
 * @Author wanganxiong
 * @Date 2019/6/19 17:24
 * @Version 1.0
 **/
public class ShiroHelper {
    /**
     * 登录
     * @param userName
     * @param psd
     */
    public static void login(String userName, String psd){
        UsernamePasswordToken token = new UsernamePasswordToken(userName, psd);
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
    }
}
