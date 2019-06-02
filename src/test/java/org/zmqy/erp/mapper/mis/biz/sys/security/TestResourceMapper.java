package org.zmqy.erp.mapper.mis.biz.sys.security;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.zmqy.erp.model.mis.entity.sys.security.Resource;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TestResourceMapper {
    @Autowired
    private ResourceMapper mapper;

    private Resource resource = new Resource();

    @Test
    @Transactional
    @Rollback(false)
    public void insert(){

        Date nowTime = new Date();

        resource.setMenuId("1073759035809460226");
        resource.setResOperNo("");
        resource.setResUrl("/pc/module/pc010201/getMenuList");

        resource.setCreateUserId("");
        resource.setCreateTime(nowTime);
        resource.setLastUpdateTime(nowTime);

        mapper.insert(resource);
    }
}
