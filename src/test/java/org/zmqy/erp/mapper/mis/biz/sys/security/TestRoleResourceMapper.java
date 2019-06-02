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
import org.zmqy.erp.model.mis.entity.sys.security.RoleResource;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TestRoleResourceMapper {
    @Autowired
    private RoleResourceMapper mapper;

    private RoleResource roleResource = new RoleResource();

    @Test
    @Transactional
    @Rollback(false)
    public void insert(){

        Date nowTime = new Date();

        roleResource.setRoleId("1073764299044311041");
        roleResource.setResId("1074945769284829186");

        roleResource.setCreateUserId("");
        roleResource.setCreateTime(nowTime);
        roleResource.setLastUpdateTime(nowTime);

        mapper.insert(roleResource);
    }
}
