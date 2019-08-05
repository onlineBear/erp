package org.anson.miniProject.domain.sys.operLog.impl;

import org.anson.miniProject.domain.sys.operLog.cmd.OperSuccessCMD;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
abstract class OperSuccessCMDConverter {
    @Mapping(target = "clientKey", source = "clientKey.key")
    abstract OperLog toOperLog(OperSuccessCMD cmd);
}
