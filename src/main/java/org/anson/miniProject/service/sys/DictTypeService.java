package org.anson.miniProject.service.sys;

import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.core.biz.sys.DictTypeBiz;
import org.anson.miniProject.core.model.bo.sys.AddDictTypeBO;
import org.anson.miniProject.core.model.bo.sys.MdfDictTypeBO;
import org.anson.miniProject.core.model.service.dictType.AddDictTypeDTO;
import org.anson.miniProject.core.model.service.dictType.DictTypeAddVo;
import org.anson.miniProject.core.model.service.dictType.DictTypeMdfDTO;
import org.anson.miniProject.framework.log.service.ServiceLog;
import org.anson.miniProject.framework.pojo.CommonParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class DictTypeService {
    @Autowired
    private DictTypeBiz biz;

    @ServiceLog(description="新增了数据字典类型", pkCalssFrom="return", mainTableName="dictType")
    public DictTypeAddVo addDictType(AddDictTypeDTO dto, CommonParam commonParam) throws Exception{
        AddDictTypeBO bo = AddDictTypeDTO.toAddDictTypeBO(dto);
        String dictTypeId = this.biz.addDictType(bo, commonParam.getLoginUserId(), commonParam.getOperTime());
        DictTypeAddVo vo = new DictTypeAddVo(dictTypeId);
        return vo;
    }

    public void mdfDictType(DictTypeMdfDTO dto, CommonParam commonParam) throws Exception{
        MdfDictTypeBO bo = DictTypeMdfDTO.toMdfDictTypeBOCopier(dto);
        this.biz.mdfDictType(bo, commonParam.getLoginUserId(), commonParam.getOperTime());
        return;
    }

    public void delDictType(String dictTypeId, CommonParam commonParam) throws Exception {
        this.biz.delDictType(dictTypeId, commonParam.getLoginUserId(), commonParam.getOperTime());
        return;
    }

}
