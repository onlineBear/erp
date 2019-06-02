package org.zmqy.erp.model.mis.bo.sys.base;

import lombok.Data;
import org.springframework.cglib.beans.BeanCopier;
import org.zmqy.erp.model.mis.bo.sys.security.ResourceBo;
import org.zmqy.erp.model.mis.entity.sys.base.Menu;

import java.util.Date;
import java.util.List;

@Data
public class MenuBo {
    private String id;

    private String menuNo;

    private String parentMenuId;

    private String iconUrl;

    private String menuClientNo;

    private Boolean areCatalog;

    private Integer menuLevel;

    private Integer seq;

    private Boolean areHqDisplay;

    private Boolean areShopDisplay;

    private Boolean areDcDisplay;

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;

    private String reportId;

    private List<MenuLangBo> menuLangBoList;
    private List<ResourceBo> resourceBoList;

    private static final BeanCopier bo2entityCopier = BeanCopier.create(MenuBo.class, Menu.class, false);

    public static Menu bo2entity(MenuBo bo){
        if (bo == null){
            return null;
        }

        Menu entity = new Menu();

        bo2entityCopier.copy(bo, entity, null);

        return entity;
    }
}
