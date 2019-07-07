package org.anson.miniProject.core.repository.sys;

import cn.hutool.core.collection.IterUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.core.mapper.account.UserMapper;
import org.anson.miniProject.core.model.po.account.User;
import org.anson.miniProject.core.repository.BaseRep;
import org.anson.miniProject.tool.helper.SecurityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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

    // super
    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public User selectById(Serializable id) {
        User po = this.mapper.selectById(id);
        po.setPassword(null);
        return po;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<User> selectBatchIds(Collection<? extends Serializable> idList) {
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
    public List<User> selectByMap(Map<String, Object> columnMap) {
        List<User> poList = this.mapper.selectByMap(columnMap);

        if (IterUtil.isEmpty(poList)) {
            return null;
        }

        if (columnMap.containsKey(User.PASSWORD)){
            for (User po : poList){
                po.setPassword(null);
            }
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

    // 注入
    @Autowired
    public void setMapper(UserMapper mapper){
        this.mapper = mapper;
    }
}
