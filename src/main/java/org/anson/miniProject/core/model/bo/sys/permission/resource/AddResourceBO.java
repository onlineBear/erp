package org.anson.miniProject.core.model.bo.sys.permission.resource;

import lombok.Data;
import org.anson.miniProject.core.model.dmo.sys.permission.resource.AddResourceParam;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

@Data
public class AddResourceBO {
    private String menuId;

    private String name;
    private String description;
    private String url;

    private static final BeanCopier toAddResourceDMOCopier = BeanCopier.create(AddResourceBO.class, AddResourceParam.class, false);

    public static AddResourceParam toAddResourceDMO(AddResourceBO dmo) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(dmo, AddResourceParam.class, toAddResourceDMOCopier);
    }
}
