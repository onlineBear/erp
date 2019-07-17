package org.anson.miniProject.core.domain.eg;

import org.anson.miniProject.core.repository.eg.impl.ExampleRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(rollbackFor = Exception.class)
public class ExampleDomain implements IExampleDomain{
    @Autowired
    private ExampleRep rep;

}
