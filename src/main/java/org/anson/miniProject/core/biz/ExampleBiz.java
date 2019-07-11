package org.anson.miniProject.core.biz;

import org.anson.miniProject.core.domain.IExampleDomain;
import org.anson.miniProject.core.model.bo.AddExampleBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional(rollbackFor = Exception.class)
public class ExampleBiz {
    @Autowired
    private IExampleDomain domain;

    public String add(AddExampleBO bo, String operUserId, Date operTime) throws Exception{
        return this.domain.add(AddExampleBO.toExampleAddDo(bo), operUserId, operTime);
    }
}
