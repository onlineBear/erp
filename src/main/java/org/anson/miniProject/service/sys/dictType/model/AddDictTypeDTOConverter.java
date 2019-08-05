package org.anson.miniProject.service.sys.dictType.model;

import org.anson.miniProject.domain.sys.dictType.cmd.AddDictTypeCMD;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class AddDictTypeDTOConverter {
    public abstract AddDictTypeCMD toAddDictTypeCMD(AddDictTypeDTO dto);

    protected abstract List<AddDictTypeCMD.AddDictCMD> toAddDictCMDList(List<AddDictTypeDTO.AddDictDTO> dto);
    protected abstract AddDictTypeCMD.AddDictCMD toAddDictCMD(AddDictTypeDTO.AddDictDTO dto);
}
