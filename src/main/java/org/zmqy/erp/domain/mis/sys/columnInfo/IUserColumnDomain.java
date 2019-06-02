package org.zmqy.erp.domain.mis.sys.columnInfo;

import org.zmqy.erp.constract.mis.enums.PageTypeNoEnum;
import org.zmqy.erp.domain.mis.IBaseDomain;
import org.zmqy.erp.model.mis.entity.sys.columnInfo.UserColumn;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Auther: lly
 * @Date: 2018-12-19 18:12
 */
public interface IUserColumnDomain extends IBaseDomain<UserColumn> {

    List<UserColumn> selectByMap(Map<String, Object> params);

    void del(String columnId);

    List<Map<String, String>> getUserRequiredColumn(String langId, String userId, String menuNo, PageTypeNoEnum pageTypeNo);

    void resetById(String id) throws Exception;

}
