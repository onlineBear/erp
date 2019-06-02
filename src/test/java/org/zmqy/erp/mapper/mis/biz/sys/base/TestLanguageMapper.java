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

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TestLanguageMapper {
    @Autowired
    private LanguageMapper mapper;

    private Language language = new Language();

    @Test
    @Transactional
    @Rollback(false)
    public void insert(){

        Date nowTime = new Date();

        language.setLangNo("en");
        language.setLangName("English");
        language.setAreUsing(true);
        language.setChineseName("英文");
        language.setCreateTime(nowTime);
        language.setCreateUserId("");
        language.setLastUpdateTime(nowTime);

        mapper.insert(language);
    }
}
