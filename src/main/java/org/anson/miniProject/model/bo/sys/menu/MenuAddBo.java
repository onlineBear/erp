package org.anson.miniProject.model.bo.sys.menu;

import lombok.Data;
import org.anson.miniProject.model.entity.sys.Menu;
import org.springframework.cglib.beans.BeanCopier;

@Data
public class MenuAddBo {
    private String no;
    private String icon;
    private String name;
    private String description;
    private String parentMenuId;
    private Boolean areDisplay;

    private static final BeanCopier bo2entityCopier = BeanCopier.create(MenuAddBo.class, Menu.class, false);

    public static Menu bo2entity(MenuAddBo bo){
        if(bo == null){
            return null;
        }

        Menu entity = new Menu();

        bo2entityCopier.copy(bo, entity, null);

        return entity;
    }
}
