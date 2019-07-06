package org.anson.miniProject.core.repository.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.core.mapper.account.UserMapper;
import org.anson.miniProject.core.model.po.account.User;
import org.anson.miniProject.core.repository.BaseRep;
import org.anson.miniProject.tool.helper.SecurityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class UserRep extends BaseRep<User, UserMapper> {

    // 查询
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public String authentication(String userNo, String encryptedPsd){
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq(User.NO, userNo)
          .select(User.PASSWORD, User.REGISTRATIONTIME, User.ID);

        User user = mapper.selectOne(qw);

        if (user == null) {
            return null;
        }

        // 计算密码
        String secondEncryptedPsd = SecurityHelper.calcPsd(user.getRegistrationTime(), encryptedPsd);

        if (secondEncryptedPsd.equals(user.getPassword())) {
            return user.getId();
        }

        return null;
    }

    // 注入
    @Autowired
    public void setMapper(UserMapper mapper){
        this.mapper = mapper;
    }
}
