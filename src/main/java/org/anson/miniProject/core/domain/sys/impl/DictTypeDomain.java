package org.anson.miniProject.core.domain.sys.impl;

import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.core.domain.sys.IDelRecordDomain;
import org.anson.miniProject.core.domain.sys.IDictDomain;
import org.anson.miniProject.core.domain.sys.IDictTypeDomain;
import org.anson.miniProject.core.model.dmo.sys.DelRecordDMO;
import org.anson.miniProject.core.model.dmo.sys.dictType.AddDictTypeDmo;
import org.anson.miniProject.core.model.dmo.sys.dictType.MdfDictTypeDmo;
import org.anson.miniProject.core.model.po.sys.DictType;
import org.anson.miniProject.core.repository.sys.DictTypeRep;
import org.anson.miniProject.framework.jackson.Jackson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    public String add(AddDictTypeDmo dmo, String operUserId, Date operTime) throws Exception{
        // 新增数据字典类型
        DictType dictType = AddDictTypeDmo.toDictType(dmo);

        String dictTypeId = this.dictTypeRep.insert(dictType, operUserId, operTime);

        // 新增数据字典
        /*
        if (IterUtil.isNotEmpty(dmo.getDictDmoList())){
            this.dictDomain.batchAdd(dmo.getDictDmoList(), operUserId, operTime);
        }
         */

        return dictTypeId;
    }

    @Override
    public void mdf(MdfDictTypeDmo dmo, String operUserId, Date operTime) throws Exception{
        // 修改数据字典类型
        DictType dictType = MdfDictTypeDmo.toDictType(dmo);

        this.dictTypeRep.update(dictType, operUserId, operTime);

        // save 数据字典
        /*
        if (IterUtil.isNotEmpty(dmo.getDictDmoList())){
            this.dictDomain.batchSave(dmo.getDictDmoList(), operUserId, operTime);
        }
         */
    }

    @Override
    public void del(String dictTypeId, String operUserId, Date operTime) throws Exception {
        // 查询删除的数据
        log.debug(dictTypeId);
        DictType dictType = this.dictTypeRep.selectById(dictTypeId);

        if (dictType == null){
            return;
        }

        // 记录要删除的数据
        DelRecordDMO delRecordDmo = DelRecordDMO.builder()
                                                .tableName(DictType.__TABLENAME)
                                                .pk(dictTypeId)
                                                .record(jackson.toJson(dictType))
                                                .build();
        this.delRecordDomain.record(delRecordDmo, operUserId, operTime);

        // 删除数据字典类型
        this.dictTypeRep.del(dictTypeId);

        // 删除数据字典
        this.dictDomain.delByDictTypeId(dictTypeId, operUserId, operTime);
    }
}
