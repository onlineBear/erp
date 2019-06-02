package org.zmqy.erp.model.mis.bo.sys.columnInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.cglib.beans.BeanCopier;
import org.zmqy.erp.model.mis.entity.sys.columnInfo.HeadColumn;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Auther: lly
 * @Date: 2018-12-17 10:04
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HeadColumnBo implements Serializable {
    private String id;

    private String columnNo;

    private String menuId;

    private String pageTypeNo;

    private Integer seq;

    private Boolean areHidden;

    private Boolean areSysRequired;

    private Boolean areUserRequired;

    private String createUserId;

    private Date createTime;

    private Date lastUpdateTime;

    private List<HeadColumnLangBo> headColumnLangBoList;

    private static final BeanCopier bo2entityCopier = BeanCopier.create(HeadColumnBo.class, HeadColumn.class, false);

    public static HeadColumn bo2entity(HeadColumnBo bo) {
        if (bo == null) {
            return null;
        }

        HeadColumn entity = new HeadColumn();

        bo2entityCopier.copy(bo, entity, null);

        return entity;
    }
}
