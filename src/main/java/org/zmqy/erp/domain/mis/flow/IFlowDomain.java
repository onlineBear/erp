package org.zmqy.erp.domain.mis.flow;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.zmqy.erp.model.mis.bo.sys.flow.FlowBo;
import org.zmqy.erp.model.mis.flow.Flow;
import org.zmqy.erp.model.mis.flow.FlowDtl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface IFlowDomain {
    String add(FlowBo bo, String operUserId, Date operTime)throws Exception;

    void mdfById(FlowBo bo, String operUserId, Date operTime) throws Exception;

    void delFlowById(List<String> flowIdList, String operUserId, Date operTime)throws Exception;
    void delFlowDtlById(String flowId, List<String> flowDtlIdList, String operUserId, Date operTime)throws Exception;

    void addBill(String billId, String menuNo, String operUserId, Date operTime) throws Exception;
    Boolean check(String billId, String menuNo, Integer nextLevel, String operUserId, Date operTime) throws Exception;
    void back(String billId, String menuNo, Integer currentLevel, String operUserId, Date operTime) throws Exception;

    // 专用查询
    // flow
    Flow getByMenuId(String menuId);

    Boolean canMdf(String billId, String menuNo);

    // 通用查询
    // flow
    Flow selById(String id);

    List<Flow> selBatchIds(Collection<? extends Serializable> idList);

    Integer selCount(QueryWrapper<Flow> queryWrapper);

    Flow selOne(QueryWrapper<Flow> queryWrapper);

    List<Flow> selList(QueryWrapper<Flow> queryWrapper);

    // flowDtl
    FlowDtl selFlowDtlById(String id);

    List<FlowDtl> selFlowDtlBatchIds(Collection<? extends Serializable> idList);

    Integer selFlowDtlCount(QueryWrapper<FlowDtl> queryWrapper);

    FlowDtl selFlowDtlOne(QueryWrapper<FlowDtl> queryWrapper);

    List<FlowDtl> selFlowDtlList(QueryWrapper<FlowDtl> queryWrapper);
}
