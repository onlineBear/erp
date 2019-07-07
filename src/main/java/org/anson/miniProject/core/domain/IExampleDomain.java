package org.anson.miniProject.core.domain;

import org.anson.miniProject.core.model.dmo.example.ExampleAddDmo;

import java.util.Date;

public interface IExampleDomain {
    String add(ExampleAddDmo dmo, String operUserId, Date operTime) throws Exception;
}
