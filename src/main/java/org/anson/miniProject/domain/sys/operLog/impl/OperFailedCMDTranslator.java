package org.anson.miniProject.domain.sys.operLog.impl;

import lombok.Data;
import org.anson.miniProject.domain.sys.operLog.cmd.OperFailedCMD;
import org.anson.miniProject.tool.bean.BeanUtils;

@Data
class OperFailedCMDTranslator {
    public static OperLog toOperLog(OperFailedCMD cmd) throws InstantiationException, IllegalAccessException {
        OperLog operLog = BeanUtils.beanToBean(cmd, OperLog.class);

        return operLog;
    }
}
