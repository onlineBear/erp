package org.zmqy.erp.domain.mis.programDra.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.zmqy.erp.domain.mis.programDra.IProgramDraDoMain;
import org.zmqy.erp.framework.exception.model.unI18n.InternalException;
import org.zmqy.erp.mapper.mis.pc.module.sys.programDra.*;
import org.zmqy.erp.model.mis.bo.sys.programDra.*;
import org.zmqy.erp.model.mis.flow.FlowLang;
import org.zmqy.erp.model.mis.programDra.*;
import org.zmqy.erp.tool.util.common.StringUtil;
import org.zmqy.erp.tool.util.common.dataType.IntegerUtil;
import org.zmqy.erp.tool.util.common.dataType.ListUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
@Transactional(rollbackFor = Exception.class)
public class ProgramDraDoMain implements IProgramDraDoMain {

    @Autowired
    private ProgramDraMapper programDraMapper;
    @Autowired
    private ProgramDraLangMapper programDraLangMapper;
    @Autowired
    private NodeMapper nodeMapper;
    @Autowired
    private NodeLangMapper nodeLangMapper;
    @Autowired
    private LinkMapper linkMapper;
    @Autowired
    private LinkLangMapper linkLangMapper;

    /**
     * 新增流程图
     * @param bo
     * @param operUserId
     * @param operTime
     * @return
     * @throws Exception
     */
    @Override
    public String add(ProgramDraBo bo, String operUserId, Date operTime) throws Exception {
        if(bo == null){
            throw new InternalException("请输入流程图");
        }
        ProgramDra programDra = ProgramDraBo.bo2entity(bo);

        // 流程图主表
        String id = (String) this.add(programDra, operUserId, operTime);

        for(ProgramDraLangBo programDraLangBo : bo.getProgramDraLangBoList()){
            ProgramDraLang programDraLang = ProgramDraLangBo.bo2entity(programDraLangBo);
            programDraLang.setProgramDraId(id);
            this.add(programDraLang, operUserId, operTime,operTime);
        }
        //新增节点数据
        List<NodeBo> nodeBoList = bo.getNodeBoList();
        if(ListUtil.isNotEmpty(nodeBoList)){
            for(NodeBo nodeBo : nodeBoList){
                //................
                nodeBo.setProgramDraId(id);
                this.add(nodeBo, operUserId, operTime);
            }
        }
        //新增线条数据
        List<LinkBo> linkBoList = bo.getLinkBoList();
        if(ListUtil.isNotEmpty(linkBoList)){
            for(LinkBo linkBo : linkBoList){
                //................
                linkBo.setProgramDraId(id);
                this.add(linkBo, operUserId, operTime);
            }
        }
        return null;
    }

    /**
     * 修改流程图
     * @param bo
     * @param operUserId
     * @param operTime
     * @throws Exception
     */
    @Override
    public void mdfProgramDra(ProgramDraBo bo, String operUserId, Date operTime) throws Exception {
        if(bo==null || bo.equals("")){
            throw new RuntimeException("没有需要修改的流程图");
        }
        ProgramDra programDra = ProgramDraBo.bo2entity(bo);
        this.mdf(programDra,operUserId,operTime);


        List<ProgramDraLangBo> programDraLangBoList = bo.getProgramDraLangBoList();
        if(ListUtil.isNotEmpty(programDraLangBoList)){
            for(ProgramDraLangBo programDraLangBo : programDraLangBoList){
                ProgramDraLang programDraLang = ProgramDraLangBo.bo2entity(programDraLangBo);
                this.mdf(programDraLang,operUserId,operTime,operTime);
            }
        }

        //在这边统一删除，不能在for里面删除不然，会只保存到最后一次的数据
        List<LinkBo> linkBoList =  bo.getLinkBoList();
            this.delLink(bo.getId());
        List<NodeBo> nodeBoList =  bo.getNodeBoList();
            this.delNode(bo.getId());



        if(ListUtil.isNotEmpty(nodeBoList)){
            for(NodeBo nodeBo : nodeBoList){
                this.save(nodeBo,operUserId,operTime,operTime);
            }
        }

        if(ListUtil.isNotEmpty(linkBoList)){
            for(LinkBo linkBo : linkBoList){
                this.save(linkBo,operUserId,operTime,operTime);
            }
        }


    }

    /**
     * 修改前删除线条数据
     * @param programDraId
     */
    public void delLink(String programDraId){
        QueryWrapper<Link> linkQueryWrapper = new QueryWrapper<>();
        linkQueryWrapper.eq(Link.PROGRAMDRAID,programDraId);

        List<Link> linkList = linkMapper.selectList(linkQueryWrapper);
        if(ListUtil.isNotEmpty(linkList)){
            for(Link links : linkList){
                String linkId = links.getId();
                QueryWrapper<LinkLang> linkLangQueryWrapper = new QueryWrapper<>();
                linkLangQueryWrapper.eq(LinkLang.LINKID,linkId);
                linkLangMapper.delete(linkLangQueryWrapper);
            }
        }
        linkMapper.delete(linkQueryWrapper);

    }

    /**
     * 修改前删除节点数据
     * @param programDraId
     */
    public void delNode(String programDraId){
         QueryWrapper<Node> nodeQueryWrapper = new QueryWrapper<>();
        nodeQueryWrapper.eq(Node.PROGRAMDRAID,programDraId);
        List<Node> nodeList = nodeMapper.selectList(nodeQueryWrapper);
        if(ListUtil.isNotEmpty(nodeList)){
            for(Node nodes : nodeList){
                String nodesId = nodes.getId();
                QueryWrapper<NodeLang> nodeLangQueryWrapper = new QueryWrapper<>();
                nodeLangQueryWrapper.eq(NodeLang.NODEID,nodesId);
                nodeLangMapper.delete(nodeLangQueryWrapper);
            }
        }
        nodeMapper.delete(nodeQueryWrapper);

    }

    /**
     * 删除流程图
     * @param programDraId
     * @param langId
     * @throws Exception
     */
    @Override
    public void delProgramDra(String programDraId, String langId) throws Exception {
        QueryWrapper<ProgramDra> programDraQueryWrapper = new QueryWrapper<>();
        programDraQueryWrapper.eq(ProgramDra.ID,programDraId);
        programDraMapper.delete(programDraQueryWrapper);

        QueryWrapper<ProgramDraLang> programDraLangQueryWrapper = new QueryWrapper<>();
        programDraLangQueryWrapper.eq(ProgramDraLang.PROGRAMDRAID,programDraId).eq(ProgramDraLang.LANGID,langId);
        programDraLangMapper.delete(programDraLangQueryWrapper);

        QueryWrapper<Node> nodeQueryWrapper = new QueryWrapper<>();
        nodeQueryWrapper.eq(Node.PROGRAMDRAID,programDraId);
        List<Node> nodeList = nodeMapper.selectList(nodeQueryWrapper);
        List nodeIdList = new ArrayList();
        for(Node node : nodeList){
            nodeIdList.add(node.getId());
        }
        nodeMapper.delete(nodeQueryWrapper);

        QueryWrapper<NodeLang> nodeLangQueryWrapper = new QueryWrapper<>();
        for(Object nodeId : nodeIdList){
            nodeLangQueryWrapper.eq(NodeLang.NODEID,nodeId).eq(NodeLang.LANGID,langId);
            nodeLangMapper.delete(nodeLangQueryWrapper);
        }

        QueryWrapper<Link> linkQueryWrapper = new QueryWrapper<>();
        linkQueryWrapper.eq(Link.PROGRAMDRAID,programDraId);
        List<Link> linkList = linkMapper.selectList(linkQueryWrapper);
        List linkIdList = new ArrayList();
        for(Link link : linkList){
            linkIdList.add(link.getId());
        }
        linkMapper.delete(linkQueryWrapper);

        QueryWrapper<LinkLang> linkLangQueryWrapper = new QueryWrapper<>();
        for(Object linkId : linkIdList){
            linkLangQueryWrapper.eq(LinkLang.LINKID,linkId).eq(LinkLang.LANGID,langId);
            linkLangMapper.delete(linkLangQueryWrapper);
        }
    }

    /**
     * 修改顺序
     * @param programDraBo
     * @param operUserId
     * @param operTime
     * @throws Exception
     */
    @Override
    public void mdfSeq(ProgramDraBo programDraBo,String operUserId, Date operTime) throws Exception {
        ProgramDra programDra = ProgramDraBo.bo2entity(programDraBo);
        QueryWrapper<ProgramDra> programDraQueryWrapper = new QueryWrapper<>();
        programDraQueryWrapper.eq(ProgramDra.ID,programDra.getId());
        programDra.setLinkToPortIdProperty(null);
        programDra.setLinkFromPortIdProperty(null);
        programDra.setClassType(null);
        programDra.setCreateUserId(null);
        programDra.setCreateUserTime(null);
        programDra.setLastUpdateTime(operTime);
        programDraMapper.update(programDra,programDraQueryWrapper);
    }


    /**
     * 修改线条数据
     * @param linkBo
     * @param operUserId
     * @param createUserTime
     * @param lastUpdateTime
     * @throws Exception
     */
    public void save(LinkBo linkBo,String operUserId,Date createUserTime,Date lastUpdateTime)throws Exception{
        Link link = LinkBo.bo2entity(linkBo);
        /*QueryWrapper<Link> linkQueryWrapper = new QueryWrapper<>();
        linkQueryWrapper.eq(Link.PROGRAMDRAID,link.getProgramDraId());

        List<Link> linkList = linkMapper.selectList(linkQueryWrapper);
        if(ListUtil.isNotEmpty(linkList)){
            for(Link links : linkList){
                String linkId = links.getId();
                QueryWrapper<LinkLang> linkLangQueryWrapper = new QueryWrapper<>();
                linkLangQueryWrapper.eq(LinkLang.LINKID,linkId);
                linkLangMapper.delete(linkLangQueryWrapper);
            }
        }
        linkMapper.delete(linkQueryWrapper);*/
        String linkId =  this.add(link,operUserId,createUserTime,lastUpdateTime);

            List<LinkLangBo> linkLangBoList = linkBo.getLinkLangBoList();
            if(ListUtil.isNotEmpty(linkLangBoList)){
                for(LinkLangBo linkLangBo : linkLangBoList){
                    LinkLang linkLang = LinkLangBo.bo2entity(linkLangBo);
                    linkLang.setLinkId(linkId);
                    this.mdf(linkLang,operUserId,createUserTime,lastUpdateTime);
                }
            }


    }

    /**
     * 修改线条数据语言表tLinkLang
     * @param entity
     * @param operUserId
     * @param createUserTime
     * @param lastUpdateTime
     * @throws Exception
     */
    public void mdf(LinkLang entity,String operUserId,Date createUserTime,Date lastUpdateTime)throws Exception{
        QueryWrapper<LinkLang> linkLangQueryWrapper = new QueryWrapper<>();
        linkLangQueryWrapper.eq(LinkLang.LINKID, entity.getLinkId())
                .eq(LinkLang.LANGID, entity.getLangId());
        List<LinkLang> linkLangList = linkLangMapper.selectList(linkLangQueryWrapper);
        String id = null;
        if(ListUtil.isNotEmpty(linkLangList)){

            for(LinkLang linkLang : linkLangList){
                operUserId = linkLang.getCreateUserId();
                createUserTime = linkLang.getCreateUserTime();
                id = linkLang.getId();
            }
        }
//        linkLangMapper.delete(linkLangQueryWrapper);
        entity.setId(id);
        if(entity!=null || entity.equals("")){
            this.add(entity,operUserId,createUserTime,lastUpdateTime);
        }
    }

    /**
     * 修改流程图tProgramDra
     * @param entity
     * @param operUserId
     * @param operTime
     */
    public void mdf(ProgramDra entity, String operUserId, Date operTime)throws Exception{
        if(StringUtil.isEmpty(entity.getClassType())){
            throw new RuntimeException("画布类型为空");
        }
        if(entity.getSeq()==null || entity.getSeq().equals("")){
            throw new RuntimeException("流程图顺序为空");
        }
        if(StringUtil.isEmpty(entity.getLinkFromPortIdProperty())){
            throw new RuntimeException("linkFromPortIdProperty为空");
        }
        if(StringUtil.isEmpty(entity.getLinkToPortIdProperty())){
            throw new RuntimeException("linkToPortIdProperty为空");
        }

        entity.setCreateUserId(null);
        entity.setCreateUserTime(null);
        entity.setLastUpdateTime(operTime);
        this.programDraMapper.updateById(entity);
    }

    /**
     * 修改流程图语言表
     * @param entity
     * @param operUserId
     * @param createUserTime
     * @param lastUpdateTime
     * @throws Exception
     */
    public void mdf(ProgramDraLang entity, String operUserId,Date createUserTime,Date lastUpdateTime)throws Exception{
        QueryWrapper<ProgramDraLang> programDraLangQueryWrapper = new QueryWrapper<>();
        programDraLangQueryWrapper.eq(ProgramDraLang.PROGRAMDRAID, entity.getProgramDraId())
                .eq(ProgramDraLang.LANGID, entity.getLangId());
        List<ProgramDraLang> programDraLangList = programDraLangMapper.selectList(programDraLangQueryWrapper);
        String id = null;
        for(ProgramDraLang programDraLang : programDraLangList){
            operUserId = programDraLang.getCreateUserId();
            createUserTime = programDraLang.getCreateUserTime();
            id = programDraLang.getId();
        }
        programDraLangMapper.delete(programDraLangQueryWrapper);
        entity.setId(id);
        this.add(entity,operUserId,createUserTime,lastUpdateTime);
    }

    /**
     * 修改节点数据
     * @param nodeBo
     * @param operUserId
     * @param createUserTime
     * @param lastUpdateTime
     * @throws Exception
     */
    public void save(NodeBo nodeBo,String operUserId,Date createUserTime,Date lastUpdateTime)throws Exception{
        Node node = NodeBo.bo2entity(nodeBo);
        String nodeId = this.add(node,operUserId,createUserTime,lastUpdateTime);

            List<NodeLangBo> nodeLangList = nodeBo.getNodeLangBoList();
            if(ListUtil.isNotEmpty(nodeLangList)){

                for(NodeLangBo nodeLangBo : nodeLangList){
                    NodeLang nodeLang = NodeLangBo.bo2entity(nodeLangBo);
                    nodeLang.setNodeId(nodeId);
                    this.mdf(nodeLang,operUserId,createUserTime,lastUpdateTime);
                }
            }

    }

    /**
     * 修改节点数据语言表tNodeLang
     * @param entity
     * @param operUserId
     * @param createUserTime
     * @param lastUpdateTime
     * @throws Exception
     */
    public void mdf(NodeLang entity,String operUserId,Date createUserTime,Date lastUpdateTime)throws Exception{
        QueryWrapper<NodeLang> nodeLangQueryWrapper = new QueryWrapper<>();
        nodeLangQueryWrapper.eq(NodeLang.NODEID, entity.getNodeId())
                .eq(NodeLang.LANGID, entity.getLangId());
        List<NodeLang> nodeLangList = nodeLangMapper.selectList(nodeLangQueryWrapper);
        String id = null;
        for(NodeLang nodeLang : nodeLangList){
            operUserId = nodeLang.getCreateUserId();
            createUserTime = nodeLang.getCreateUserTime();
            id = nodeLang.getId();
        }
//        nodeLangMapper.delete(nodeLangQueryWrapper);
        entity.setId(id);
        this.add(entity,operUserId,createUserTime,lastUpdateTime);

    }

    /**
     * 新增流程图主表
     * @param entity
     * @param operUserId
     * @param operTime
     * @return
     */
    public String add(ProgramDra entity,String operUserId,Date operTime) throws Exception {
        if(entity == null){
            return null;
        }
        if(StringUtil.isEmpty(entity.getClassType())){
            throw new RuntimeException("画布类型为空");
        }
        QueryWrapper<ProgramDra> programDraQueryWrappe = new QueryWrapper<>();
//        programDraQueryWrappe.
        int i = programDraMapper.selectCount(programDraQueryWrappe);
        entity.setSeq(i+1);

        if(StringUtil.isEmpty(entity.getLinkFromPortIdProperty())){
            throw new RuntimeException("linkFromPortIdProperty为空");
        }
        if(StringUtil.isEmpty(entity.getLinkToPortIdProperty())){
            throw new RuntimeException("linkToPortIdProperty为空");
        }

        entity.setCreateUserId(operUserId);
        entity.setCreateUserTime(operTime);
        entity.setLastUpdateTime(operTime);


        programDraMapper.insert(entity);
        return entity.getId();
    }

    /**
     * 新增流程图语言表
     * @param entity
     * @param operUserId
     * @param createUserTime
     * @param lastUpdateTime
     * @return
     * @throws Exception
     */
    public String add(ProgramDraLang entity,String operUserId,Date createUserTime,Date lastUpdateTime) throws Exception {

        if(StringUtil.isEmpty(entity.getProgramDraName())){
            throw new RuntimeException("流程图名称为空");
        }
        if(StringUtil.isEmpty(entity.getLangId())){
            throw new InternalException("语言id为空");
        }
        entity.setCreateUserId(operUserId);
        entity.setCreateUserTime(createUserTime);
        entity.setLastUpdateTime(lastUpdateTime);
        programDraLangMapper.insert(entity);
        return null;
    }

    /**
     * 新增节点数据表
     * @param entity
     * @param operUserId
     * @param createUserTime
     * @param lastUpdateTime
     * @return
     * @throws Exception
     */
    public String add(Node entity, String operUserId,Date createUserTime,Date lastUpdateTime) throws Exception{
        if(StringUtil.isEmpty(entity.getHeight())){
            entity.setHeight("");
        }
        if(StringUtil.isEmpty(entity.getWidth())){
            entity.setWidth("");
        }

        if(StringUtil.isEmpty(entity.getProgramDraId())){
            throw new RuntimeException("流程图id为空");
        }
        if(StringUtil.isEmpty(entity.getNodeIdentity())){
            throw new RuntimeException("节点标识为空");
        }
        if(StringUtil.isEmpty(entity.getCategory())){
            throw new RuntimeException("节点类型为空");
        }
        if(StringUtil.isEmpty(entity.getLoc())){
            throw new RuntimeException("节点位置为空");
        }
        if(StringUtil.isEmpty(entity.getLink())){
            entity.setLink("");
        }
        entity.setCreateUserId(operUserId);
        entity.setCreateUserTime(createUserTime);
        entity.setLastUpdateTime(lastUpdateTime);
        nodeMapper.insert(entity);
        return entity.getId();
    }

    /**
     * 新增节点数据语言表
     * @param entity
     * @param operUserId
     * @param createUserTime
     * @param lastUpdateTime
     */
    public void add(NodeLang entity,String operUserId,Date createUserTime,Date lastUpdateTime)throws Exception{

        if(StringUtil.isEmpty(entity.getLangId())){
            throw new RuntimeException("语言id为空");
        }
        if(StringUtil.isEmpty(entity.getContent())){
            throw new RuntimeException("节点内容为空");
        }
        entity.setCreateUserId(operUserId);
        entity.setCreateUserTime(createUserTime);
        entity.setLastUpdateTime(lastUpdateTime);
        nodeLangMapper.insert(entity);
    }

    /**
     * 节点数据bo
     * @param nodeBo
     * @param operUserId
     * @param operTime
     * @throws Exception
     */
    public void add(NodeBo nodeBo, String operUserId, Date operTime) throws Exception {
        Node node = NodeBo.bo2entity(nodeBo);
        String nodeId = (String) this.add(node, operUserId, operTime,operTime);
        List<NodeLangBo> nodeLangBoList = nodeBo.getNodeLangBoList();
        for(NodeLangBo nodeLangBo : nodeLangBoList){
            nodeLangBo.setNodeId(nodeId);
            NodeLang nodeLang = NodeLangBo.bo2entity(nodeLangBo);
            this.add(nodeLang, operUserId, operTime,operTime);
        }
    }

    /**
     * 线条数据表bo
     * @param linkBo
     * @param operUserId
     * @param operTime
     * @throws Exception
     */
    public void add(LinkBo linkBo, String operUserId, Date operTime) throws Exception{
        Link link = LinkBo.bo2entity(linkBo);
        String linkId = (String) this.add(link, operUserId, operTime,operTime);
        List<LinkLangBo> linkLangBoList = linkBo.getLinkLangBoList();
        if(ListUtil.isNotEmpty(linkLangBoList)){
            for (LinkLangBo linkLangBo : linkLangBoList){
                linkLangBo.setLinkId(linkId);
                LinkLang linkLang = LinkLangBo.bo2entity(linkLangBo);
                this.add(linkLang, operUserId, operTime,operTime);
            }
        }
    }

    /**
     * 新增线条数据语言表
     * @param entity
     * @param operUserId
     * @param createUserTime
     * @param lastUpdateTime
     */
    public void add(LinkLang entity, String operUserId,Date createUserTime,Date lastUpdateTime)throws Exception{
        if(StringUtil.isEmpty(entity.getContent())){
            entity.setContent("");
        }
        if(StringUtil.isEmpty(entity.getLangId())){
            throw new RuntimeException("语言id为空");
        }
        if(StringUtil.isEmpty(entity.getLinkId())){
            throw new RuntimeException("线条id为空");
        }
        entity.setCreateUserId(operUserId);
        entity.setCreateUserTime(createUserTime);
        entity.setLastUpdateTime(lastUpdateTime);
        linkLangMapper.insert(entity);

    }

    /**
     * 新增线条数据表
     * @param entity
     * @param operUserId
     * @param createUserTime
     * @param lastUpdateTime
     * @return
     */
    public String add(Link entity, String operUserId,Date createUserTime,Date lastUpdateTime)throws Exception{
        if(StringUtil.isEmpty(entity.getProgramDraId())){
            throw new RuntimeException("流程图id为空");
        }
        if(StringUtil.isEmpty(entity.getDeparture())){
            throw new RuntimeException("线条标识，从哪里出发");
        }
        if(StringUtil.isEmpty(entity.getDirection())){
            throw new RuntimeException("线条标识，从节点那个方向");
        }
        if(StringUtil.isEmpty(entity.getFromPort())){
            throw new RuntimeException("线条标识，到那个位置");
        }
        if(StringUtil.isEmpty(entity.getToPort())){
            throw new RuntimeException("线条标识，至节点那个方向");
        }
        if(StringUtil.isEmpty(entity.getPoints())){
            throw new RuntimeException("线条坐标");
        }
        entity.setCreateUserId(operUserId);
        entity.setCreateUserTime(createUserTime);
        entity.setLastUpdateTime(lastUpdateTime);
        linkMapper.insert(entity);
        return entity.getId();
    }
}
