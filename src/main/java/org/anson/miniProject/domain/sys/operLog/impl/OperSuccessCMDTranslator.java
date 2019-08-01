package org.anson.miniProject.domain.sys.operLog.impl;

import lombok.Data;
import org.anson.miniProject.domain.sys.operLog.cmd.OperSuccessCMD;
import org.anson.miniProject.tool.bean.BeanUtils;

@Data
class OperSuccessCMDTranslator {
    public static OperLog toOperLog(OperSuccessCMD cmd) throws InstantiationException, IllegalAccessException {
        OperLog operLog = BeanUtils.beanToBean(cmd, OperLog.class);

        return operLog;
    }
}
