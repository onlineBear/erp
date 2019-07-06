package org.anson.miniProject.core.domain.sys;

import org.anson.miniProject.core.model.dmo.sys.MenuBo;

import java.util.Date;

public interface IMenuDomain {
    String addMenu(MenuBo bo, String operUserId, Date operTime);
    void mdfMenu(MenuBo bo, String operUserId, Date operTime);
    void delMenu(String menuId);
    void delMenu(String[] menuIdArray);
}
