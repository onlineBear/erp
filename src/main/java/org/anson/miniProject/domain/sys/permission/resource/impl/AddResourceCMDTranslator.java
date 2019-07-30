package org.anson.miniProject.domain.sys.permission.resource.impl;

import org.anson.miniProject.domain.sys.permission.resource.cmd.AddResourceCMD;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

class AddResourceCMDTranslator {
    private static final BeanCopier toResourceCopier = BeanCopier.create(AddResourceCMD.class, Resource.class, false);

    public static Resource toResource(AddResourceCMD cmd) throws InstantiationException, IllegalAccessException {
        if (cmd == null){
            return null;
        }

        Resource resource = BeanHelper.beanToBean(cmd, Resource.class, toResourceCopier);

        return resource;
    }
}
