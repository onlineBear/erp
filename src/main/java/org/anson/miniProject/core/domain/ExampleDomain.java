package org.anson.miniProject.core.domain;

import org.anson.miniProject.core.model.dmo.example.ExampleAddDmo;
import org.anson.miniProject.core.repository.ExampleRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional(rollbackFor = Exception.class)
public class ExampleDomain implements IExampleDomain{
    @Autowired
    private ExampleRep rep;

    @Override
    public String add(ExampleAddDmo dmo, String operUserId, Date operTime) throws Exception {
        return this.rep.insert(ExampleAddDmo.toExamplePo(dmo), operUserId, operTime);
    }
}
