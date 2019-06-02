package org.zmqy.erp.model.mis.bo.sys.columnInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.cglib.beans.BeanCopier;
import org.zmqy.erp.model.mis.entity.sys.columnInfo.DtlColumn;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Auther: lly
 * @Date: 2018-12-17 15:26
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DtlColumnBo {
    private String id;

    private String menuId;

    private String columnNo;

    private String arrayNo;

    private String dataTypeNo;

    private String pageTypeNo;

    private Integer seq;

    private Boolean areHidden;

    private Boolean areSysRequired;

    private Boolean areUserRequired;

    private Integer width;

    private String alias;

    private String formula;

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;

    private List<DtlColumnLangBo> dtlColumnLangBoList;

    private static final BeanCopier bo2entityCopier = BeanCopier.create(DtlColumnBo.class, DtlColumn.class, false);

    public static DtlColumn bo2entity(DtlColumnBo bo) {
        if (bo == null) {
            return null;
        }

        DtlColumn entity = new DtlColumn();

        bo2entityCopier.copy(bo, entity, null);

        return entity;
    }
}
