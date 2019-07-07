package org.anson.miniProject.core.model.bo.example;

import lombok.Data;
import org.anson.miniProject.core.model.dmo.example.ExampleAddDmo;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

@Data
public class ExampleAddBo {
    private String no;

    private static final BeanCopier toExampleDmoCopier = BeanCopier.create(ExampleAddBo.class, ExampleAddDmo.class, false);

    public static ExampleAddDmo toExampleAddDo(ExampleAddBo bo) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(bo, ExampleAddDmo.class, toExampleDmoCopier);
    }
}
