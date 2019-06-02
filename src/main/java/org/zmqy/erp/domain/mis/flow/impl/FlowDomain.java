package org.zmqy.erp.domain.mis.flow.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.zmqy.erp.domain.mis.flow.ICheckBillDomain;
import org.zmqy.erp.domain.mis.flow.IFlowDomain;
import org.zmqy.erp.domain.mis.sys.security.IResourceDomain;
import org.zmqy.erp.framework.exception.model.unI18n.InternalException;
import org.zmqy.erp.mapper.mis.biz.sys.base.MenuMapper;
import org.zmqy.erp.mapper.mis.pc.module.sys.flow.*;
import org.zmqy.erp.model.mis.bo.sys.flow.FlowBo;
import org.zmqy.erp.model.mis.bo.sys.flow.FlowDtlBo;
import org.zmqy.erp.model.mis.bo.sys.flow.FlowDtlLangBo;
import org.zmqy.erp.model.mis.bo.sys.flow.FlowLangBo;
import org.zmqy.erp.model.mis.bo.sys.security.ResourceBo;
import org.zmqy.erp.model.mis.bo.sys.security.ResourceLangBo;
import org.zmqy.erp.model.mis.entity.CheckBill;
import org.zmqy.erp.model.mis.entity.sys.base.Menu;
import org.zmqy.erp.model.mis.flow.*;
import org.zmqy.erp.tool.util.common.StringUtil;
import org.zmqy.erp.tool.util.common.dataType.ListUtil;

import java.io.Serializable;
import java.util.*;

@Component
@Transactional(rollbackFor = Exception.class)
public class FlowDomain implements IFlowDomain {
    @Autowired
    private FlowMapper flowMapper;

    @Autowired
    private FlowLangMapper flowLangMapper;

    @Autowired
    private FlowDtlMapper flowDtlMapper;

    @Autowired
    private FlowDtlLangMapper flowDtlLangMapper;

    @Autowired
    private FlowLogMapper flowLogMpapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private IResourceDomain resourceDomain;
    @Autowired
    private ICheckBillDomain billDomain;

    private static final String CK = "ck";
    private static final String UNCK = "unCk";
    private static final String RESOPER = "resOper-";

    @Override
    public String add(FlowBo bo, String operUserId, Date operTime)throws Exception{
        if(bo == null){
            throw new InternalException("请输入流程");
        }

        Flow flow = FlowBo.bo2entity(bo);

        // 流程主表
        String id = this.add(flow, operUserId, operTime);

        if(ListUtil.isEmpty(bo.getFlowLangBoList())){
            throw new InternalException("请输入流程名称");
        }

        // flowLang
        for(FlowLangBo flowLangBo : bo.getFlowLangBoList()){
            FlowLang flowLang = FlowLangBo.bo2entity(flowLangBo);
            flowLang.setFlowId(id);

            this.add(flowLang, operUserId, operTime);
        }

        // 层级
        if(ListUtil.isNotEmpty(bo.getFlowDtlBoList())){

            if(flow.getMinLevel() > bo.getFlowDtlBoList().size()){
                throw new Exception("层级数不能小于最小层级");
            }

            for(FlowDtlBo flowDtlBo : bo.getFlowDtlBoList()){
                flowDtlBo.setFlowId(flow.getId());
                this.add(flowDtlBo, operUserId, operTime);
            }
        }

        // 设置最后一层级
        this.setLastLevel(flow.getId(), operUserId, operTime);

        return id;
    }

    @Override
    public void mdfById(FlowBo bo, String operUserId, Date operTime) throws Exception {

        if(bo == null){
            throw new InternalException("请输入流程");
        }

        Flow flow = FlowBo.bo2entity(bo);

        this.mdfById(flow, operUserId, operTime);

        // flowLang
        if(ListUtil.isNotEmpty(bo.getFlowLangBoList())){
            for(FlowLangBo flowLangBo: bo.getFlowLangBoList()){
                FlowLang flowLang = FlowLangBo.bo2entity(flowLangBo);
                flowLang.setFlowId(flow.getId());
                this.saveByPPK(flowLang, operUserId, operTime);
            }
        }

        // 层级
        if(ListUtil.isNotEmpty(bo.getFlowDtlBoList())){
            for(FlowDtlBo flowDtlBo : bo.getFlowDtlBoList()){
                flowDtlBo.setFlowId(flow.getId());
                this.saveById(flowDtlBo, operUserId, operTime);
            }
        }

        // 检查最小层级
        flow = this.flowMapper.selectById(flow.getId());

        Integer minLevel = flow.getMinLevel();

        QueryWrapper<FlowDtl> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(FlowDtl.FLOWID, flow.getId());

        Integer realLevel = this.flowDtlMapper.selectCount(queryWrapper);

        if(realLevel < minLevel){
            throw new RuntimeException("层级小于最小层级");
        }

        // 设置最后一层级
        this.setLastLevel(flow.getId(), operUserId, operTime);
    }

    @Override
    public void delFlowById(List<String> flowIdList, String operUserId, Date operTime)throws Exception {
        if(ListUtil.isNotEmpty(flowIdList)){
            for(String flowId : flowIdList){
                //直接删除flow
                flowMapper.deleteById(flowId);
                Map<String, Object> flowIdMap = new HashMap<>();
                flowIdMap.put("flowId", flowId);
                //删除flowLang
                flowLangMapper.deleteByMap(flowIdMap);
                QueryWrapper<FlowDtl> flowDtlQueryWrapper = new QueryWrapper<>();
                //查出FlowDtl表id
                List<FlowDtl> flowDtlList = flowDtlMapper.selectList(flowDtlQueryWrapper.eq(FlowDtl.FLOWID,flowId));
                List<String> list = new ArrayList<>();
                for(FlowDtl flowDtl : flowDtlList){
                    list.add(flowDtl.getId());
                }
                //删除flowDtl表
                this.delFlowDtlById(list, operUserId, operTime);
            }
        }
    }

    @Override
    public void delFlowDtlById(String flowId, List<String> flowDtlIdList, String operUserId, Date operTime)throws Exception{
        if(flowDtlIdList !=null){
            for(String flowDtlId : flowDtlIdList){
                QueryWrapper<FlowDtlLang> flowDtlLangQueryWrapper = new QueryWrapper<>();
                flowDtlLangQueryWrapper.eq(FlowDtlLang.FLOWDTLID,flowDtlId);
                //查询层级
                int seq = this.flowDtlMapper.selectById(flowDtlId).getSeq();
                //拼接url
                String url = "/pc/module/pc010207/"+CK+seq;
                //删除资源
                this.resourceDomain.delByUrl(url);
                flowDtlMapper.deleteById(flowDtlId);
                flowDtlLangMapper.delete(flowDtlLangQueryWrapper);
            }
        }
    }

    @Override
    public void addBill(String billId, String menuNo, String operUserId, Date operTime) throws Exception{
        this.billDomain.addBill(billId, menuNo, operUserId, operTime);
    }

    @Override
    public Boolean check(String billId, String menuNo, Integer nextLevel, String operUserId, Date operTime) throws Exception{
        return this.billDomain.check(billId, menuNo, nextLevel, operUserId, operTime);
    }

    @Override
    public void back(String billId, String menuNo, Integer currentLevel, String operUserId, Date operTime) throws Exception{
        this.billDomain.back(billId, menuNo, currentLevel, operUserId, operTime);
    }

    // 专用查询方法
    // flow
    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Flow getByMenuId(String menuId){
        QueryWrapper<Flow> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Flow.MENUID, menuId);
        return this.flowMapper.selectOne(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Boolean canMdf(String billId, String menuNo){
        if(StringUtil.isEmpty(billId)){
            throw new InternalException("请输入单据id");
        }

        if(StringUtil.isEmpty(menuNo)){
            throw new InternalException("请输入菜单编码");
        }

        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Menu.MENUNO, menuNo);
        Menu menu = this.menuMapper.selectOne(queryWrapper);

        if(menu == null){
            throw new InternalException("没有这个菜单");
        }

        Flow flow = this.getByMenuId(menu.getId());

        // 没有这个流程, 可以修改
        if(flow == null){
            return true;
        }

        CheckBill bill = this.billDomain.getByPPK(billId, flow.getId());

        // 没有插入 checkBill, 可以修改
        if(bill == null || bill.getCurentLevel() <= 0){
            return true;
        }else {
            return false;
        }
    }

    // 通用查询方法
    // flow
    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Flow selById(String id){
        return this.flowMapper.selectById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<Flow> selBatchIds(Collection<? extends Serializable> idList){
        return this.flowMapper.selectBatchIds(idList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Integer selCount(QueryWrapper<Flow> queryWrapper){
        return this.flowMapper.selectCount(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Flow selOne(QueryWrapper<Flow> queryWrapper){
        return this.flowMapper.selectOne(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<Flow> selList(QueryWrapper<Flow> queryWrapper){
        return this.flowMapper.selectList(queryWrapper);
    }

    // flowDtl
    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public FlowDtl selFlowDtlById(String id){
        return this.flowDtlMapper.selectById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<FlowDtl> selFlowDtlBatchIds(Collection<? extends Serializable> idList){
        return this.flowDtlMapper.selectBatchIds(idList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Integer selFlowDtlCount(QueryWrapper<FlowDtl> queryWrapper){
        return this.flowDtlMapper.selectCount(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public FlowDtl selFlowDtlOne(QueryWrapper<FlowDtl> queryWrapper){
        return this.flowDtlMapper.selectOne(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<FlowDtl> selFlowDtlList(QueryWrapper<FlowDtl> queryWrapper){
        return this.flowDtlMapper.selectList(queryWrapper);
    }

    // 以下方法不公开
    // flow
    public String add(Flow entity, String operUserId, Date operTime)throws Exception{
        // 父级
        if(StringUtil.isEmpty(entity.getUpFlowId())){
            entity.setUpFlowId("");
        }else {
            if(flowMapper.selectById(entity.getUpFlowId()) == null){
                throw new RuntimeException("父流程不存在");
            }
        }

        if(StringUtil.isEmpty(entity.getMenuId())){
            throw new RuntimeException("菜单模块不能为空");
        }

        if(menuMapper.selectById(entity.getMenuId()) == null){
            throw new RuntimeException("菜单模块不存在");
        }

        if(entity.getMinLevel() == null || entity.getMinLevel() < 0){
            entity.setMinLevel(0);
        }

        entity.setCreateUserId(operUserId);
        entity.setCreateUserTime(operTime);
        entity.setLastUpdateTime(operTime);

        this.flowMapper.insert(entity);

        return entity.getId();
    }

    public void mdfById(Flow entity, String operUserId, Date operTime) throws Exception {
        if(StringUtil.isEmpty(entity.getId())){
            throw new InternalException("请输入流程id");
        }

        // 检查父级id
        if(StringUtil.isNotEmpty(entity.getUpFlowId())){
            if(flowMapper.selectById(entity.getUpFlowId().trim()) == null){
                throw new InternalException("父流程不存在");
            }
        }

        // 检查菜单
        if(entity.getMenuId() != null){
            if(menuMapper.selectById(entity.getMenuId().trim()) == null){
                throw new InternalException("没有这个菜单");
            }
        }

        if(entity.getMinLevel() != null && entity.getMinLevel() < 0){
            entity.setMinLevel(0);
        }

        entity.setCreateUserId(null);
        entity.setCreateUserTime(null);
        entity.setLastUpdateTime(operTime);

        this.flowMapper.updateById(entity);
    }

    // flowLang
    public String add(FlowLang entity, String operUserId, Date operTime)throws Exception{
        if(StringUtil.isEmpty(entity.getFlowId())){
            throw new InternalException("请输入流程id");
        }

        if(StringUtil.isEmpty(entity.getFlowName())){
            throw new InternalException("请输入流程名称");
        }

        if(StringUtil.isEmpty(entity.getLangId())){
            throw new InternalException("请输入语言id");
        }

        entity.setCreateUserId(operUserId);
        entity.setCreateUserTime(operTime);
        entity.setLastUpdateTime(operTime);

        this.flowLangMapper.insert(entity);

        return entity.getId();
    }

    public void mdfByPPK(FlowLang entity, String operUserId, Date operTime){
        if(StringUtil.isEmpty(entity.getFlowId())){
            throw new RuntimeException("请输入流程id");
        }

        if(StringUtil.isEmpty(entity.getLangId())){
            throw new RuntimeException("请输入语言id");
        }

        if(entity.getFlowName() != null && entity.getFlowName().trim().equals("")){
            throw new InternalException("请输入流程名称");
        }

        QueryWrapper<FlowLang> flowLangQueryWrapper = new QueryWrapper<>();
        flowLangQueryWrapper.eq(FlowLang.FLOWID, entity.getFlowId())
                .eq(FlowLang.LANGID, entity.getLangId());

        entity.setCreateUserId(null);
        entity.setCreateUserTime(null);
        entity.setLastUpdateTime(operTime);

        this.flowLangMapper.update(entity, flowLangQueryWrapper);
    }

    public String saveByPPK(FlowLang entity, String operUserId, Date operTime) throws Exception {
        if(entity == null){
            throw new InternalException("请输入流程层级");
        }

        QueryWrapper<FlowLang> flowLangQueryWrapper = new QueryWrapper<>();
        flowLangQueryWrapper.eq(FlowLang.FLOWID, entity.getFlowId())
                            .eq(FlowLang.LANGID, entity.getLangId());

        if(this.flowLangMapper.selectCount(flowLangQueryWrapper) == 0){
            return this.add(entity, operUserId, operTime);
        } else {
            this.mdfByPPK(entity, operUserId, operTime);
            return entity.getId();
        }
    }

    public void delFlowLangByFK(String flowId) throws Exception{
        QueryWrapper<FlowLang> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(FlowLang.FLOWID, flowId);

        this.flowLangMapper.delete(queryWrapper);

        // flowDtl
        this.delFlowDtlByFK(flowId);
    }

    public void batchDelFlowLangByFK(List<String> flowIdList){
        QueryWrapper<FlowLang> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(FlowLang.FLOWID, flowIdList);

        this.flowLangMapper.delete(queryWrapper);

        // flowDtl
        this.batchDelFlowDtlByFK(flowIdList);
    }

    // flowDtlBo
    public String add(FlowDtlBo flowDtlBo, String operUserId, Date operTime) throws Exception{
        FlowDtl flowDtl = FlowDtlBo.bo2entity(flowDtlBo);

        String flowDtlId = this.add(flowDtl, operUserId, operTime);

        // 层级名称
        if(ListUtil.isEmpty(flowDtlBo.getFlowDtlLangBoList())){
            throw new InternalException("请输入层级名称");
        }

        for(FlowDtlLangBo flowDtlLangBo :  flowDtlBo.getFlowDtlLangBoList()){
            FlowDtlLang flowDtlLang = FlowDtlLangBo.bo2entity(flowDtlLangBo);
            flowDtlLang.setFlowDtlId(flowDtlId);

            this.add(flowDtlLang, operUserId, operTime);
        }

        return flowDtlId;
    }

    public void mdfById(FlowDtlBo flowDtlBo, String operUserId, Date operTime)throws Exception{
        FlowDtl flowDtl = FlowDtlBo.bo2entity(flowDtlBo);

        this.mdfById(flowDtl, operUserId, operTime);

        if(ListUtil.isNotEmpty(flowDtlBo.getFlowDtlLangBoList())){
            for(FlowDtlLangBo flowDtlLangBo : flowDtlBo.getFlowDtlLangBoList()){
                FlowDtlLang flowDtlLang = FlowDtlLangBo.bo2entity(flowDtlLangBo);
                flowDtlLang.setFlowDtlId(flowDtl.getId());
                this.saveByPPK(flowDtlLang, operUserId, operTime);
            }
        }
    }

    public String saveById(FlowDtlBo flowDtlBo, String operUserId, Date operTime) throws Exception {
        if(StringUtil.isEmpty(flowDtlBo.getId())){
            // 新增
            return this.add(flowDtlBo, operUserId, operTime);
        }else {
            // 修改
            this.mdfById(flowDtlBo, operUserId, operTime);
            return flowDtlBo.getId();
        }
    }

    // flowDtl
    public String add(FlowDtl entity, String operUserId, Date operTime){
        if(StringUtil.isEmpty(entity.getFlowId())){
            throw new InternalException("请输入流程id");
        }

        if(entity.getAreLastLevel() == null){
            entity.setAreLastLevel(false);
        }

        if(entity.getSeq() == null || entity.getSeq() <= 0){
            throw new InternalException("请输入层数");
        }

        entity.setCreateUserId(operUserId);
        entity.setCreateUserTime(operTime);
        entity.setLastUpdateTime(operTime);

        this.flowDtlMapper.insert(entity);

        return entity.getId();
    }

    public void mdfById(FlowDtl entity, String operUserId, Date operTime){
        if(StringUtil.isEmpty(entity.getId())){
            throw new InternalException("请输入层级id");
        }

        if(entity.getFlowId() != null && entity.getFlowId().trim().equals("")){
            throw new InternalException("请输入流程id");
        }

        if(entity.getSeq() != null && entity.getSeq() <= 0){
            throw new InternalException("层级应大于0");
        }

        entity.setCreateUserId(null);
        entity.setCreateUserTime(null);
        entity.setLastUpdateTime(operTime);

        this.flowDtlMapper.updateById(entity);
    }

    public String saveById(FlowDtl entity, String operUserId, Date operTime){
        if(StringUtil.isEmpty(entity.getId())){
            return this.add(entity, operUserId, operTime);
        }else {
            this.mdfById(entity, operUserId, operTime);
            return entity.getId();
        }
    }

    public void delFlowDtlById(List<String> flowDtlIdList, String operUserId, Date operTime) throws Exception{
        if(flowDtlIdList !=null){
            for(String flowDtlId : flowDtlIdList){
                QueryWrapper<FlowDtlLang> flowDtlLangQueryWrapper = new QueryWrapper<>();
                flowDtlLangQueryWrapper.eq(FlowDtlLang.FLOWDTLID,flowDtlId);
                //查询层级
                int seq = this.flowDtlMapper.selectById(flowDtlId).getSeq();
                //拼接url
                String url = "/pc/module/pc010207/"+CK+seq;
                //删除资源
                this.resourceDomain.delByUrl(url);
                flowDtlMapper.deleteById(flowDtlId);
                flowDtlLangMapper.delete(flowDtlLangQueryWrapper);
            }
        }
    }

    public void delFlowDtlByFK(String flowId) throws Exception{
        QueryWrapper<FlowDtl> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(FlowDtl.FLOWID, flowId);

        this.flowDtlMapper.delete(queryWrapper);

        // flowDtlLang
        this.delFlowDtlLangByFK(flowId);
    }

    public void batchDelFlowDtlByFK(List<String> flowIdList){
        QueryWrapper<FlowDtl> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(FlowDtl.FLOWID, flowIdList);

        this.flowDtlMapper.delete(queryWrapper);

        // flowDtlLang
        this.batchDelFlowDtlLangByFK(flowIdList);
    }

    /**
     * 设置最后一层级
     * @param flowId
     * @param operUserId
     * @param operTime
     */
    public void setLastLevel(String flowId, String operUserId, Date operTime){
        QueryWrapper<FlowDtl> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(FlowDtl.FLOWID, flowId)
                    .eq(FlowDtl.ARELASTLEVEL, true);

        Integer lastLevelNum = this.flowDtlMapper.selectCount(queryWrapper);

        if(lastLevelNum != 1){
            if(lastLevelNum > 1){
                // 设置 seq 最大的为最后一层, 其他都不是最后一层
                // 将 最后一层为 true 改成 false
                queryWrapper = new QueryWrapper<>();
                queryWrapper.eq(FlowDtl.FLOWID, flowId)
                        .eq(FlowDtl.ARELASTLEVEL, true);

                FlowDtl notLast = new FlowDtl();
                notLast.setAreLastLevel(false);

                this.flowDtlMapper.update(notLast, queryWrapper);
            }

            // 设置 seq 最大的为最后一层
            queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(FlowDtl.FLOWID, flowId)
                    .orderByDesc(FlowDtl.SEQ);

            List<FlowDtl> flowDtlList = this.flowDtlMapper.selectList(queryWrapper);

            if(ListUtil.isNotEmpty(flowDtlList)){
                FlowDtl lastFlowDtl = flowDtlList.get(0);
                if(lastFlowDtl != null){
                    lastFlowDtl.setAreLastLevel(true);
                    this.mdfById(lastFlowDtl, operUserId, operTime);
                }
            }
        }
    }

    // flowDtlLang
    public String add(FlowDtlLang entity, String operUserId, Date operTime) throws Exception{

        if(StringUtil.isEmpty(entity.getFlowDtlId())){
            throw new InternalException("请输入层级id");
        }

        if(StringUtil.isEmpty(entity.getLangId())){
            throw new InternalException("请输入语言id");
        }

        if(StringUtil.isEmpty(entity.getFlowDtlName())){
            throw new InternalException("请输入层级名称");
        }

        entity.setCreateUserId(operUserId);
        entity.setCreateUserTime(operTime);
        entity.setLastUpdateTime(operTime);

        this.flowDtlLangMapper.insert(entity);

        // 添加审核资源
        this.saveResource(entity.getFlowDtlName(), entity.getFlowDtlId(), entity.getLangId(), CK, operUserId, operTime);

        // 添加回退资源
        this.saveResource(entity.getFlowDtlName(), entity.getFlowDtlId(), entity.getLangId(), UNCK, operUserId, operTime);

        return entity.getId();
    }

    public void mdfByPPK(FlowDtlLang entity, String operUserId, Date operTime) throws Exception{
        if(StringUtil.isEmpty(entity.getFlowDtlId())){
            throw new InternalException("请输入层级id");
        }

        if(StringUtil.isEmpty(entity.getLangId())){
            throw new InternalException("请输入语言id");
        }

        if(entity.getFlowDtlName() != null && entity.getFlowDtlName().trim().equals("")){
            throw new InternalException("请输入层级名称");
        }

        QueryWrapper<FlowDtlLang> flowDtlLangQueryWrapper = new QueryWrapper<>();
        flowDtlLangQueryWrapper.eq(FlowDtlLang.FLOWDTLID, entity.getFlowDtlId())
                                .eq(FlowDtlLang.LANGID, entity.getLangId());

        entity.setId(null);
        entity.setCreateUserId(null);
        entity.setCreateUserTime(null);
        entity.setLastUpdateTime(operTime);

        flowDtlLangMapper.update(entity, flowDtlLangQueryWrapper);

        // 修改 审核资源
        this.saveResource(entity.getFlowDtlName(), entity.getFlowDtlId(), entity.getLangId(), CK, operUserId, operTime);

        // 修改 回退资源
        this.saveResource(entity.getFlowDtlName(), entity.getFlowDtlId(), entity.getLangId(), UNCK, operUserId, operTime);
    }

    public String saveByPPK(FlowDtlLang entity, String operUserId, Date operTime) throws Exception{
        if(StringUtil.isEmpty(entity.getFlowDtlId())){
            throw new InternalException("请输入层级id");
        }

        if(StringUtil.isEmpty(entity.getLangId())){
            throw new InternalException("请输入语言id");
        }

        QueryWrapper<FlowDtlLang> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(FlowDtlLang.FLOWDTLID, entity.getFlowDtlId())
                    .eq(FlowDtlLang.LANGID, entity.getLangId());

        if(this.flowDtlLangMapper.selectCount(queryWrapper) == 0){
            return this.add(entity, operUserId, operTime);
        }else {
            this.mdfByPPK(entity, operUserId, operTime);
            return entity.getId();
        }
    }

    public void delFlowDtlLangByFK(String flowDtlId) throws Exception{
        QueryWrapper<FlowDtlLang> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(FlowDtlLang.FLOWDTLID, flowDtlId);

        this.flowDtlLangMapper.delete(queryWrapper);

        // 删除资源
        Integer seq = null;
        String menuId = null;

        FlowDtl flowDtl = this.flowDtlMapper.selectById(flowDtlId);

        if(flowDtl != null){
            seq = flowDtl.getSeq();

            Flow flow = this.flowMapper.selectById(flowDtl.getFlowId());

            if(flow != null){
                menuId = flow.getMenuId();
            }
        }

        if(seq != null && menuId != null){
            Menu menu = this.menuMapper.selectById(menuId);

            if(menu != null){
                String url = "/pc/module/" + menu.getMenuNo() + "/" + CK + seq;
                this.resourceDomain.delByUrl(url);
            }
        }
    }

    public void batchDelFlowDtlLangByFK(List<String> flowDtlIdList){
        QueryWrapper<FlowDtlLang> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(FlowDtlLang.FLOWDTLID, flowDtlIdList);

        this.flowDtlLangMapper.delete(queryWrapper);
    }

    // resource
    public void saveResource(String flowDtlName, String flowDtlId, String langId,
                             String pre, String operUserId, Date operTime)throws  Exception{
        String menuId = "";
        Integer seq = null;

        FlowDtl flowDtl = this.flowDtlMapper.selectById(flowDtlId);

        if(flowDtl != null){
            seq = flowDtl.getSeq();

            Flow flow = this.flowMapper.selectById(flowDtl.getFlowId());

            if(flow != null){
                menuId = flow.getMenuId();
            }
        }

        Menu menu = this.menuMapper.selectById(menuId);

        if(menu == null){
            throw new InternalException("没有这个菜单");
        }

        String url = "/pc/module/" + menu.getMenuNo() + "/" + pre + seq;

        ResourceBo bo = new ResourceBo();
        bo.setMenuId(menuId);
        bo.setResOperNo(RESOPER + pre + seq);
        bo.setResUrl(url);
        List<ResourceLangBo> resourceLangBoList = new ArrayList<>();
        ResourceLangBo resourceLangBo = new ResourceLangBo();
        resourceLangBo.setLangId(langId);
        resourceLangBo.setResDesc(null);
        if(pre.equals(CK)){
            resourceLangBo.setResName(flowDtlName);
        }else if(pre.equals(UNCK)){
            resourceLangBo.setResName(flowDtlName + " - 回退");
        }

        resourceLangBoList.add(resourceLangBo);
        bo.setResourceLangBoList(resourceLangBoList);

        this.resourceDomain.saveByPPK(bo, operUserId, operTime);
    }

    public void flowLog(CheckBill checkBill, String operUserId, Date operTime, String ip) {
        FlowLog flowLog = new FlowLog();
        flowLog.setUserId(operUserId);
        flowLog.setIp(ip);
        flowLog.setCheckTime(operTime);
        flowLog.setOperNo("Check");
        flowLog.setBillId(checkBill.getBillId());
        flowLog.setFlowId(checkBill.getFlowId());
        flowLog.setLevel(checkBill.getCurentLevel());
        flowLog.setCreateUserId(operUserId);
        flowLog.setCreateUserTime(operTime);
        flowLog.setLastUpdateTime(operTime);

        flowLogMpapper.insert(flowLog);

        /*
        表：tFlowLog --审批日志表
	字段：
	id				--主键
	userId 			--执行操作的人
	ip 			--地点（ip）
	checkTime 	--执行时间
	operNo			--操作
	billId 		--单据ID
	flowId 		--流程ID
	level 	--层级

         */
    }

    public void addFlowLog(FlowLog entity, String operUserId, Date operTime) throws Exception {
        if(StringUtil.isEmpty(entity.getUserId())){
            throw new Exception("操作人不能为空");
        }
        if(StringUtil.isEmpty(entity.getIp())){
            throw new Exception("ip地址为空");
        }
        if(entity.getCheckTime() == null || entity.getCheckTime().equals("")){
            throw new Exception("执行时间不能为空");
        }
        if(StringUtil.isEmpty(entity.getOperNo())){
            throw new Exception("操作不能为空");
        }
        if(StringUtil.isEmpty(entity.getFlowId())){
            throw new Exception("流程id不能为空");
        }

        entity.setCreateUserId(operUserId);
        entity.setCreateUserTime(operTime);
        entity.setLastUpdateTime(operTime);

        flowLogMpapper.insert(entity);
    }
}
