package org.anson.miniProject.core.repository.eg.impl;

import org.anson.miniProject.core.mapper.ExampleMapper;
import org.anson.miniProject.core.model.po.Example;
import org.anson.miniProject.core.repository.BaseRep;
import org.anson.miniProject.core.repository.eg.IExampleRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional(rollbackFor = Exception.class)
public class ExampleRep extends BaseRep<Example, ExampleMapper>
                        implements IExampleRep {

    // 接口命令(需要事务)
    public String insert(Example po, String operUserId, Date operTime){

        po.setNo(po.getId());
        po.setCreateUserId(operUserId);
        po.setCreateTime(operTime);
        po.setLastUpdateTime(operTime);

        this.mapper.insert(po);

        return po.getId();
    }

    // 接口查询(只读事务)

    // 非接口命令(需要事务)

    // 非接口查询(只读事务)

    // 私有方法(没有事务)

    // 注入
    @Override
    @Autowired
    public void setMapper(ExampleMapper mapper){
        this.mapper = mapper;
    }
}
