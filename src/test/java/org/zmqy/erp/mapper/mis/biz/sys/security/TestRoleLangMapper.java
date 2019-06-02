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
import org.zmqy.erp.model.mis.entity.sys.security.RoleLang;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TestRoleLangMapper {
    @Autowired
    private RoleLangMapper mapper;

    private RoleLang roleLang = new RoleLang();

    @Test
    @Transactional
    @Rollback(false)
    public void insert(){

        Date nowTime = new Date();

        roleLang.setLangId("1073471954843422721");
        roleLang.setRoleId("1073764440799272961");

        roleLang.setRoleName("测试角色");
        roleLang.setRoleDesc("测试角色");

        roleLang.setCreateUserId("");
        roleLang.setCreateTime(nowTime);
        roleLang.setLastUpdateTime(nowTime);

        mapper.insert(roleLang);
    }
}
