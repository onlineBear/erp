package org.anson.miniProject.core.domain.sys;

import org.anson.miniProject.core.model.param.sys.MenuDmo;

import java.util.Date;

public interface IMenuDomain {
    String addMenu(MenuDmo bo, String operUserId, Date operTime) throws Exception;
    void mdfMenu(MenuDmo bo, String operUserId, Date operTime) throws Exception;
    void delMenu(String menuId, String operUserId, Date operTime) throws Exception;
}
