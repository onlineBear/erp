package org.anson.miniProject.core.domain.sys.base.impl;

import cn.hutool.core.collection.IterUtil;
import org.anson.miniProject.core.domain.sys.base.IDictDomain;
import org.anson.miniProject.core.model.bo.sys.base.DictBO;
import org.anson.miniProject.core.model.param.sys.base.dict.AddDictParam;
import org.anson.miniProject.core.model.param.sys.base.dict.MdfDictParam;
import org.anson.miniProject.core.model.po.sys.base.Dict;
import org.anson.miniProject.core.repository.sys.base.impl.DictRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Component
@Transactional(rollbackFor = Exception.class)
public class DictDomain implements IDictDomain {
    @Autowired
    private DictRep rep;

    @Override
    public String add(AddDictParam param, String operUserId, Date operTime) throws Exception{
        DictBO bo = param.toBO();
        // 新增数据字典
        Dict entity = bo.toDict();

        return this.rep.insert(entity, operUserId, operTime);
    }

    @Override
    public void batchAdd(List<AddDictParam> paramList, String operUserId, Date operTime) throws Exception{
        if (IterUtil.isNotEmpty(paramList)){
            for (AddDictParam bo : paramList){
                this.add(bo, operUserId, operTime);
            }
        }
    }

    @Override
    public void mdf(MdfDictParam param, String operUserId, Date operTime) throws Exception{
        // 修改数据字典
        DictBO bo = param.toBO();

        Dict entity = bo.toDict();

        this.rep.update(entity, operTime);
    }

    @Override
    public void batchMdf(List<MdfDictParam> paramList, String operUserId, Date operTime) throws Exception{
        if (IterUtil.isNotEmpty(paramList)){
            for (MdfDictParam param : paramList){
                this.mdf(param, operUserId, operTime);
            }
        }
    }

    @Override
    public void del(String dictId, String operUserId, Date operTime) throws Exception {
        this.rep.delByDictType(dictId, operUserId, operTime);
    }

    @Override
    public void delByDictTypeId(String dictTypeId, String operUserId, Date operTime) throws Exception {
        this.rep.delByDictType(dictTypeId, operUserId, operTime);
    }
}
