package org.anson.miniProject.core.domain;

import org.anson.miniProject.core.model.dmo.example.ExampleAddDo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional(rollbackFor = Exception.class)
public class ExampleDomain implements IExampleDomain{
    @Override
    public String add(ExampleAddDo dmo, String operUserId, Date operTime) {
        return null;
    }
}
