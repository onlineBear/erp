package org.zmqy.erp.mapper.mis.biz.sys.base;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.zmqy.erp.model.mis.entity.sys.base.Menu;
import org.zmqy.erp.model.mis.entity.sys.base.MenuLang;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TestMenuLangMapper {
    @Autowired
    private MenuLangMapper mapper;

    private MenuLang menuLang = new MenuLang();

    @Test
    @Transactional
    @Rollback(false)
    public void insert(){

        Date nowTime = new Date();

        menuLang.setLangId("1073471954843422721");
        menuLang.setMenuId("1074519040728842242");
        menuLang.setMenuName("列信息设置");
        menuLang.setMenuDesc("");

        menuLang.setCreateUserId("");
        menuLang.setCreateTime(nowTime);
        menuLang.setLastUpdateTime(nowTime);

        mapper.insert(menuLang);
    }
}
