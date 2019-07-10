package org.anson.miniProject.core.model.bo.sys;

import lombok.Data;
import org.anson.miniProject.core.model.dmo.sys.dictType.MdfDictTypeDmo;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

@Data
public class MdfDictTypeBO {
    private String id;
    private String no;
    private String name;
    private String description;

    private static final BeanCopier toMdfDictTypeDmoCopier = BeanCopier.create(MdfDictTypeBO.class, MdfDictTypeDmo.class, false);

    public static MdfDictTypeDmo toMdfDictTypeDmo(MdfDictTypeBO bo) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(bo, MdfDictTypeDmo.class, toMdfDictTypeDmoCopier);
    }
}
