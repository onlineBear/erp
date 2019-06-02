package org.zmqy.erp.domain.mis.sys.base;

import org.zmqy.erp.domain.mis.IBaseDomain;
import org.zmqy.erp.model.mis.bo.sys.base.MenuBo;
import org.zmqy.erp.model.mis.entity.sys.base.Menu;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IMenuDomain extends IBaseDomain<MenuBo> {

    // 查询
    public Boolean existsMenu(String menuId);
    public void mdfMenuSeq(String menuId, String parentMenuId, String frontMenuId, String behindMenuId,
                           String operUserId, Date operTime) throws Exception;
    public List<Menu> selectByMap(Map<String,Object> paramsMap);

    public Menu getById(String menuId);
    public Menu getByMenuNo(String menuNo);
}
