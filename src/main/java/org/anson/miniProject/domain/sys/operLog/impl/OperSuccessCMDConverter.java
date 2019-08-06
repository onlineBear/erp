package org.anson.miniProject.domain.sys.operLog.impl;

import org.anson.miniProject.domain.sys.operLog.cmd.OperSuccessCMD;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface OperSuccessCMDConverter {
    @Mapping(target = "clientKey", source = "clientKey.key")
    OperLog toOperLog(OperSuccessCMD cmd);
}
