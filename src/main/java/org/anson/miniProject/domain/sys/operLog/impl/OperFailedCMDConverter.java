package org.anson.miniProject.domain.sys.operLog.impl;

import org.anson.miniProject.domain.sys.operLog.cmd.OperFailedCMD;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface OperFailedCMDConverter {
    @Mapping(target = "clientKey", source = "clientKey.key")
    OperLog toOperLog(OperFailedCMD cmd);
}
