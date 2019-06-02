package org.zmqy.erp.model.mis.bo.sys.programDra;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.cglib.beans.BeanCopier;
import org.zmqy.erp.model.mis.programDra.ProgramDra;
import org.zmqy.erp.model.mis.programDra.ProgramDraLang;

import java.util.Date;

@Data
public class ProgramDraLangBo {
    private String id;

    private String langId;

    private String programDraId;

    private String programDraName;

    private String createUserId;

    private Date createUserTime;

    private Date lastUpdateTime;

    private static final BeanCopier bo2entityCopier = BeanCopier.create(ProgramDraLangBo.class, ProgramDraLang.class, false);

    public static ProgramDraLang bo2entity(ProgramDraLangBo bo){
        if (bo == null){
            return null;
        }

        ProgramDraLang entity = new ProgramDraLang();

        bo2entityCopier.copy(bo, entity, null);

        return entity;
    }

}