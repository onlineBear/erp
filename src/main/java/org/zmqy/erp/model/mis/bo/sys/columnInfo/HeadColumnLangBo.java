package org.zmqy.erp.model.mis.bo.sys.columnInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.cglib.beans.BeanCopier;
import org.zmqy.erp.model.mis.entity.sys.columnInfo.HeadColumnLang;

import java.util.Date;

/**
 * @Description:
 * @Auther: lly
 * @Date: 2018-12-17 10:07
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HeadColumnLangBo {
    private String id;

    private String headColumnId;

    private String langId;

    private String columnName;

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;

    private static final BeanCopier bo2entityCopier = BeanCopier.create(HeadColumnLangBo.class, HeadColumnLang.class, false);

    public static HeadColumnLang bo2entity(HeadColumnLangBo bo) {
        if (bo == null) {
            return null;
        }

        HeadColumnLang entity = new HeadColumnLang();

        bo2entityCopier.copy(bo, entity, null);

        return entity;
    }
}
