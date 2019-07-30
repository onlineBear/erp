package org.anson.miniProject.domain.sys.menu.impl;

import org.anson.miniProject.domain.base.BaseDao;
import org.anson.miniProject.domain.internal.deletedRecord.DelHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional(rollbackFor = Exception.class)
class MenuDao extends BaseDao<Menu, MenuMapper> {

    public String insert(Menu menu){
        return null;
    }

    public void updateById(Menu menu){

    }

    public void deleteById(String id){

    }

    @Autowired
    @Override
    protected void setMapper(MenuMapper mapper) {
        this.mapper = mapper;
    }
    @Autowired
    private DelHelper delHelper;
    private String operUserId = "createUserId";
    private Date operTime = new Date();
}
