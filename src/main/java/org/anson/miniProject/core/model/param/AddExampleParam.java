package org.anson.miniProject.core.model.param;

import lombok.Data;
import org.anson.miniProject.core.model.bo.ExampleBO;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

@Data
public class AddExampleParam {
    private String no;

    private static final BeanCopier toBOCopier = BeanCopier.create(AddExampleParam.class, ExampleBO.class, false);

    public ExampleBO toBO() throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(this, ExampleBO.class, toBOCopier);
    }
}
