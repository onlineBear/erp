package org.anson.miniProject.domain.sys.dictType.impl;

import cn.hutool.core.collection.CollUtil;
import org.anson.miniProject.domain.base.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Component
@Transactional(rollbackFor = Exception.class)
class DictDao extends BaseDao<Dict, DictMapper> {
    public String insert(Dict po){
        po.setId(po.getNo());
        po.setCreateUserId(operUserId);
        po.setCreateTime(operTime);
        po.setLastUpdateTime(operTime);

        this.mapper.insert(po);

        return po.getId();
    }

    public void batchInsert(List<Dict> dictList){
        if (CollUtil.isEmpty(dictList)){
            return;
        }

        for (Dict dict : dictList){
            this.insert(dict);
        }
    }

    public void updateById(Dict dict){
        dict.setNo(null); // 编码不可修改
        dict.setCreateUserId(null);
        dict.setCreateTime(null);
        dict.setLastUpdateTime(operTime);

        this.mapper.updateById(dict);
    }

    public void batchUpdateById(List<Dict> dictList){
        if (CollUtil.isEmpty(dictList)){
            return;
        }

        for (Dict dict : dictList){
            this.updateById(dict);
        }
    }

    public void deleteById(String dictId){

    }

    public void deleteByDictType(String dictTypeId, List<String> dictId){

    }

    public void deleteByDictType(String dictTypeId){

    }

    // 注入
    @Autowired
    @Override
    protected void setMapper(DictMapper mapper) {
        this.mapper = mapper;
    }

    private String operUserId = "operUserId";
    private Date operTime = new Date();
}
