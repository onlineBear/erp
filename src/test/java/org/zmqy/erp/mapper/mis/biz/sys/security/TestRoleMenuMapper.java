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
import org.zmqy.erp.model.mis.entity.sys.security.RoleMenu;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TestRoleMenuMapper {
    @Autowired
    private RoleMenuMapper mapper;

    private RoleMenu roleMenu = new RoleMenu();

    @Test
    @Transactional
    @Rollback(false)
    public void insert(){

        Date nowTime = new Date();

        roleMenu.setRoleId("1073764299044311041");
        roleMenu.setMenuId("1074843466803978242");

        roleMenu.setCreateUserId("");
        roleMenu.setCreateTime(nowTime);
        roleMenu.setLastUpdateTime(nowTime);

        mapper.insert(roleMenu);
    }
}
