package org.anson.miniProject.core.model.bo;

import lombok.Data;
import org.anson.miniProject.core.model.dmo.example.ExampleAddDMO;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

@Data
public class AddExampleBO {
    private String no;

    private static final BeanCopier toExampleDmoCopier = BeanCopier.create(AddExampleBO.class, ExampleAddDMO.class, false);

    public static ExampleAddDMO toExampleAddDo(AddExampleBO bo) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(bo, ExampleAddDMO.class, toExampleDmoCopier);
    }
}
