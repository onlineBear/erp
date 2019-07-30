package org.anson.miniProject.domain.sys.operLog.impl;

import lombok.Data;
import org.anson.miniProject.domain.sys.operLog.cmd.OperFailedCMD;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

@Data
class OperFailedCMDTranslator {
    private static final BeanCopier toOperLogCopier = BeanCopier.create(OperFailedCMD.class, OperLog.class, false);

    public static OperLog toOperLog(OperFailedCMD cmd) throws InstantiationException, IllegalAccessException {
        if (cmd == null){
            return null;
        }

        OperLog operLog = BeanHelper.beanToBean(cmd, OperLog.class, toOperLogCopier);

        return operLog;
    }
}
