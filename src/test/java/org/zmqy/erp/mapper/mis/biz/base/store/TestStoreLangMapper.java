package org.zmqy.erp.mapper.mis.biz.base.store;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.zmqy.erp.model.mis.entity.base.store.Store;
import org.zmqy.erp.model.mis.entity.base.store.StoreLang;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TestStoreLangMapper {
    @Autowired
    private StoreLangMapper mapper;

    private StoreLang storeLang = new StoreLang();

    @Test
    @Transactional
    @Rollback(false)
    public void insert(){

        Date nowTime = new Date();

        storeLang.setStoreId("1073475183501213698");
        storeLang.setLangId("zh-CN");

        storeLang.setStoreName("分店0001");
        storeLang.setStoreShortName("分店0001");

        storeLang.setCreateUserId("");
        storeLang.setCreateTime(nowTime);
        storeLang.setLastUpdateTime(nowTime);

        mapper.insert(storeLang);
    }
}
