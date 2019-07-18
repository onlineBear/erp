package org.anson.miniProject.core.domain.sys.base.impl;

import cn.hutool.core.collection.IterUtil;
import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.core.domain.sys.base.IDictDomain;
import org.anson.miniProject.core.domain.sys.base.IDictTypeDomain;
import org.anson.miniProject.core.model.bo.sys.base.DictTypeBO;
import org.anson.miniProject.core.model.param.sys.base.dict.AddDictParam;
import org.anson.miniProject.core.model.param.sys.base.dict.MdfDictParam;
import org.anson.miniProject.core.model.param.sys.dictType.AddDictTypeParam;
import org.anson.miniProject.core.model.param.sys.dictType.MdfDictTypeParam;
import org.anson.miniProject.core.model.po.sys.base.DictType;
import org.anson.miniProject.core.repository.sys.base.impl.DictTypeRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class DictTypeDomain implements IDictTypeDomain {
    @Autowired
    private DictTypeRep rep;
    @Autowired
    private IDictDomain dictDomain;

    @Override
    public String add(AddDictTypeParam param, String operUserId, Date operTime) throws Exception{
        // 新增数据字典类型
        DictTypeBO bo = AddDictTypeParam.toBO(param);
        DictType po = bo.toDictType();

        String dictTypeId = this.rep.insert(po, operUserId, operTime);

        // 新增数据字典
        List<AddDictParam> addDictParamList = param.getDictList();
        if (IterUtil.isNotEmpty(addDictParamList)){
            for (AddDictParam addDictParam : addDictParamList){
                addDictParam.setDictTypeId(po.getId());     // 设置数据字典类型id
                this.dictDomain.add(addDictParam, operUserId, operTime);
            }
        }

        return dictTypeId;
    }

    @Override
    public void mdf(MdfDictTypeParam param, String operUserId, Date operTime) throws Exception{
        // 修改数据字典类型
        DictTypeBO bo = MdfDictTypeParam.toBO(param);
        DictType dictType = bo.toDictType();

        this.rep.update(dictType, operUserId, operTime);

        // 数据字典处理
        List<AddDictParam> addDictList = param.getAddDictList();
        this.dictDomain.batchAdd(addDictList, operUserId, operTime);

        List<MdfDictParam> mdfDictList = param.getMdfDictList();
        this.dictDomain.batchMdf(mdfDictList, operUserId, operTime);
    }
}
