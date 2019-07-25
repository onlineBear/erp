package org.anson.miniProject.domain.sys.dictType.impl;

import org.anson.miniProject.domain.sys.dictType.cmd.UpdateDictTypeCMD;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TestMapper {
    TestMapper INSTANCE = Mappers.getMapper(TestMapper.class);

    DictTypeEntity toDictEntity(UpdateDictTypeCMD cmd);

    @Mapping(target = "dict.addDictList.no", source = "entity.dictList.no")
    void toDTO(DictTypeEntity entity, @MappingTarget UpdateDictTypeCMD cmd);
}
