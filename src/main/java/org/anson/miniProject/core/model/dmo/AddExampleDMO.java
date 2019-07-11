package org.anson.miniProject.core.model.dmo;

import lombok.Data;
import org.anson.miniProject.core.model.po.ExamplePo;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

@Data
public class AddExampleDMO {
    public String no;

    private static final BeanCopier toExampleCopier = BeanCopier.create(AddExampleDMO.class, ExamplePo.class, false);

    public static ExamplePo toExample(AddExampleDMO dmo) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(dmo, ExamplePo.class, toExampleCopier);
    }
}
