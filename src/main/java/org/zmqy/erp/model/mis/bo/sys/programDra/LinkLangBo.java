package org.zmqy.erp.model.mis.bo.sys.programDra;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.cglib.beans.BeanCopier;
import org.zmqy.erp.model.mis.programDra.Link;
import org.zmqy.erp.model.mis.programDra.LinkLang;

import java.util.Date;

@Data
public class LinkLangBo {
    private String id;

    private String linkId;

    private String content;

    private String langId;

    private String createUserId;

    private Date createUserTime;

    private Date lastUpdateTime;

    private static final BeanCopier bo2entityCopier = BeanCopier.create(LinkLangBo.class, LinkLang.class, false);

    public static LinkLang bo2entity(LinkLangBo bo){
        if (bo == null){
            return null;
        }

        LinkLang entity = new LinkLang();

        bo2entityCopier.copy(bo, entity, null);

        return entity;
    }

}