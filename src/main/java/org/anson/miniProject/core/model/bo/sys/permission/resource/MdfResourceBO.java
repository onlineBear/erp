package org.anson.miniProject.core.model.bo.sys.permission.resource;

import lombok.Data;
import org.anson.miniProject.core.model.dmo.sys.permission.resource.MdfResourceDMO;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

@Data
public class MdfResourceBO {
    private String id;

    private String menuId;

    private String name;
    private String description;
    private String url;

    private static final BeanCopier toMdfResourceDMOCopier = BeanCopier.create(MdfResourceBO.class, MdfResourceDMO.class, false);

    public static MdfResourceDMO toMdfResourceDMO(MdfResourceBO dmo) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(dmo, MdfResourceDMO.class, toMdfResourceDMOCopier);
    }
}
