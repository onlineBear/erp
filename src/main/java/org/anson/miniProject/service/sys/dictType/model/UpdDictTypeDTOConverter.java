package org.anson.miniProject.service.sys.dictType.model;

import org.anson.miniProject.domain.sys.dictType.cmd.UpdDictTypeCMD;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UpdDictTypeDTOConverter {
    public abstract UpdDictTypeCMD toUpdDictTypeCMD(UpdDictTypeDTO dto);

    protected abstract UpdDictTypeCMD.Dict toDict(UpdDictTypeDTO.DictDTO dto);

    // add
    protected abstract List<UpdDictTypeCMD.AddDictCMD> toAddDictCMDList(List<UpdDictTypeDTO.AddDictDTO> dtoList);
    protected abstract UpdDictTypeCMD.AddDictCMD toAddDictCMD(UpdDictTypeDTO.AddDictDTO dto);

    // upd
    protected abstract List<UpdDictTypeCMD.UpdDict> toUpdDict(List<UpdDictTypeDTO.UpdDictDTO> dtoList);
    protected abstract UpdDictTypeCMD.UpdDict toUpdDict(UpdDictTypeDTO.UpdDictDTO dto);
}
