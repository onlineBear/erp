package org.anson.miniProject.core.model.bo.sys;

import lombok.Data;
import org.anson.miniProject.core.model.bo.BaseBO;
import org.anson.miniProject.core.model.po.sys.DictType;
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

    public static DictType toDictType(DictTypeBO param) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(param, DictType.class, toDictTypeCopier);
    }
}
