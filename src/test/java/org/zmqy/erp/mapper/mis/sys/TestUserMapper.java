package org.zmqy.erp.mapper.mis.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.zmqy.erp.mapper.mis.biz.sys.base.UserMapper;
import org.zmqy.erp.model.mis.entity.sys.base.User;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TestUserMapper {
    @Autowired
    private UserMapper mapper;

    private User user = new User();

    @Test
    @Transactional
    @Rollback(false)
    public void insert(){
        Date nowTime = new Date();
        /*
        user.setUserNo("test");
        user.setUserName("测试用户");
        user.setPassword("");

        user.setCreateUserId("");
        user.setCreateTime(nowTime);
        user.setLastUpdateTime(nowTime);

        mapper.insert(user);
        */

        //User a = mapper.selectById("1073481048329289730");


        Map map = new HashMap();
        map.put(User.USERNO, "dev");

        List<User> userList = mapper.selectByMap(map);

        for(User user : userList){
            log.info(user.toString());
        }
    }
}
