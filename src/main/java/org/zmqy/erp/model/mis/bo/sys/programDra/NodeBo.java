package org.zmqy.erp.model.mis.bo.sys.programDra;

import lombok.Data;
import org.springframework.cglib.beans.BeanCopier;
import org.zmqy.erp.model.mis.programDra.Node;

import java.util.Date;
import java.util.List;

@Data
public class NodeBo {
    private String id;

    private String programDraId;

    private String nodeIdentity;

    private String width;

    private String height;


    private String category;

    private String loc;

    private String link;

    private String createUserId;

    private Date createUserTime;

    private Date lastUpdateTime;

    List<NodeLangBo> nodeLangBoList;

    private static final BeanCopier bo2entityCopier = BeanCopier.create(NodeBo.class, Node.class, false);

    public static Node bo2entity(NodeBo bo){
        if (bo == null){
            return null;
        }

        Node entity = new Node();

        bo2entityCopier.copy(bo, entity, null);

        return entity;
    }

}