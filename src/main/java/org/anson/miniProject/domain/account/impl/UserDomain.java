package org.anson.miniProject.domain.account.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.constrant.dictionary.Dictionary;
import org.anson.miniProject.domain.account.IUserDomain;
import org.anson.miniProject.mapper.account.UserMapper;
import org.anson.miniProject.model.entity.account.User;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
        queryWrapper.eq(User.PASSWORD, psd);

        User user = mapper.selectOne(queryWrapper);

        if(user == null){
            throw new AuthenticationException("用户名或密码错误");
        }

        // 若已离职, 不能登录
        if(Dictionary.USER_SERVE_STATUS_LEAVED.equals(user.getServeStatus())){
            throw new AuthenticationException("已离职, 无法登录");
        }
    }
}
