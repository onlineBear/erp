package org.anson.miniProject.core.model.bo.sys.base;

import lombok.Data;
import org.anson.miniProject.core.model.bo.BaseBO;
import org.anson.miniProject.core.model.po.sys.base.DictType;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

import java.util.List;

@Data
public class DictTypeBO extends BaseBO {
    private String no;
    private String name;
    private String description;

    private List<DictBO> dictBOList;

    private static final BeanCopier toDictTypeCopier = BeanCopier.create(DictTypeBO.class, DictType.class, false);

    public DictType toDictType() throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(this, DictType.class, toDictTypeCopier);
    }
}
