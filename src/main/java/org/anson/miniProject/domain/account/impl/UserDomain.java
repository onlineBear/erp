package org.anson.miniProject.domain.account.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.anson.miniProject.domain.account.IUserDomain;
import org.anson.miniProject.mapper.account.UserMapper;
import org.anson.miniProject.model.entity.account.User;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Wrapper;
import java.util.Date;

/**
 * @ClassName UserDomain
 * @Description TODO
 * @Author wanganxiong
 * @Date 2019/6/20 8:50
 * @Version 1.0
 **/
@Component
public class UserDomain implements IUserDomain {
    @Autowired
    private UserMapper mapper;

    @Override
    public void login(String no, String psd) throws AuthenticationException {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(User.NO, no);
        queryWrapper.eq(User.PASSWORD, psd);

        Integer count = mapper.selectCount(queryWrapper);

        if(count <= 0){
            throw new AuthenticationException("用户名或密码错误");
        }
    }
}
