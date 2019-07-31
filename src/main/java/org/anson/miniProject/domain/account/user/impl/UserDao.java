package org.anson.miniProject.domain.account.user.impl;

import org.anson.miniProject.domain.base.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional(rollbackFor = Exception.class)
class UserDao extends BaseDao<User, UserMapper> {

    public String insert(User user){
        return null;
    }

    public void updateById(User user){

    }

    public void deleteById(String id){

    }

    // 注入
    @Autowired
    @Override
    protected void setMapper(UserMapper mapper) {
        this.mapper = mapper;
    }
    private Date operTime = new Date();
}
