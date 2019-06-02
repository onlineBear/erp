package org.zmqy.erp.service.mis.biz.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zmqy.erp.constract.mis.enums.OperationLogEmum;
import org.zmqy.erp.domain.mis.base.vendor.IVendorDomain;
import org.zmqy.erp.domain.mis.flow.IFlowDomain;
import org.zmqy.erp.model.mis.bo.base.vendor.VendorBo;
import org.zmqy.erp.service.mis.pc.log.OperationLogService;
import org.zmqy.erp.tool.util.common.dataType.ListUtil;

import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class VendorService {
    @Autowired
    private IVendorDomain domain;
    @Autowired
    private IFlowDomain flowDomain;
    @Autowired
    private OperationLogService logService;

    public String add(VendorBo bo, String menuNo, String operUserId, Date operTime) throws Exception{
        // 新增供应商
        String vendorId = this.domain.add(bo, operUserId, operTime);
        // 新增单据
        this.flowDomain.addBill(vendorId, menuNo, operUserId, operTime);
        // 操作日志
        this.logService.logging(vendorId, operTime, operUserId, OperationLogEmum.OPERTYPE_ADD, menuNo);

        return vendorId;
    }

    public String mdfById(VendorBo bo, List<String> vendorCertifIdList, String menuNo, String operUserId, Date operTime) throws Exception{
        // 检查是否可以修改(已审核的不能修改)
        if(!this.flowDomain.canMdf(bo.getId(), menuNo)){
            throw new RuntimeException("已审核, 不可修改");
        }

        // 删除证书
        if(ListUtil.isNotEmpty(vendorCertifIdList)){
            this.domain.delVendorCertif(bo.getId(), vendorCertifIdList, operUserId, operTime);
        }

        // 修改
        this.domain.mdfById(bo, operUserId, operTime);

        // 操作日志
        this.logService.logging(bo.getId(), operTime, operUserId, OperationLogEmum.OPERTYPE_MDF, menuNo);

        return bo.getId();
    }

    // 审核
    public void check(String vendorId, String menuNo, Integer nextLevel, String operUserId, Date operTime) throws Exception{
        this.flowDomain.check(vendorId, menuNo, nextLevel, operUserId, operTime);
    }

    // 回退
    public void back(String vendorId, String menuNo, Integer currentLevel, String operUserId, Date operTime) throws Exception{
        this.flowDomain.back(vendorId, menuNo, currentLevel, operUserId, operTime);
    }
}
