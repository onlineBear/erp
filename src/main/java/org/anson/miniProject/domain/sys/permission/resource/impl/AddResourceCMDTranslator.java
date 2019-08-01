package org.anson.miniProject.domain.sys.permission.resource.impl;

import org.anson.miniProject.domain.sys.permission.resource.cmd.AddResourceCMD;
import org.anson.miniProject.tool.bean.BeanUtils;

class AddResourceCMDTranslator {
    public static Resource toResource(AddResourceCMD cmd) throws InstantiationException, IllegalAccessException {
        Resource resource = BeanUtils.beanToBean(cmd, Resource.class);

        return resource;
    }
}
