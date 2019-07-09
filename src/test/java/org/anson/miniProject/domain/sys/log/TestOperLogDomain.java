package org.anson.miniProject.domain.sys.log;

import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.constrant.dict.ClientEnum;
import org.anson.miniProject.core.domain.sys.log.IOperLogDomain;
import org.anson.miniProject.core.model.dmo.sys.log.operLog.OperFailedDmo;
import org.anson.miniProject.core.model.dmo.sys.log.operLog.OperSuccessDmo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TestOperLogDomain {
    @Autowired
    private IOperLogDomain domain;

    @Test
    @Transactional
    @Rollback(false)
    public void testOperSuccess() throws Exception {
        OperSuccessDmo dmo = OperSuccessDmo.builder()
                                        .ipv4("ipv4")
                                        .operMenuId("operMenuId")
                                        .operTypeKey("operTypeKey")
                                        .clientKey(ClientEnum.PC)
                                        .longitude(1.0)
                                        .latitude(2.0)
                                        .pk("pk")
                                        .mainTableName("mainTableName")
                                        .description("description")
                                        .build();
        this.domain.operSuccess(dmo, "test", new Date());
    }

    @Test
    @Transactional
    @Rollback(false)
    public void testOperFailed() throws Exception {
        OperFailedDmo dmo = OperFailedDmo.builder()
                .operMenuId("operMenuId")
                .operTypeKey("operTypeKey")
                .description("description")
                .failReason("failReason")
                .pk("pk")
                .clientKey(ClientEnum.PC)
                .mainTableName("mainTableName")
                .ipv4("ipv4")
                .longitude(2.0)
                .latitude(3.3)
                .build();
        this.domain.operFailed(dmo, "test", new Date());
    }
}
