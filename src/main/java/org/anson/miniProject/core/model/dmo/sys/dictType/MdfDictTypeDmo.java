package org.anson.miniProject.core.model.dmo.sys.dictType;

import lombok.Data;
import org.anson.miniProject.core.model.po.sys.DictType;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

@Data
public class MdfDictTypeDmo {
    private String id;
    private String no;
    private String name;
    private String description;

    private static final BeanCopier toDictTypeCopier = BeanCopier.create(MdfDictTypeDmo.class, DictType.class, false);

    public static DictType toDictType(MdfDictTypeDmo dmo) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(dmo, DictType.class, toDictTypeCopier);
    }
}
