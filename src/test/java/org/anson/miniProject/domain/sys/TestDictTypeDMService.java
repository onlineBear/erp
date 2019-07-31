package org.anson.miniProject.domain.sys;

import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.domain.sys.dictType.IDictTypeDMService;
import org.anson.miniProject.domain.sys.dictType.cmd.AddDictTypeCMD;
import org.anson.miniProject.domain.sys.dictType.cmd.UpdDictTypeCMD;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TestDictTypeDMService {
    @Autowired
    private IDictTypeDMService dictTypeDMService;

    @Test
    @Transactional
    @Rollback(false)
    public void testAddDictType() throws Exception {
        AddDictTypeCMD cmd = new AddDictTypeCMD();

        cmd.setNo("thing");
        cmd.setName("name");
        cmd.setDescription("description");

        this.dictTypeDMService.addDictType(cmd);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void testUpdateDictType() throws Exception {
        UpdDictTypeCMD cmd = new UpdDictTypeCMD();

        cmd.setId("");
        cmd.setDescription("desc");

        this.dictTypeDMService.updateDictType(cmd);
    }
}
