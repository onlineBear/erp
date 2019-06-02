package org.zmqy.erp.model.mis.bo.sys.programDra;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.cglib.beans.BeanCopier;
import org.zmqy.erp.model.mis.programDra.LinkLang;
import org.zmqy.erp.model.mis.programDra.NodeLang;

import java.util.Date;

@Data
public class NodeLangBo {
    private String id;

    private String nodeId;

    private String content;

    private String langId;

    private String createUserId;

    private Date createUserTime;

    private Date lastUpdateTime;

    private static final BeanCopier bo2entityCopier = BeanCopier.create(NodeLangBo.class, NodeLang.class, false);

    public static NodeLang bo2entity(NodeLangBo bo){
        if (bo == null){
            return null;
        }

        NodeLang entity = new NodeLang();

        bo2entityCopier.copy(bo, entity, null);

        return entity;
    }

}