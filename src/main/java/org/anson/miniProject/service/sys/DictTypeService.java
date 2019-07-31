package org.anson.miniProject.service.sys;

import lombok.extern.slf4j.Slf4j;
import org.anson.miniProject.core.model.dto.service.sys.dictType.AddDictTypeDTO;
import org.anson.miniProject.core.model.dto.service.sys.dictType.MdfDictTypeDTO;
import org.anson.miniProject.core.model.param.sys.dictType.AddDictTypeParam;
import org.anson.miniProject.core.model.param.sys.dictType.MdfDictTypeParam;
import org.anson.miniProject.core.model.po.sys.base.DictType;
import org.anson.miniProject.core.model.vo.common.AddCommonVO;
import org.anson.miniProject.domain.sys.dictType.IDictTypeDMService;
import org.anson.miniProject.framework.log.service.PkClassFrom;
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
    private IDictTypeDMService dictTypeDMService;

    private static final String MAINTABLENAME = DictType.__TABLENAME;

    @ServiceLog(description = "新增了数据字典类型", valKey = "no",
                pkCalssFrom = PkClassFrom.RETURN, pkKey = "id",
                mainTableName = MAINTABLENAME)
    public AddCommonVO addDictType(AddDictTypeDTO dto, CommonParam commonParam) throws Exception{
        AddDictTypeParam param = dto.toParam();
        //String dictTypeId = this.domain.add(param, commonParam.getLoginUserId(), commonParam.getOperTime());
        AddCommonVO vo = new AddCommonVO("");
        return vo;
    }

    @ServiceLog(description = "修改了数据字典类型", valKey = "no",
                pkCalssFrom = PkClassFrom.INPUT, pkKey = "id",
                mainTableName = MAINTABLENAME)
    public void mdfDictType(MdfDictTypeDTO dto, CommonParam commonParam) throws Exception{
        MdfDictTypeParam param = MdfDictTypeDTO.toParam(dto);
        //this.domain.mdf(param, commonParam.getLoginUserId(), commonParam.getOperTime());
    }

}
