package org.anson.miniProject.core.model.bo.sys.permission.resource;

import lombok.Data;
import org.anson.miniProject.core.model.param.sys.permission.resource.MdfResourceParam;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

@Data
public class MdfResourceBO {
    private String id;

    private String menuId;

    private String name;
    private String description;
    private String url;

    private static final BeanCopier toMdfResourceDMOCopier = BeanCopier.create(MdfResourceBO.class, MdfResourceParam.class, false);

    public static MdfResourceParam toMdfResourceDMO(MdfResourceBO dmo) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(dmo, MdfResourceParam.class, toMdfResourceDMOCopier);
    }
}
