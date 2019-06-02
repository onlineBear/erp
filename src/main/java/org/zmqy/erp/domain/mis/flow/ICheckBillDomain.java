package org.zmqy.erp.domain.mis.flow;

import org.zmqy.erp.model.mis.entity.CheckBill;

import java.util.Date;

public interface ICheckBillDomain {
    String addBill(String billId, String menuNo, String operUserId, Date operTime) throws Exception;
    Boolean check(String billId, String menuNo, Integer nextLevel, String operUserId, Date operTime) throws Exception;
    void back(String billId, String menuNo, Integer currentLevel, String operUserId, Date operTime) throws Exception;

    // 专用查询
    CheckBill getByPPK(String billId, String flowId);

    // 通用查询
}
