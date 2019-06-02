package org.zmqy.erp.service.mis.pc.module.sys.programDra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zmqy.erp.domain.mis.programDra.IProgramDraDoMain;
import org.zmqy.erp.domain.mis.programDra.impl.ProgramDraDoMain;
import org.zmqy.erp.mapper.mis.pc.module.sys.programDra.programDraModule.ProgramDraModuleMapper;
import org.zmqy.erp.model.mis.bo.sys.programDra.*;
import org.zmqy.erp.tool.util.common.StringUtil;
import org.zmqy.erp.tool.util.common.dataType.ListUtil;

import java.util.*;

@Service
public class ProgramDraService {
    @Autowired
    private IProgramDraDoMain programDraDoMain;

    @Autowired
    private ProgramDraModuleMapper programDraModuleMapper;


    /**
     * 新增
     * @param paramsMap
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void add(Map<String, Object> paramsMap)throws Exception{
        Date nowTime = new Date();
        String loginLangId = (String) paramsMap.get("loginLangId");//语言
        String loginUserId = (String) paramsMap.get("loginUserId");//用户
        String loginStoreId = (String) paramsMap.get("loginStoreId");//分店
        String classType = (String) paramsMap.get("classType");//画布类型
//        Integer seq = (Integer) paramsMap.get("seq");//流程图排序
        String linkFromPortIdProperty = (String) paramsMap.get("linkFromPortIdProperty");
        String linkToPortIdProperty = (String) paramsMap.get("linkToPortIdProperty");
        String programDraName = (String) paramsMap.get("programDraName");
        ProgramDraBo programDraBo = new ProgramDraBo();
        programDraBo.setClassType(classType);
//        programDraBo.setSeq(seq);
        programDraBo.setLinkFromPortIdProperty(linkFromPortIdProperty);
        programDraBo.setLinkToPortIdProperty(linkToPortIdProperty);

        List<ProgramDraLangBo> programDraLangBoList = new ArrayList<>();
        ProgramDraLangBo programDraLangBo = new ProgramDraLangBo();
        programDraLangBo.setLangId(loginLangId);
        programDraLangBo.setProgramDraName(programDraName);
        programDraLangBoList.add(programDraLangBo);

        List<NodeBo> nodeBoList = new ArrayList<>();
        List<Object> nodeDataArray = (List<Object>) paramsMap.get("nodeDataArray");
        if(ListUtil.isNotEmpty(nodeDataArray)){
            for(int i = 0; i<nodeDataArray.size();i++){
                Map<String, Object> params = (Map<String, Object>) nodeDataArray.get(i);
                List<NodeLangBo> nodeLangBoList = new ArrayList<>();
                String key = (String) params.get("key");
                String height = (String) params.get("height");
                String width = (String) params.get("width");
                String category = (String) params.get("category");
                String loc = (String) params.get("loc");
                String link = (String) params.get("link");
                String text = (String) params.get("text");

                NodeBo nodeBo = new NodeBo();
                nodeBo.setNodeIdentity(key);
                nodeBo.setWidth(width);
                nodeBo.setHeight(height);
                nodeBo.setCategory(category);
                nodeBo.setLoc(loc);
                nodeBo.setLink(link);

                NodeLangBo nodeLangBo = new NodeLangBo();
                nodeLangBo.setContent(text);
                nodeLangBo.setLangId(loginLangId);
                nodeLangBoList.add(nodeLangBo);
                nodeBo.setNodeLangBoList(nodeLangBoList);
                nodeBoList.add(nodeBo);


            }
        }

        List<LinkBo> linkBoList = new ArrayList<>();
        List<Object> linkDataArray = (List<Object>) paramsMap.get("linkDataArray");
        if(ListUtil.isNotEmpty(linkDataArray)){
            for(int i = 0; linkDataArray.size() > i;i++){
                Map<String,Object> params = (Map<String,Object>)linkDataArray.get(i);
                List<LinkLangBo> linkLangBoList = new ArrayList<>();
                String from = (String) params.get("from");
                String to = (String) params.get("to");
                String fromPort = (String) params.get("fromPort");
                String toPort = (String) params.get("toPort");
                String points = (String) params.get("points");
                String content = (String) params.get("text");
                LinkBo linkBo = new LinkBo();
                linkBo.setDeparture(from);
                linkBo.setDirection(to);
                linkBo.setFromPort(fromPort);
                linkBo.setToPort(toPort);
                linkBo.setPoints(points);

                LinkLangBo linkLangBo = new LinkLangBo();
                linkLangBo.setContent(content);
                linkLangBo.setLangId(loginLangId);

                linkLangBoList.add(linkLangBo);
                linkBo.setLinkLangBoList(linkLangBoList);
                linkBoList.add(linkBo);


            }
        }

        programDraBo.setProgramDraLangBoList(programDraLangBoList);
        programDraBo.setNodeBoList(nodeBoList);
        programDraBo.setLinkBoList(linkBoList);
        programDraDoMain.add(programDraBo,loginUserId, nowTime);


    }

    @Transactional(rollbackFor = Exception.class)
    public List<Map> getProgramDraList(String langId){
        return this.programDraModuleMapper.getProgramDraList(langId);
    }

    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> getProgramDraDtlList(String programDraId, String langId){

        List<Map> programDraDtlList = this.programDraModuleMapper.getProgramDraDtlList(programDraId, langId);
        List<Map> nodeList = this.programDraModuleMapper.getNode(programDraId, langId);
        List<Map> linkList = this.programDraModuleMapper.getLink(programDraId, langId);

        //这里需要进行部分数据转换，因为部分数据前端需要字段和数据库字段不对应
            for(Map<String, Object> nodeMap : nodeList){
                String key = (String)nodeMap.get("nodeIdentity");
                String text = (String)nodeMap.get("content");
                nodeMap.put("key",key);
                nodeMap.put("text",text);
                nodeMap.remove("nodeIdentity");
                nodeMap.remove("content");
            }

            for(Map<String, Object> link : linkList){
                String from = (String) link.get("departure");
                String to = (String)link.get("direction");
                String text = (String)link.get("content");
                link.put("from", from);
                link.put("to", to);
                link.put("text", text);
                link.remove("departure");
                link.remove("direction");
                link.remove("content");
            }
            Map<String,Object> programDraDtlMap = programDraDtlList.get(0);

        programDraDtlMap.put("nodeDataArray", nodeList);
        programDraDtlMap.put("linkDataArray", linkList);
        return programDraDtlMap;
    }

    @Transactional(rollbackFor = Exception.class)
    public void mdfProgramDra(Map<String, Object> paramsMap) throws Exception {
        Date nowTime = new Date();
        String loginLangId = (String) paramsMap.get("loginLangId");//语言
        String loginUserId = (String) paramsMap.get("loginUserId");//用户
        String loginStoreId = (String) paramsMap.get("loginStoreId");//分店
        String classType = (String) paramsMap.get("classType");//画布类型
        Integer seq = (Integer) paramsMap.get("seq");//流程图排序
        String linkFromPortIdProperty = (String) paramsMap.get("linkFromPortIdProperty");
        String linkToPortIdProperty = (String) paramsMap.get("linkToPortIdProperty");
        String programDraName = (String) paramsMap.get("programDraName");
        String programDraId = (String) paramsMap.get("programDraId");

        ProgramDraBo programDraBo = new ProgramDraBo();
        programDraBo.setId(programDraId);
        programDraBo.setClassType(classType);
        programDraBo.setSeq(seq);
        programDraBo.setLinkFromPortIdProperty(linkFromPortIdProperty);
        programDraBo.setLinkToPortIdProperty(linkToPortIdProperty);

        List<ProgramDraLangBo> programDraLangBoList = new ArrayList<>();
        ProgramDraLangBo programDraLangBo = new ProgramDraLangBo();
        programDraLangBo.setProgramDraId(programDraId);
        programDraLangBo.setLangId(loginLangId);
        programDraLangBo.setProgramDraName(programDraName);
        programDraLangBoList.add(programDraLangBo);

        List<NodeBo> nodeBoList = new ArrayList<>();
        List<Object> nodeDataArray = (List<Object>) paramsMap.get("nodeDataArray");
        if(ListUtil.isNotEmpty(nodeDataArray)){
            for(int i = 0; i<nodeDataArray.size();i++){
                Map<String, Object> params = (Map<String, Object>) nodeDataArray.get(i);
                List<NodeLangBo> nodeLangBoList = new ArrayList<>();
                String key =  params.get("key").toString();
                String height = null;
                String width = null;
                if(params.get("height")!=null){
                    height =  params.get("height").toString();
                }
                if(params.get("width")!=null){
                    width = params.get("width").toString();
                }
                String category = (String) params.get("category");
                String loc = (String) params.get("loc");
                String link = (String) params.get("link");
                String text = (String) params.get("text");
                String nodeId = (String) params.get("nodeId");



                NodeBo nodeBo = new NodeBo();
                nodeBo.setProgramDraId(programDraId);
                nodeBo.setNodeIdentity(key);
                nodeBo.setWidth(width);
                nodeBo.setHeight(height);
                nodeBo.setCategory(category);
                nodeBo.setLoc(loc);
                nodeBo.setLink(link);

                NodeLangBo nodeLangBo = new NodeLangBo();

//                if(StringUtil.isNotEmpty(nodeId)){
                    nodeBo.setId(nodeId);

                    nodeLangBo.setNodeId(nodeId);
//                }
                nodeLangBo.setContent(text);
                nodeLangBo.setLangId(loginLangId);
                nodeLangBoList.add(nodeLangBo);
                nodeBo.setNodeLangBoList(nodeLangBoList);
                nodeBoList.add(nodeBo);


            }
        }/*else{
            NodeBo nodeBo = new NodeBo();
            nodeBo.setProgramDraId(programDraId);
            nodeBoList.add(nodeBo);
        }*/

        List<LinkBo> linkBoList = new ArrayList<>();
        List<Object> linkDataArray = (List<Object>) paramsMap.get("linkDataArray");
        if(ListUtil.isNotEmpty(linkDataArray)){
            for(int i = 0; linkDataArray.size() > i;i++){
                Map<String,Object> params = (Map<String,Object>)linkDataArray.get(i);
                List<LinkLangBo> linkLangBoList = new ArrayList<>();
                String from =  params.get("from")+"";
                String to =  params.get("to")+"";
                String fromPort = (String) params.get("fromPort");
                String toPort = (String) params.get("toPort");
                String points = params.get("points").toString();
                String content = (String) params.get("text");
                String linkId = (String) params.get("linkId");
                LinkBo linkBo = new LinkBo();
                linkBo.setId(linkId);
                linkBo.setProgramDraId(programDraId);
                linkBo.setDeparture(from);
                linkBo.setDirection(to);
                linkBo.setFromPort(fromPort);
                linkBo.setToPort(toPort);
                linkBo.setPoints(points);

                LinkLangBo linkLangBo = new LinkLangBo();
                linkLangBo.setLinkId(linkId);
                linkLangBo.setContent(content);
                linkLangBo.setLangId(loginLangId);

                linkLangBoList.add(linkLangBo);
                linkBo.setLinkLangBoList(linkLangBoList);
                linkBoList.add(linkBo);


            }
        }/*else{
            LinkBo linkBo = new LinkBo();
            linkBo.setProgramDraId(programDraId);
            linkBoList.add(linkBo);
        }*/

        programDraBo.setProgramDraLangBoList(programDraLangBoList);
        programDraBo.setNodeBoList(nodeBoList);
        programDraBo.setLinkBoList(linkBoList);

//        this.programDraDoMain.delNode(programDraBo.getId(),paramsMap.get("nodeIdList"),loginUserId,nowTime);
//        this.programDraDoMain.delLink(programDraBo.getId(),paramsMap.get("LinkIdList"),loginUserId,nowTime);
        this.programDraDoMain.mdfProgramDra(programDraBo,loginUserId,nowTime);

    }

    @Transactional(rollbackFor = Exception.class)
    public void delProgramDra(Map<String, Object> paramsMap) throws Exception{
       String langId = (String)paramsMap.get("loginLangId");
       String programDraId = (String)paramsMap.get("programDraId");
       programDraDoMain.delProgramDra(programDraId,langId);

    }

    @Transactional(rollbackFor = Exception.class)
    public void mdfSeq(Map<String, Object> paramsMap) throws Exception{
        Date nowTime = new Date();
        String loginLangId = (String) paramsMap.get("loginLangId");//语言
        String loginUserId = (String) paramsMap.get("loginUserId");//用户
        String loginStoreId = (String) paramsMap.get("loginStoreId");//分店
        List seqList = (List) paramsMap.get("seqList");
        for(int i = 0 ; seqList.size()>i; i++){
            Map<String, Object> map = (Map<String, Object>) seqList.get(i);
            ProgramDraBo programDraBo = new ProgramDraBo();
            programDraBo.setId((String)map.get("programDraId"));
            programDraBo.setSeq(i+1);
            programDraDoMain.mdfSeq(programDraBo,loginUserId,nowTime);
        }

    }


}
