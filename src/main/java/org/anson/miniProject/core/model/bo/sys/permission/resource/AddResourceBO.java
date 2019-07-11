package org.anson.miniProject.core.model.bo.sys.permission.resource;

import lombok.Data;
import org.anson.miniProject.core.model.dmo.sys.permission.resource.AddResourceDMO;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

@Data
public class AddResourceBO {
    private String menuId;

    private String name;
    private String description;
    private String url;

    private static final BeanCopier toAddResourceDMOCopier = BeanCopier.create(AddResourceBO.class, AddResourceDMO.class, false);

    public static AddResourceDMO toAddResourceDMO(AddResourceBO dmo) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(dmo, AddResourceDMO.class, toAddResourceDMOCopier);
    }
}
