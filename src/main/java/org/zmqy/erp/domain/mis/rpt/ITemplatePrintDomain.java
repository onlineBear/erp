package org.zmqy.erp.domain.mis.rpt;

import org.zmqy.erp.model.mis.entity.rpt.TemplatePrint;

import java.util.Date;

/**
 * @Description:
 * @Author: lly
 * @Date: 2019-02-19 14:35
 */
public interface ITemplatePrintDomain {
    public void updateById(TemplatePrint entity, Date operTime);

    public void add(TemplatePrint entity,String operUserId,Date operTime);

    public void del(String id);

    public TemplatePrint selectById(String id);
}
