package org.anson.miniProject.core.repository;

import org.anson.miniProject.core.mapper.ExampleMapper;
import org.anson.miniProject.core.model.po.ExamplePo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(rollbackFor = Exception.class)
public class ExampleRep extends BaseRep<ExamplePo, ExampleMapper> {

    // 更新等 (需要事务)

    // 查询 (不需要事务)

    // 注入
    @Autowired
    public void setMapper(ExampleMapper mapper){
        this.mapper = mapper;
    }
}
