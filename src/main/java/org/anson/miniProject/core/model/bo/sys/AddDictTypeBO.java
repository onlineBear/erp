package org.anson.miniProject.core.model.bo.sys;

import lombok.Data;
import org.anson.miniProject.core.model.dmo.sys.dictType.AddDictTypeDmo;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

@Data
public class AddDictTypeBO {
    private String no;
    private String name;
    private String description;

    private static final BeanCopier toAddDictTypeDmoCopier = BeanCopier.create(AddDictTypeBO.class, AddDictTypeDmo.class, false);

    public static AddDictTypeDmo toAddDictTypeDmo(AddDictTypeBO bo) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(bo, AddDictTypeDmo.class, toAddDictTypeDmoCopier);
    }
}
