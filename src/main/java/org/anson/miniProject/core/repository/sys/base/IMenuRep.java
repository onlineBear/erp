package org.anson.miniProject.core.repository.sys.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.anson.miniProject.core.mapper.sys.MenuMapper;
import org.anson.miniProject.core.model.param.sys.MenuDmo;
import org.anson.miniProject.core.model.po.sys.Menu;
import org.anson.miniProject.core.repository.IBaseRep;

import java.util.Date;

public interface IMenuRep extends IBaseRep<Menu, MenuMapper> {
    String addMenu(MenuDmo bo, String operUserId, Date operTime);
    String mdfMenu(MenuDmo bo, String operUserId, Date operTime);
    String saveMenu(MenuDmo bo, String operUserId, Date operTime);
    void delMenu(String menuId, String operUserId, Date operTime) throws JsonProcessingException;

    MenuDmo getMenu(String menuId);
}
