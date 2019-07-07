package org.anson.miniProject.core.domain.sys;

import org.anson.miniProject.core.model.dmo.sys.MenuDmo;

import java.util.Date;

public interface IMenuDomain {
    String addMenu(MenuDmo bo, String operUserId, Date operTime);
    void mdfMenu(MenuDmo bo, String operUserId, Date operTime);
    void delMenu(String menuId);
    void delMenu(String[] menuIdArray);
}
