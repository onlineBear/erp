package org.zmqy.erp.mapper.mis.biz.sys.security;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.zmqy.erp.model.mis.entity.sys.security.ResourceLang;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TestResourceLangMapper {
    @Autowired
    private ResourceLangMapper mapper;

    private ResourceLang resourceLang = new ResourceLang();

    @Test
    @Transactional
    @Rollback(false)
    public void insert(){

        Date nowTime = new Date();

        resourceLang.setLangId("1073471954843422721");
        resourceLang.setResId("1074945769284829186");
        resourceLang.setResName("查询菜单");
        resourceLang.setResDesc("查询菜单");

        resourceLang.setCreateUserId("");
        resourceLang.setCreateTime(nowTime);
        resourceLang.setLastUpdateTime(nowTime);

        mapper.insert(resourceLang);
    }
}
