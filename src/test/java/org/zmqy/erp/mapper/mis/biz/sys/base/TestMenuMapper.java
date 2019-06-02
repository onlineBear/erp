package org.zmqy.erp.mapper.mis.biz.sys.base;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.zmqy.erp.model.mis.entity.sys.base.Language;
import org.zmqy.erp.model.mis.entity.sys.base.Menu;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TestMenuMapper {
    @Autowired
    private MenuMapper mapper;

    private Menu menu = new Menu();

    @Test
    @Transactional
    @Rollback(false)
    public void insert(){

        Date nowTime = new Date();

        menu.setMenuNo("pc010205");
        menu.setParentMenuId("1073758836143759362");
        menu.setAreCatalog(false);
        menu.setAreDcDisplay(true);
        menu.setAreHqDisplay(true);
        menu.setAreShopDisplay(true);
        menu.setIconUrl("");
        menu.setMenuLevel(5);
        menu.setSeq(1);
        menu.setMenuClientNo("menuClient-pc");

        menu.setCreateUserId("");
        menu.setCreateTime(nowTime);
        menu.setLastUpdateTime(nowTime);

        mapper.insert(menu);
    }
}
