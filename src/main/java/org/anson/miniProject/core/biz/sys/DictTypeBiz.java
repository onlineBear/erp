package org.anson.miniProject.core.biz.sys;

import org.anson.miniProject.core.domain.sys.IDictTypeDomain;
import org.anson.miniProject.core.model.bo.sys.AddDictTypeBO;
import org.anson.miniProject.core.model.bo.sys.MdfDictTypeBO;
import org.anson.miniProject.core.model.dmo.sys.dictType.AddDictTypeDmo;
import org.anson.miniProject.core.model.dmo.sys.dictType.MdfDictTypeDmo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional(rollbackFor = Exception.class)
public class DictTypeBiz {
    @Autowired
    private IDictTypeDomain domain;

    public String addDictType(AddDictTypeBO bo, String operUserId, Date operTime) throws Exception {
        AddDictTypeDmo dmo = AddDictTypeBO.toAddDictTypeDmo(bo);
        return this.domain.add(dmo, operUserId, operTime);
    }

    public void mdfDictType(MdfDictTypeBO bo, String operUserId, Date operTime) throws Exception{
        MdfDictTypeDmo dmo = MdfDictTypeBO.toMdfDictTypeDmo(bo);
        this.domain.mdf(dmo, operUserId, operTime);
        return;
    }

    public void delDictType(String dictTypeId, String operUserId, Date operTime) throws Exception {
        this.domain.del(dictTypeId, operUserId, operTime);
        return;
    }
}
