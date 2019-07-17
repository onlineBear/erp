package org.anson.miniProject.core.model.bo.sys.permission.resource;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.anson.miniProject.core.model.bo.BaseBO;
import org.anson.miniProject.core.model.bo.sys.MenuBO;
import org.anson.miniProject.core.model.po.sys.permission.Resource;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

@Data
@Builder
public class ResourceBO extends BaseBO {
    private MenuBO menuBO;

    private String name;
    private String description;
    private String url;

    private static final BeanCopier toResourceCopier = BeanCopier.create(ResourceBO.class, Resource.class, false);

    public static Resource toResource(ResourceBO bo) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(bo, Resource.class, toResourceCopier);
    }

    @Tolerate
    public ResourceBO (){}
}
