package org.anson.miniProject.domain.account.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.domain.account.IUserDomain;
import org.anson.miniProject.mapper.account.UserMapper;
import org.anson.miniProject.model.entity.account.User;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @ClassName UserDomain
 * @Description TODO
 * @Author wanganxiong
 * @Date 2019/6/20 8:50
 * @Version 1.0
 **/
@Component
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class UserDomain implements IUserDomain {
    @Autowired
    private UserMapper mapper;

    @Override
    public void login(String userNo, String psd) throws AuthenticationException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(User.NO, userNo);

        User user = mapper.selectOne(queryWrapper);

        if(user == null) {
            throw new AuthenticationException("用户名或密码错误");
        }

        // 密码不正确
        String sysPsd = this.calPsd(user.getRegistrationTime(), psd);

        log.debug(sysPsd);

        if(!sysPsd.equals(user.getPassword())){
            throw new AuthenticationException("用户名或密码错误");
        }
    }

    private String calPsd(Date registrationTime, String psd){
        String lowerPsd = psd.toLowerCase();
        log.debug("注册时间: {}" , DateUtil.formatDateTime(registrationTime));
        HMac mac = new HMac(HmacAlgorithm.HmacSHA1, DateUtil.formatDateTime(registrationTime).getBytes());
        return mac.digestHex(lowerPsd).toLowerCase();
    }
}
