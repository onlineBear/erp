package org.zmqy.erp.model.mis.bo.sys.columnInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.cglib.beans.BeanCopier;
import org.zmqy.erp.model.mis.entity.sys.columnInfo.DtlColumnLang;

import java.util.Date;

/**
 * @Description:
 * @Auther: lly
 * @Date: 2018-12-17 15:28
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DtlColumnLangBo {
    private String id;

    private String dtlColumnId;

    private String langId;

    private String columnName;

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;

    private static final BeanCopier bo2entityCopier = BeanCopier.create(DtlColumnLangBo.class, DtlColumnLang.class, false);

    public static DtlColumnLang bo2entity(DtlColumnLangBo bo) {
        if (bo == null) {
            return null;
        }

        DtlColumnLang entity = new DtlColumnLang();

        bo2entityCopier.copy(bo, entity, null);

        return entity;
    }
}
