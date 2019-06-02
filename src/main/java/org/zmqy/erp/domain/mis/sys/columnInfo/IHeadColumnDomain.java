package org.zmqy.erp.domain.mis.sys.columnInfo;

import org.zmqy.erp.domain.mis.IBaseDomain;
import org.zmqy.erp.model.mis.bo.sys.columnInfo.HeadColumnBo;
import org.zmqy.erp.model.mis.entity.sys.columnInfo.HeadColumn;
import org.zmqy.erp.model.mis.entity.sys.columnInfo.HeadColumnLang;

/**
 * @Description:
 * @Auther: lly
 * @Date: 2018-12-17 09:58
 */
public interface IHeadColumnDomain extends IBaseDomain<HeadColumnBo> {
    HeadColumn selectById(String id);

    void del(String menuId, String pageTypeNo);

    void update(String menuId, String pageTypeNo, String newMenuId, String newPageTypeNo);

    HeadColumnLang selectColumnName(String headColumnId, String langId);
}
