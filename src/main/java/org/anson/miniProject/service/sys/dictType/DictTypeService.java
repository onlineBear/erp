package org.anson.miniProject.service.sys.dictType;

import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.service.sys.dictType.model.AddDictTypeDTO;
import org.anson.miniProject.service.sys.dictType.model.AddDictTypeDTOConverter;
import org.anson.miniProject.service.sys.dictType.model.UpdDictTypeDTO;
import org.anson.miniProject.core.model.vo.common.AddCommonVO;
import org.anson.miniProject.domain.sys.dictType.IDictTypeDMService;
import org.anson.miniProject.domain.sys.dictType.cmd.AddDictTypeCMD;
import org.anson.miniProject.domain.sys.dictType.cmd.UpdDictTypeCMD;
import org.anson.miniProject.framework.pojo.CommonParam;
import org.anson.miniProject.service.sys.dictType.model.UpdDictTypeDTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class DictTypeService {
    public AddCommonVO addDictType(AddDictTypeDTO dto, CommonParam commonParam) throws Exception{
        AddDictTypeCMD cmd = addDictTypeDTOConverter.toAddDictTypeCMD(dto);
        String id = this.dictTypeDMService.addDictType(cmd);
        AddCommonVO vo = new AddCommonVO(id);
        return vo;
    }

    public void mdfDictType(UpdDictTypeDTO dto, CommonParam commonParam) throws Exception{
        UpdDictTypeCMD cmd = updDictTypeDTOConverter.toUpdDictTypeCMD(dto);
        this.dictTypeDMService.updateDictType(cmd);
    }

    @Autowired
    private IDictTypeDMService dictTypeDMService;
    @Autowired
    private AddDictTypeDTOConverter addDictTypeDTOConverter;
    @Autowired
    private UpdDictTypeDTOConverter updDictTypeDTOConverter;
}
