package org.anson.miniProject.core.model.service.menu;

import lombok.Data;
import org.anson.miniProject.core.model.param.sys.base.menu.MdfMenuParam;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

import javax.validation.constraints.NotEmpty;

@Data
public class MenuMdfDTO {
    @NotEmpty(message = "请输入菜单id")
    private String id;
    private String no;
    private String icon;
    private String name;
    private String description;
    private String parentMenuId;
    private Boolean areDisplay;

    private static final BeanCopier toBOCopier = BeanCopier.create(MenuMdfDTO.class, MdfMenuParam.class, false);

    public MdfMenuParam toBO() throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(this, MdfMenuParam.class, toBOCopier);
    }
}
