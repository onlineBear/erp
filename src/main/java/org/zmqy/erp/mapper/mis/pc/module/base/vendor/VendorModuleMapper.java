package org.zmqy.erp.mapper.mis.pc.module.base.vendor;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface VendorModuleMapper {

    // 数据
    public List<Map> getVendorList(@Param("langId")String langId,
                                   @Param("startRow")Integer startRow, @Param("pageSize")Integer pageSize,
                                   @Param("vendorNo")String vendorNo,
                                   @Param("vendorName")String vendorName, @Param("filterCondition")String filterCondition);

    // 导出
    public List<Map> export(@Param("langId")String langId,
                                   @Param("startRow")Integer startRow, @Param("pageSize")Integer pageSize,
                                   @Param("vendorNo")String vendorNo,
                                   @Param("vendorName")String vendorName, @Param("filterCondition")String filterCondition);

    // 总记录数
    public Long getVendorListTotal(@Param("langId")String langId,
                                   @Param("vendorNo")String vendorNo,
                                   @Param("vendorName")String vendorName, @Param("filterCondition")String filterCondition);

    // 合计列
    public Map getVendorListSum(@Param("langId")String langId,
                                   @Param("startRow")Integer startRow, @Param("pageSize")Integer pageSize,
                                   @Param("vendorNo")String vendorNo,
                                   @Param("vendorName")String vendorName, @Param("filterCondition")String filterCondition);

    public Map getById(@Param("langId")String langId, @Param("vendorId")String vendorId);
    public List<Map> getCertifById(@Param("vendorId")String vendorId);
}
