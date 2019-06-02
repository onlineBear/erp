package org.zmqy.erp.model.mis.bo.sys.base;

import lombok.Data;
import org.springframework.cglib.beans.BeanCopier;
import org.zmqy.erp.model.mis.entity.sys.base.MenuLang;

import java.util.Date;

@Data
public class MenuLangBo {
    private String id;

    private String menuId;

    private String langId;

    private String menuName;

    private String menuDesc;

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;

    private static final BeanCopier bo2entityCopier = BeanCopier.create(MenuLangBo.class, MenuLang.class, false);

    public static MenuLang bo2entity(MenuLangBo bo){
        if (bo == null){
            return null;
        }

        MenuLang entity = new MenuLang();

        bo2entityCopier.copy(bo, entity, null);

        return entity;
    }
}
