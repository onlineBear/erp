package org.anson.miniProject.core.domain;

import org.anson.miniProject.core.model.dmo.example.ExampleAddDMO;

import java.util.Date;

public interface IExampleDomain {
    String add(ExampleAddDMO dmo, String operUserId, Date operTime) throws Exception;
}
