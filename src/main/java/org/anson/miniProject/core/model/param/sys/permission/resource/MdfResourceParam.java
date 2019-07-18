package org.anson.miniProject.core.model.param.sys.permission.resource;

import lombok.Data;
import org.anson.miniProject.core.model.bo.sys.permission.ResourceBO;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

@Data
public class MdfResourceParam {
    private String id;

    private String menuId;

    private String name;
    private String description;
    private String url;

    private static final BeanCopier toBOCopier = BeanCopier.create(MdfResourceParam.class, ResourceBO.class, false);

    public static ResourceBO toBO(MdfResourceParam param) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(param, ResourceBO.class, toBOCopier);
    }
}