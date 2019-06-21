package org.anson.miniProject.service.account;

import org.anson.miniProject.framework.shiro.ShiroHelper;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserService
 * @Description TODO
 * @Author wanganxiong
 * @Date 2019/6/20 8:47
 * @Version 1.0
 **/
@Service
public class UserService {

    public void login(String no, String psd){
        ShiroHelper.login(no, psd);
    }
}
