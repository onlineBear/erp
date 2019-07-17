package org.anson.miniProject.core.domain.sys.impl;

import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.core.domain.sys.IDictDomain;
import org.anson.miniProject.core.domain.sys.IDictTypeDomain;
import org.anson.miniProject.core.model.bo.sys.DictTypeBO;
import org.anson.miniProject.core.model.param.sys.dictType.AddDictTypeParam;
import org.anson.miniProject.core.model.param.sys.dictType.MdfDictTypeParam;
import org.anson.miniProject.core.model.po.sys.DictType;
import org.anson.miniProject.core.repository.sys.base.impl.DictTypeRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class DictTypeDomain implements IDictTypeDomain {
    @Autowired
    private DictTypeRep rep;
    @Autowired
    private IDictDomain dictDomain;

    @Override
    public String add(AddDictTypeParam param, String operUserId, Date operTime) throws Exception{
        DictTypeBO bo = AddDictTypeParam.toBO(param);

        // 新增数据字典类型
        DictType dictType = DictTypeBO.toDictType(bo);

        String dictTypeId = this.rep.insert(dictType, operUserId, operTime);

        // 新增数据字典
        /*
        if (IterUtil.isNotEmpty(dmo.getDictDmoList())){
            this.dictDomain.batchAdd(dmo.getDictDmoList(), operUserId, operTime);
        }
         */

        return dictTypeId;
    }

    @Override
    public void mdf(MdfDictTypeParam param, String operUserId, Date operTime) throws Exception{
        DictTypeBO bo = MdfDictTypeParam.toBO(param);

        // 修改数据字典类型
        DictType dictType = DictTypeBO.toDictType(bo);

        this.rep.update(dictType, operUserId, operTime);

        // save 数据字典
        /*
        if (IterUtil.isNotEmpty(dmo.getDictDmoList())){
            this.dictDomain.batchSave(dmo.getDictDmoList(), operUserId, operTime);
        }
         */
    }

    @Override
    public void del(String dictTypeId, String operUserId, Date operTime) throws Exception {
        // 删除数据字典类型
        this.rep.del(dictTypeId, operUserId, operTime);

        // 删除数据字典
        this.dictDomain.delByDictTypeId(dictTypeId, operUserId, operTime);
    }
}
