package org.anson.miniProject.service.sys;

import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.core.model.dto.service.sys.dictType.AddDictTypeDTO;
import org.anson.miniProject.core.model.dto.service.sys.dictType.UpdDictTypeDTO;
import org.anson.miniProject.core.model.vo.common.AddCommonVO;
import org.anson.miniProject.domain.sys.dictType.IDictTypeDMService;
import org.anson.miniProject.domain.sys.dictType.cmd.AddDictTypeCMD;
import org.anson.miniProject.domain.sys.dictType.cmd.UpdDictTypeCMD;
import org.anson.miniProject.framework.pojo.CommonParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class DictTypeService {
    @Autowired
    private IDictTypeDMService dictTypeDMService;

    private static final String MAINTABLENAME = "2";

    /*@ServiceLog(description = "新增了数据字典类型", valKey = "no",
                pkCalssFrom = PkClassFrom.RETURN, pkKey = "id",
                mainTableName = MAINTABLENAME)*/
    public AddCommonVO addDictType(AddDictTypeDTO dto, CommonParam commonParam) throws Exception{
        AddDictTypeCMD cmd = AddDictTypeDTO.toAddDictTypeCMD(dto);
        String id = this.dictTypeDMService.addDictType(cmd);
        AddCommonVO vo = new AddCommonVO(id);
        return vo;
    }

    /*@ServiceLog(description = "修改了数据字典类型", valKey = "no",
                pkCalssFrom = PkClassFrom.INPUT, pkKey = "id",
                mainTableName = MAINTABLENAME)*/
    public void mdfDictType(UpdDictTypeDTO dto, CommonParam commonParam) throws Exception{
        UpdDictTypeCMD cmd = UpdDictTypeDTO.toUpdDictTypeCMD(dto);
        this.dictTypeDMService.updateDictType(cmd);
    }

}
