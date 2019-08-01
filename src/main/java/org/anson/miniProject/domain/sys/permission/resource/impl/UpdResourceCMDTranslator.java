package org.anson.miniProject.domain.sys.permission.resource.impl;

import org.anson.miniProject.domain.sys.permission.resource.cmd.UpdResourceCMD;
import org.anson.miniProject.tool.bean.BeanUtils;

class UpdResourceCMDTranslator {
    public static Resource toResource(UpdResourceCMD cmd) throws InstantiationException, IllegalAccessException {
        Resource resource = BeanUtils.beanToBean(cmd, Resource.class);

        return resource;
    }
}
