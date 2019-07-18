package org.anson.miniProject.core.model.bo.sys.base;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.anson.miniProject.core.model.bo.BaseBO;
import org.anson.miniProject.core.model.po.sys.base.Menu;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

@Data
public class MenuBO extends BaseBO {
    private String no;
    private String icon;
    private String name;
    private String description;
    private String clientDictId;
    private String path;
    private Boolean areDisplay;

    private MenuBO parentMenuBO;

    private static final BeanCopier toDictCopier = BeanCopier.create(MenuBO.class, Menu.class, false);

    public Menu toMenu() throws InstantiationException, IllegalAccessException {
        Menu po = BeanHelper.beanToBean(this, Menu.class, toDictCopier);

        if (this.parentMenuBO != null && StrUtil.isNotEmpty(this.parentMenuBO.getId())){
            po.setParentMenuId(this.parentMenuBO.getId());
        }

        return po;
    }

}
