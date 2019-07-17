package org.anson.miniProject.core.repository.eg;

import org.anson.miniProject.core.mapper.ExampleMapper;
import org.anson.miniProject.core.model.po.Example;
import org.anson.miniProject.core.repository.IBaseRep;

import java.util.Date;

public interface IExampleRep extends IBaseRep<Example, ExampleMapper> {
    String insert(Example po, String operUserId, Date operTime) throws Exception;
}
