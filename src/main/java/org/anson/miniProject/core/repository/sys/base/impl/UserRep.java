package org.anson.miniProject.core.repository.sys.base.impl;

import cn.hutool.core.collection.IterUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.core.mapper.account.UserMapper;
import org.anson.miniProject.core.model.po.account.User;
import org.anson.miniProject.core.repository.BaseRep;
import org.anson.miniProject.core.repository.sys.base.IUserRep;
import org.anson.miniProject.tool.helper.SecurityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Component
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class UserRep extends BaseRep<User, UserMapper>
                     implements IUserRep {

    // 接口命令(需要事务)
    @Override
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
        log.debug("注册时间 : {}, 输入密码: {}", user.getRegistrationTime(), encryptedPsd);
        String secondEncryptedPsd = SecurityHelper.calcPsd(user.getRegistrationTime(), encryptedPsd);

        if (secondEncryptedPsd.equals(user.getPassword())) {
            return user.getId();
        }

        return null;
    }

    // 接口查询(只读事务)
    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public User selectById(String id) {
        User po = this.mapper.selectById(id);
        po.setPassword(null);
        return po;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<User> selectBatchIds(Collection<? extends String> idList) {
        List<User> poList = this.mapper.selectBatchIds(idList);

        if (IterUtil.isEmpty(poList)) {
            return null;
        }

        for (User po : poList){
            po.setPassword(null);
        }

        return poList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public User selectOne(Wrapper<User> queryWrapper) {
        User po = this.mapper.selectOne(queryWrapper);
        po.setPassword(null);
        return po;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Integer selectCount(Wrapper<User> queryWrapper) {
        return this.mapper.selectCount(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<User> selectList(Wrapper<User> queryWrapper) {
        List<User> poList = this.mapper.selectList(queryWrapper);

        if (IterUtil.isEmpty(poList)) {
            return null;
        }

        for (User po : poList){
            po.setPassword(null);
        }

        return poList;
    }

    // 非接口命令(需要事务)

    // 非接口查询(只读事务)

    // 私有方法(没有事务)

    // 注入
    @Override
    @Autowired
    public void setMapper(UserMapper mapper){
        this.mapper = mapper;
    }
}
