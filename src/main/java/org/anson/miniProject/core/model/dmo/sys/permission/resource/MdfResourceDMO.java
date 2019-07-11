package org.anson.miniProject.core.model.dmo.sys.permission.resource;

import lombok.Data;
import org.anson.miniProject.core.model.po.sys.permission.Resource;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

@Data
public class MdfResourceDMO {
    private String id;

    private String menuId;

    private String name;
    private String description;
    private String url;

    private static final BeanCopier toResourceCopier = BeanCopier.create(MdfResourceDMO.class, Resource.class, false);

    public static Resource toResource(MdfResourceDMO dmo) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(dmo, Resource.class, toResourceCopier);
    }
}
