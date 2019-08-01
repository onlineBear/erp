package org.anson.miniProject.domain.sys.operLog.impl;

import org.anson.miniProject.domain.sys.operLog.cmd.OperFailedCMD;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface OperFailedCMDMapper {
    public OperLog to(OperFailedCMD cmd);
}
