package org.anson.miniProject.domain.sys;

import org.anson.miniProject.model.bo.sys.MenuBo;

import java.util.Date;

public interface IMenuDomain {
    String addMenu(MenuBo bo, String operUserId, Date operTime);
    void mdfMenu(MenuBo bo, String operUserId, Date operTime);
    void delMenu(String menuId);
    void delMenu(String[] menuIdArray);
}
