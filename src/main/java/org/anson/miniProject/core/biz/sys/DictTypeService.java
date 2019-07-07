package org.anson.miniProject.core.biz.sys;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.core.domain.sys.IDictTypeDomain;
import org.anson.miniProject.core.model.dmo.sys.DictTypeDmo;
import org.anson.miniProject.core.model.service.dictType.DictTypeAddDTO;
import org.anson.miniProject.core.model.service.dictType.DictTypeAddVo;
import org.anson.miniProject.core.model.service.dictType.DictTypeMdfDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class DictTypeService {
    @Autowired
    private IDictTypeDomain domain;

    public DictTypeAddVo addDictType(DictTypeAddDTO dto, String operUserId, Date operTime){
        DictTypeDmo bo = DictTypeAddDTO.dto2bo(dto);
        String dictTypeId = this.domain.add(bo, operUserId, operTime);
        DictTypeAddVo vo = new DictTypeAddVo(dictTypeId);
        return vo;
    }

    public void mdfDictType(DictTypeMdfDTO dto, String operUserId, Date operTime){
        DictTypeDmo bo = DictTypeMdfDTO.dto2bo(dto);
        this.domain.mdf(bo, operUserId, operTime);
        return;
    }

    public void delDictType(String dictTypeId, String operUserId, Date operTime) throws JsonProcessingException {
        this.domain.del(dictTypeId, operUserId, operTime);
        return;
    }


}
