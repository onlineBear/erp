package org.anson.miniProject.domain.sys;

import org.anson.miniProject.model.bo.sys.menu.MenuAddBo;
import org.anson.miniProject.model.bo.sys.menu.MenuMdfBo;
import org.anson.miniProject.model.entity.sys.Menu;

import java.io.Serializable;
import java.util.Date;

public interface IMenuDomain {
    String addMenu(MenuAddBo bo, String operUserId, Date operTime);
    void mdfMenu(MenuMdfBo bo, String operUserId, Date operTime);
    void delMenu(String menuId, String operUserId, Date operTime);
    void delMenu(String[] menuIdArray, String operUserId, Date operTime);

    Menu getById(Serializable id);
}
