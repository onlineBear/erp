package org.anson.miniProject.core.domain;

import org.anson.miniProject.core.model.dmo.example.ExampleAddDo;

import java.util.Date;

public interface IExampleDomain {
    String add(ExampleAddDo dmo, String operUserId, Date operTime) throws Exception;
}
