package org.zmqy.erp.mapper.mis.biz.sys.security;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.zmqy.erp.model.mis.entity.sys.security.Role;
import org.zmqy.erp.model.mis.entity.sys.security.RoleUser;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TestRoleUserMapper {
    @Autowired
    private RoleUserMapper mapper;

    private RoleUser roleUser = new RoleUser();

    @Test
    @Transactional
    @Rollback(false)
    public void insert(){

        Date nowTime = new Date();

        roleUser.setRoleId("1073764440799272961");
        roleUser.setUserId("1073481229829517314");

        roleUser.setCreateUserId("");
        roleUser.setCreateTime(nowTime);
        roleUser.setLastUpdateTime(nowTime);

        mapper.insert(roleUser);
    }
}
