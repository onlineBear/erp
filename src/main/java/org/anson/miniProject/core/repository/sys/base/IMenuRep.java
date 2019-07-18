package org.anson.miniProject.core.repository.sys.base;

import org.anson.miniProject.core.mapper.sys.MenuMapper;
import org.anson.miniProject.core.model.po.sys.base.Menu;
import org.anson.miniProject.core.repository.IBaseRep;

import java.util.Date;

public interface IMenuRep extends IBaseRep<Menu, MenuMapper> {
    String insert(Menu po, String operUserId, Date operTime) throws Exception;
    String update(Menu po, String operUserId, Date operTime) throws Exception;
    void del(String menuId, String operUserId, Date operTime) throws Exception;
}
