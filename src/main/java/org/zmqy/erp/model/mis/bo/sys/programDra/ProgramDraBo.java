package org.zmqy.erp.model.mis.bo.sys.programDra;

import lombok.Data;
import org.springframework.cglib.beans.BeanCopier;
import org.zmqy.erp.model.mis.programDra.ProgramDra;

import java.util.Date;
import java.util.List;

@Data
public class ProgramDraBo {
    private String id;

    private String classType;

    private Integer seq;

    private String linkFromPortIdProperty;

    private String linkToPortIdProperty;

    private String createUserId;

    private Date createUserTime;

    private Date lastUpdateTime;

    List<ProgramDraLangBo> ProgramDraLangBoList;
    List<LinkBo> LinkBoList;
    List<NodeBo> NodeBoList;

    private static final BeanCopier bo2entityCopier = BeanCopier.create(ProgramDraBo.class, ProgramDra.class, false);

    public static ProgramDra bo2entity(ProgramDraBo bo){
        if (bo == null){
            return null;
        }

        ProgramDra entity = new ProgramDra();

        bo2entityCopier.copy(bo, entity, null);

        return entity;
    }

}