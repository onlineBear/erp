package org.zmqy.erp.model.mis.bo.sys.programDra;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.cglib.beans.BeanCopier;
import org.zmqy.erp.model.mis.programDra.Link;
import org.zmqy.erp.model.mis.programDra.ProgramDra;

import java.util.Date;
import java.util.List;

@Data
public class LinkBo {
    private String id;

    private String programDraId;

    private String departure;

    private String direction;

    private String fromPort;

    private String toPort;

    private String points;

    private String createUserId;

    private Date createUserTime;

    private Date lastUpdateTime;
    List<LinkLangBo> LinkLangBoList;

    private static final BeanCopier bo2entityCopier = BeanCopier.create(LinkBo.class, Link.class, false);

    public static Link bo2entity(LinkBo bo){
        if (bo == null){
            return null;
        }

        Link entity = new Link();

        bo2entityCopier.copy(bo, entity, null);

        return entity;
    }

}