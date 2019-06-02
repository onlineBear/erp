package org.zmqy.erp.mapper.mis.biz.sys.security;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.zmqy.erp.mapper.mis.biz.sys.base.MenuMapper;
import org.zmqy.erp.model.mis.entity.sys.base.Menu;
import org.zmqy.erp.model.mis.entity.sys.security.Role;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TestRoleMapper {
    @Autowired
    private RoleMapper mapper;

    private Role role = new Role();

    @Test
    @Transactional
    @Rollback(false)
    public void insert(){

        Date nowTime = new Date();

        role.setRoleNo("test");

        role.setCreateUserId("");
        role.setCreateTime(nowTime);
        role.setLastUpdateTime(nowTime);

        mapper.insert(role);
    }
}
