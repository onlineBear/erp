package org.anson.miniProject.core.model.bo.sys;

import lombok.Data;
import org.anson.miniProject.core.model.param.sys.dictType.AddDictTypeParam;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

@Data
public class AddDictTypeBO {
    private String no;
    private String name;
    private String description;

    private static final BeanCopier toAddDictTypeDmoCopier = BeanCopier.create(AddDictTypeBO.class, AddDictTypeParam.class, false);

    public static AddDictTypeParam toAddDictTypeDmo(AddDictTypeBO bo) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(bo, AddDictTypeParam.class, toAddDictTypeDmoCopier);
    }
}
