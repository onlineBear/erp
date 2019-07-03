package org.anson.miniProject.domain.sys.impl;

import cn.hutool.core.collection.IterUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.domain.sys.IDelRecordDomain;
import org.anson.miniProject.domain.sys.IDictDomain;
import org.anson.miniProject.domain.sys.IDictTypeDomain;
import org.anson.miniProject.framework.jackson.Jackson;
import org.anson.miniProject.model.bo.sys.DelRecordBo;
import org.anson.miniProject.model.bo.sys.DictTypeBo;
import org.anson.miniProject.model.entity.sys.DictType;
import org.anson.miniProject.repository.sys.DictTypeRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Date;

@Component
@Slf4j
public class DictTypeDomain implements IDictTypeDomain {
    @Autowired
    private DictTypeRep dictTypeRep;
    @Autowired
    private IDictDomain dictDomain;
    @Autowired
    private IDelRecordDomain delRecordDomain;
    @Autowired
    private Jackson jackson;

    @Override
    @Validated(DictTypeBo.add.class)
    public String add(@Valid DictTypeBo bo, String operUserId, Date operTime) {
        // 新增数据字典类型
        DictType dictType = DictTypeBo.bo2entity(bo);

        String dictTypeId = this.dictTypeRep.insert(dictType, operUserId, operTime);

        // 新增数据字典
        if (IterUtil.isNotEmpty(bo.getDictBoList())){
            this.dictDomain.batchAdd(bo.getDictBoList(), operUserId, operTime);
        }

        return dictTypeId;
    }

    @Override
    @Validated(DictTypeBo.mdf.class)
    public void mdf(@Valid DictTypeBo bo, String operUserId, Date operTime) {
        // 修改数据字典类型
        DictType dictType = DictTypeBo.bo2entity(bo);

        this.dictTypeRep.update(dictType, operUserId, operTime);

        // save 数据字典
        if (IterUtil.isNotEmpty(bo.getDictBoList())){
            this.dictDomain.batchSave(bo.getDictBoList(), operUserId, operTime);
        }
    }

    @Override
    public void del(String dictTypeId, String operUserId, Date operTime) throws JsonProcessingException {
        // 查询删除的数据
        log.debug(dictTypeId);
        DictType dictType = this.dictTypeRep.selectById(dictTypeId);

        if (dictType == null){
            return;
        }

        // 记录要删除的数据
        DelRecordBo delRecordBo = new DelRecordBo(DictType.__TABLENAME, dictTypeId, jackson.toJson(dictType));
        this.delRecordDomain.record(delRecordBo, operUserId, operTime);

        // 删除数据字典类型
        this.dictTypeRep.del(dictTypeId);

        // 删除数据字典
        this.dictDomain.delByDictTypeId(dictTypeId, operUserId, operTime);
    }
}
