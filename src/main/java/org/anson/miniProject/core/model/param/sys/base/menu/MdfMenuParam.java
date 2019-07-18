package org.anson.miniProject.core.model.param.sys.base.menu;

import lombok.Data;
import org.anson.miniProject.core.model.bo.sys.base.MenuBO;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

@Data
public class MdfMenuParam {
    private String no;
    private String icon;
    private String name;
    private String description;
    private String clientDictId;
    private String path;
    private Boolean areDisplay;

    private static final BeanCopier toBOCopier = BeanCopier.create(MdfMenuParam.class, MenuBO.class, false);

    public MenuBO toBO() throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(this, MenuBO.class, toBOCopier);
    }
}
