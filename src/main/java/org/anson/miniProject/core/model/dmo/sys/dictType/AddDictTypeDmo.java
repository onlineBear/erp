package org.anson.miniProject.core.model.dmo.sys.dictType;

import lombok.Data;
import org.anson.miniProject.core.model.po.sys.DictType;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

@Data
public class AddDictTypeDmo {
    private String no;
    private String name;
    private String description;

    // private List<DictDmo> dictDmoList;

    private static final BeanCopier toDictTypeCopier = BeanCopier.create(AddDictTypeDmo.class, DictType.class, false);

    public static DictType toDictType(AddDictTypeDmo dmo) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(dmo, DictType.class, toDictTypeCopier);
    }
}
