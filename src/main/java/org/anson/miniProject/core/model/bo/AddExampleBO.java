package org.anson.miniProject.core.model.bo;

import lombok.Data;
import org.anson.miniProject.core.model.dmo.example.ExampleAddDmo;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

@Data
public class AddExampleBO {
    private String no;

    private static final BeanCopier toExampleDmoCopier = BeanCopier.create(AddExampleBO.class, ExampleAddDmo.class, false);

    public static ExampleAddDmo toExampleAddDo(AddExampleBO bo) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(bo, ExampleAddDmo.class, toExampleDmoCopier);
    }
}
