package org.anson.miniProject.core.model.dmo;

import lombok.Data;
import org.anson.miniProject.core.model.po.Example;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

@Data
public class AddExampleDMO {
    private String no;

    private static final BeanCopier toExampleCopier = BeanCopier.create(AddExampleDMO.class, Example.class, false);

    public static Example toExample(AddExampleDMO dmo) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(dmo, Example.class, toExampleCopier);
    }
}
