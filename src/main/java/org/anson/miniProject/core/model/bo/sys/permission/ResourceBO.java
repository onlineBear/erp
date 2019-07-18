package org.anson.miniProject.core.model.bo.sys.permission;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.anson.miniProject.core.model.bo.BaseBO;
import org.anson.miniProject.core.model.bo.sys.base.MenuBO;
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

    public Resource toResource() throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(this, Resource.class, toResourceCopier);
    }

    @Tolerate
    public ResourceBO (){}
}
