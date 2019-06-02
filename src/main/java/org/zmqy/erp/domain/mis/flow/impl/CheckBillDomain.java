package org.zmqy.erp.domain.mis.flow.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.zmqy.erp.domain.mis.flow.ICheckBillDomain;
import org.zmqy.erp.domain.mis.flow.IFlowDomain;
import org.zmqy.erp.domain.mis.flow.IFlowLogDomain;
import org.zmqy.erp.domain.mis.sys.base.IMenuDomain;
import org.zmqy.erp.framework.exception.model.unI18n.InternalException;
import org.zmqy.erp.mapper.mis.pc.module.sys.flow.CheckBillMapper;
import org.zmqy.erp.model.mis.entity.CheckBill;
import org.zmqy.erp.model.mis.entity.sys.base.Menu;
import org.zmqy.erp.model.mis.flow.Flow;
import org.zmqy.erp.model.mis.flow.FlowDtl;
import org.zmqy.erp.service.mis.pc.log.FlowLogService;
import org.zmqy.erp.tool.util.common.StringUtil;

import java.util.Date;

@Component
@Transactional(rollbackFor = Exception.class)
public class CheckBillDomain implements ICheckBillDomain {
    @Autowired
    private CheckBillMapper mapper;
    @Autowired
    private IFlowDomain flowDomain;
    @Autowired
    private IMenuDomain menuDomain;
    @Autowired
    private FlowLogService flowLogService;

    @Override
    public String addBill(String billId, String menuNo, String operUserId, Date operTime) throws Exception {
        if(StringUtil.isEmpty(billId)){
            throw new InterruptedException("请输入单号");
        }

        if(StringUtil.isEmpty(menuNo)){
            throw new InterruptedException("请输入菜单编码");
        }

        Menu menu = this.menuDomain.getByMenuNo(menuNo);

        if(menu == null){
            throw new InternalException("没有这个菜单编码");
        }

        Flow flow = this.flowDomain.getByMenuId(menu.getId());

        if(flow == null){
            throw new InternalException("没有这个流程");
        }

        CheckBill bill = new CheckBill();
        bill.setBillId(billId);
        bill.setFlowId(flow.getId());

        return this.add(bill, operUserId, operTime);
    }

    @Override
    public Boolean check(String billId, String menuNo, Integer nextLevel, String operUserId, Date operTime) throws Exception {
        if(StringUtil.isEmpty(billId)){
            throw new InternalException("请输入单据id");
        }

        if(StringUtil.isEmpty(menuNo)){
            throw new InternalException("请输入菜单编码");
        }

        if(nextLevel == null || nextLevel <= 0){
            throw new InternalException("下一层级应大于0");
        }

        Menu menu = this.menuDomain.getByMenuNo(menuNo);

        if(menu == null){
            throw new InternalException("没有这个菜单编码");
        }

        Flow flow = this.flowDomain.getByMenuId(menu.getId());

        if(flow == null){
            throw new InternalException("没有这个流程");
        }

        return this.checkBill(billId, flow.getId(), nextLevel, operUserId, operTime);
    }

    @Override
    public void back(String billId, String menuNo, Integer currentLevel, String operUserId, Date operTime) throws Exception {
        if(StringUtil.isEmpty(billId)){
            throw new InternalException("请输入单据id");
        }

        if(StringUtil.isEmpty(menuNo)){
            throw new InternalException("请输入菜单编码");
        }

        if(currentLevel == null || currentLevel <= 0){
            throw new InternalException("当前层级不正确");
        }

        Menu menu = this.menuDomain.getByMenuNo(menuNo);

        if(menu == null){
            throw new InternalException("没有这个菜单编码");
        }

        Flow flow = this.flowDomain.getByMenuId(menu.getId());

        if(flow == null){
            throw new InternalException("没有这个流程");
        }

        this.backBill(billId, flow.getId(), currentLevel, operUserId, operTime);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public CheckBill getByPPK(String billId, String flowId){
        QueryWrapper<CheckBill> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(CheckBill.BILLID, billId)
                    .eq(CheckBill.FLOWID, flowId);

        return this.mapper.selectOne(queryWrapper);
    }

    // checkBill
    public String add(CheckBill entity, String operUserId, Date operTime){
        entity.setCurentLevel(0);
        entity.setFlowDtlId("");
        entity.setCurentCheckUserId("");
        entity.setCurentCheckTime(null);
        entity.setCreateUserId(operUserId);
        entity.setCreateUserTime(operTime);
        entity.setLastUpdateTime(operTime);

        this.mapper.insert(entity);

        return entity.getId();
    }

    public Integer mdfByPPK(CheckBill entity, String operUserId, Date operTime){
        // 物理主键 billId, flowId
        if(StringUtil.isEmpty(entity.getBillId())){
            throw new InternalException("请输入单据id");
        }

        if(StringUtil.isEmpty(entity.getFlowId())){
            throw new InternalException("请输入流程id");
        }

        if(entity.getCurentLevel() != null && entity.getCurentLevel() < 0){
            throw new InternalException("当前层级应大于等于0");
        }

        if(entity.getFlowDtlId() != null && entity.getFlowDtlId().trim().equals("")){
            throw new InternalException("请输入流程层级id");
        }

        entity.setId(null);
        entity.setCreateUserId(null);
        entity.setCreateUserTime(null);
        entity.setLastUpdateTime(operTime);

        QueryWrapper<CheckBill> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(CheckBill.BILLID, entity.getBillId())
                    .eq(CheckBill.FLOWID, entity.getFlowId());

        return this.mapper.update(entity, queryWrapper);
    }

    public Boolean checkBill(String billId, String flowId, Integer nextLevel, String operUserId, Date operTime)throws Exception{
        Integer currentLevel = nextLevel - 1;

        QueryWrapper<CheckBill> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(CheckBill.BILLID, billId)
                    .eq(CheckBill.FLOWID, flowId);

        //查找审核表单据信息
        CheckBill entity = this.mapper.selectOne(queryWrapper);

        if(entity == null){
            throw new InternalException("checkBill 没有这个单据");
        }else{
            if(entity.getCurentLevel() > currentLevel){
                throw new RuntimeException("已审核");
            }else if(entity.getCurentLevel() < currentLevel){
                throw new RuntimeException("已回退");
            }
        }

        //根据查询审核表中查询出来的flowID和层级去flowDtl查找有没有这个层级
        FlowDtl flowDtl = this.flowDomain.selFlowDtlOne(new QueryWrapper<FlowDtl>()
                                                    .eq(FlowDtl.SEQ, nextLevel)
                                                    .eq(FlowDtl.FLOWID, entity.getFlowId()));

        if(flowDtl == null){
            throw new InternalException("没有下一级流程了");
        }


        entity.setFlowDtlId(flowDtl.getId());
        entity.setCurentLevel(nextLevel);
        entity.setCurentCheckUserId(operUserId);
        entity.setCreateUserTime(operTime);

        entity.setId(null);
        entity.setCreateUserId(null);
        entity.setCreateUserTime(null);
        entity.setLastUpdateTime(operTime);

        //修改审核表，修改成功返回影响的条数
        //可能存在并发问题，或者A用户审核完B用户之前页面没退出也可以点到审核按钮发送审核请求，所以这里利用数据自带的锁进行处理
        int rowCount = this.mapper.update(entity, new UpdateWrapper<CheckBill>()
                                                    .eq(CheckBill.FLOWID, entity.getFlowId())
                                                    .eq(CheckBill.BILLID, entity.getBillId())
                                                    .eq(CheckBill.CURENTLEVEL, currentLevel));

        //判断有没有修改到，如果修改了这里应该是返回的影响行数，如果为零说明没有任何改变
        if(rowCount <= 0){
            throw new InternalException("可能已审核");
        }

        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(CheckBill.BILLID, billId)
                    .eq(CheckBill.FLOWID, flowId)
                    .gt(CheckBill.CURENTLEVEL, nextLevel);

        boolean isLastLevelCheck = false;

        if(this.mapper.selectCount(queryWrapper) <= 0){
            isLastLevelCheck = true;
        }else {
            isLastLevelCheck = false;
        }

        // 记录日志
        this.flowLogService.log(operUserId, operTime, entity.getFlowId(), entity.getBillId(), entity.getCurentLevel(), "check");

        return isLastLevelCheck;
    }

    public void backBill(String billId, String flowId, Integer currentLevel, String operUserId, Date operTime) throws Exception{
        Integer preLevel = currentLevel - 1;

        if(preLevel < 0){
            throw new InternalException("回退的层级参数不正确");
        }

        QueryWrapper<CheckBill> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(CheckBill.BILLID, billId)
                    .eq(CheckBill.FLOWID, flowId);

        //查找审核表单据信息
        CheckBill entity = this.mapper.selectOne(queryWrapper);

        if(entity == null){
            throw new InternalException("checkBill 没有这个单据");
        }else{
            if(entity.getCurentLevel() < currentLevel){
                throw new RuntimeException("已回退");
            }else if(entity.getCurentLevel() > currentLevel){
                throw new RuntimeException("已审核");
            }
        }

        // 若是最后一级, 则不允许回退
        QueryWrapper<FlowDtl> flowDtlQueryWrapper = new QueryWrapper<>();
        flowDtlQueryWrapper.eq(FlowDtl.FLOWID, flowId)
                           .gt(FlowDtl.SEQ, currentLevel);

        if(this.flowDomain.selFlowDtlCount(flowDtlQueryWrapper) <= 0){
            throw new RuntimeException("已终审, 不允许回退");
        }

        //根据查询审核表中查询出来的flowID和层级去flowDtl查找有没有这个层级
        if(preLevel > 0){
            FlowDtl flowDtl = this.flowDomain.selFlowDtlOne(new QueryWrapper<FlowDtl>()
                    .eq(FlowDtl.SEQ, preLevel)
                    .eq(FlowDtl.FLOWID, entity.getFlowId()));

            if(flowDtl == null){
                throw new InternalException("没有上一级流程");
            }

            entity.setFlowDtlId(flowDtl.getId());
        }else {
            entity.setFlowDtlId("");
        }

        entity.setId(null);
        entity.setCurentLevel(preLevel);
        entity.setCurentCheckUserId(operUserId);
        entity.setCurentCheckTime(operTime);
        entity.setCreateUserId(null);
        entity.setCreateUserTime(null);
        entity.setLastUpdateTime(operTime);

        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(CheckBill.FLOWID, flowId)
                    .eq(CheckBill.BILLID, billId)
                    .eq(CheckBill.CURENTLEVEL, currentLevel);

        Integer rowCount = this.mapper.update(entity, queryWrapper);

        if(rowCount <= 0){
            throw new InternalException("可能已回退");
        }
    }
}
