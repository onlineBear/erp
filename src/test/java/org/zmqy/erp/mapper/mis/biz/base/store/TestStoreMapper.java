package org.zmqy.erp.mapper.mis.biz.base.store;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.zmqy.erp.mapper.mis.biz.sys.base.LanguageMapper;
import org.zmqy.erp.model.mis.entity.base.store.Store;
import org.zmqy.erp.model.mis.entity.sys.base.Language;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TestStoreMapper {
    @Autowired
    private StoreMapper mapper;

    private Store store = new Store();

    @Test
    @Transactional
    @Rollback(false)
    public void insert(){

        Date nowTime = new Date();

        store.setStoreNo("0001");
        store.setStoreTypeNo("storeType-shop");

        store.setCreateUserId("");
        store.setCreateTime(nowTime);
        store.setLastUpdateTime(nowTime);

        mapper.insert(store);
    }
}
