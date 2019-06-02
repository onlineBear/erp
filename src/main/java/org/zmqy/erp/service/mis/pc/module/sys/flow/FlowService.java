package org.zmqy.erp.service.mis.pc.module.sys.flow;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zmqy.erp.domain.mis.flow.IFlowDomain;
import org.zmqy.erp.mapper.mis.pc.module.sys.flow.flowModule.FlowModuleMapper;
import org.zmqy.erp.model.mis.bo.sys.flow.FlowBo;
import org.zmqy.erp.model.mis.bo.sys.flow.FlowDtlBo;
import org.zmqy.erp.model.mis.bo.sys.flow.FlowDtlLangBo;
import org.zmqy.erp.model.mis.bo.sys.flow.FlowLangBo;
import org.zmqy.erp.model.mis.entity.CheckBill;
import org.zmqy.erp.tool.helper.Ip.IpHelper;
import org.zmqy.erp.tool.util.common.dataType.ListUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
@Slf4j
public class FlowService {

    @Autowired
    private IFlowDomain flowDomain;

    @Autowired
    private FlowModuleMapper flowListMapper;

    @Autowired
    private HttpServletRequest request;


    /**
     * 新增流程
     * @param paramsMap
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void add(Map<String, Object> paramsMap)throws Exception{
        Date nowTime = new Date();
        String loginLangId = (String) paramsMap.get("loginLangId");//语言
        String loginUserId = (String) paramsMap.get("loginUserId");//用户
        String loginStoreId = (String) paramsMap.get("loginStoreId");//分店
        String upFlowId = (String) paramsMap.get("upFlowId");
        String description = (String) paramsMap.get("description");
        String menuId = (String) paramsMap.get("menuId");//模块菜单
        Integer minLevel = 0;
        if(paramsMap.get("minLevel")==null || paramsMap.get("minLevel").equals("")){
             minLevel = 0;
        }else{
             minLevel = (Integer) paramsMap.get("minLevel");//最小层级
        }

        String flowName = (String) paramsMap.get("flowName");//流程名称

        FlowBo flowBo = new FlowBo();
        flowBo.setUpFlowId(upFlowId);
        flowBo.setMinLevel(minLevel);
        flowBo.setMenuId(menuId);

        List<FlowLangBo> flowLangBoList = new ArrayList<>();
        FlowLangBo flowLangBo = new FlowLangBo();
        flowLangBo.setDescription(description);
        flowLangBo.setFlowName(flowName);
        flowLangBo.setLangId(loginLangId);
        flowLangBoList.add(flowLangBo);




        List<FlowDtlBo> flowDtlBoList = new ArrayList<>();

        List<Object> paramsLevel = (List<Object>) paramsMap.get("paramsLevel");//层级数据

        if(ListUtil.isNotEmpty(paramsLevel)){
            for(int i = 0; i<paramsLevel.size();i++){
                Map<String, Object> params = (Map<String, Object>) paramsLevel.get(i);
                List<FlowDtlLangBo> flowDtlLangBoList = new ArrayList<>();
                if(params.get("seq").equals("") && params.get("areLastLevel").equals("") && params.get("flowDtlName").equals("")){
                }else{
                    Integer   seq = (Integer)params.get("seq");//层级
                    Boolean areLastLevel = (Boolean) params.get("areLastLevel");//是否最终层
                    String flowDtlName = (String) params.get("flowDtlName");//层级名称
                    String flowDesc = (String) params.get("flowDesc");//描述

                    FlowDtlBo flowDtlBo = new FlowDtlBo();
                    flowDtlBo.setAreLastLevel(areLastLevel);
                    flowDtlBo.setSeq(seq);

                    FlowDtlLangBo flowDtlLangBo = new FlowDtlLangBo();
                    flowDtlLangBo.setFlowDesc(flowDesc);
                    flowDtlLangBo.setFlowDtlName(flowDtlName);
                    flowDtlLangBo.setLangId(loginLangId);

                    flowDtlLangBoList.add(flowDtlLangBo);
                    flowDtlBo.setFlowDtlLangBoList(flowDtlLangBoList);

                    flowDtlBoList.add(flowDtlBo);
                }

            }

        }
        flowBo.setFlowLangBoList(flowLangBoList);
        flowBo.setFlowDtlBoList(flowDtlBoList);
        flowDomain.add(flowBo,loginUserId, nowTime);

    }

    /**
     * 查询流程
     * @param langId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public List<Map> getFlowList(String langId){
        return this.flowListMapper.getFlowList(langId);
    }

    /**
     * 查询流程详情
     * @param flowId
     * @param langId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Map<String,List<Map>> getFlowDtlList(String flowId, String langId){
        Map<String,List<Map>> map = new HashMap<>();
        List<Map> flowDtlList = this.flowListMapper.getFlowDtlList(flowId, langId);
        List<Map> flow = this.flowListMapper.getFlowByMenu(flowId, langId);
        map.put("flowDtlList",flowDtlList);
        map.put("flow",flow);
        return map;
    }


    /**
     * 修改流程
     * @param paramsMap
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void mdfFlow(Map<String, Object> paramsMap) throws Exception {
        Date nowTime = new Date();
        String loginLangId = (String) paramsMap.get("loginLangId");//语言
        String loginUserId = (String) paramsMap.get("loginUserId");//用户
        String loginStoreId = (String) paramsMap.get("loginStoreId");//分店
        String upFlowId = (String) paramsMap.get("upFlowId");
        String description = (String) paramsMap.get("description");
        String menuId = (String) paramsMap.get("menuId");
        System.out.println("/**/*/*////////////////////////**********************************");
        String flowId = (String) paramsMap.get("flowId");

        Integer minLevel = (Integer) paramsMap.get("minLevel");//最小层级
        String flowName = (String) paramsMap.get("flowName");//流程名称

        FlowBo flowBo = new FlowBo();
        flowBo.setMinLevel(minLevel);
        flowBo.setId(flowId);
        flowBo.setUpFlowId(upFlowId);
        flowBo.setMenuId(menuId);

        List<FlowLangBo> flowLangBoList = new ArrayList<>();
        FlowLangBo flowLangBo = new FlowLangBo();
        flowLangBo.setFlowId(flowId);
        flowLangBo.setDescription(description);
        flowLangBo.setFlowName(flowName);
        flowLangBo.setLangId(loginLangId);
        flowLangBoList.add(flowLangBo);

        List<FlowDtlBo> flowDtlBoList = new ArrayList<>();
        List<Map> paramsLevelList = (List<Map>) paramsMap.get("paramsLevel");//层级数据

        if(ListUtil.isNotEmpty(paramsLevelList)){
            for(int i = 0; i<paramsLevelList.size();i++) {
                Map<String, Object> params = (Map<String, Object>) paramsLevelList.get(i);
                List<FlowDtlLangBo> flowDtlLangBoList = new ArrayList<>();

                Integer seq = (Integer) params.get("seq");//层级;
                Boolean areLastLevel = (Boolean) params.get("areLastLevel");//是否最终层

                String flowDtlName = (String) params.get("flowDtlName");//层级名称
                String flowDesc = (String) params.get("flowDesc");//描述
                String flowDtlId = (String) params.get("flowDtlId");

                FlowDtlBo flowDtlBo = new FlowDtlBo();
                flowDtlBo.setId(flowDtlId);
                flowDtlBo.setFlowId(flowId);
                flowDtlBo.setAreLastLevel(areLastLevel);
                flowDtlBo.setSeq(seq);
                //flowDtlBoList.add(flowDtlBo);

                FlowDtlLangBo flowDtlLangBo = new FlowDtlLangBo();
                flowDtlLangBo.setFlowDesc(flowDesc);
                flowDtlLangBo.setFlowDtlName(flowDtlName);
                flowDtlLangBo.setLangId(loginLangId);
                flowDtlLangBoList.add(flowDtlLangBo);
                flowDtlBo.setFlowDtlLangBoList(flowDtlLangBoList);

                flowDtlBoList.add(flowDtlBo);
            }
        }

        flowBo.setFlowLangBoList(flowLangBoList);
        flowBo.setFlowDtlBoList(flowDtlBoList);
        System.out.println(flowBo);
        flowDomain.delFlowDtlById(flowBo.getId(), (List<String>) paramsMap.get("delFlowDtlById"), loginUserId, nowTime);

        flowDomain.mdfById(flowBo, loginUserId, nowTime);
    }

    /**
     * 删除流程
     * @param paramsMap
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void delFlow(Map<String, Object> paramsMap)throws Exception{
        flowDomain.delFlowById((List<String>) paramsMap.get("delFlowById"), (String) paramsMap.get("loginUserId"), new Date());
    }
}
