package org.anson.miniProject.service;

import org.anson.miniProject.core.biz.ExampleBiz;
import org.anson.miniProject.core.model.bo.example.ExampleAddBo;
import org.anson.miniProject.core.model.dto.service.AddExampleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional(rollbackFor = Exception.class)
public class ExampleService {
    @Autowired
    private ExampleBiz biz;

    public String add(AddExampleDTO dto, String operUserId, Date operTime) throws Exception {
        ExampleAddBo exampleAddBo = AddExampleDTO.toExampleAddBo(dto);
        return this.biz.add(exampleAddBo, operUserId, operTime);
    }
}
