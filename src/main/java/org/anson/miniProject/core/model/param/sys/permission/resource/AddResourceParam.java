package org.anson.miniProject.core.model.param.sys.permission.resource;

import lombok.Data;
import org.anson.miniProject.core.model.bo.sys.permission.resource.ResourceBO;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

@Data
public class AddResourceParam {
    private String menuId;

    private String name;
    private String description;
    private String url;

    private static final BeanCopier toBOCopier = BeanCopier.create(AddResourceParam.class, ResourceBO.class, false);

    public static ResourceBO toBO(AddResourceParam param) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(param, ResourceBO.class, toBOCopier);
    }
}
