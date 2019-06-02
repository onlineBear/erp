package org.zmqy.erp.domain.mis.sys.columnInfo;

import org.zmqy.erp.domain.mis.IBaseDomain;
import org.zmqy.erp.model.mis.bo.sys.columnInfo.DtlColumnBo;
import org.zmqy.erp.model.mis.entity.sys.columnInfo.DtlColumn;

/**
 * @Description:
 * @Auther: lly
 * @Date: 2018-12-17 15:15
 */
public interface IDtlColumnDomain extends IBaseDomain<DtlColumnBo> {
    void delById(String id) throws Exception;

    DtlColumn selectById(String id);

    void del(String menuId, String pageTypeNo);

    void update(String menuId, String pageTypeNo, String newMenuId, String newPageTypeNo);
}
