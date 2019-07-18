package org.anson.miniProject.core.domain.sys.base;

import org.anson.miniProject.core.model.param.sys.base.menu.AddMenuParam;
import org.anson.miniProject.core.model.param.sys.base.menu.MdfMenuParam;

import java.util.Date;

public interface IMenuDomain {
    String addMenu(AddMenuParam bo, String operUserId, Date operTime) throws Exception;
    void mdfMenu(MdfMenuParam bo, String operUserId, Date operTime) throws Exception;
    void delMenu(String menuId, String operUserId, Date operTime) throws Exception;
}
