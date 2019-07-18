package org.anson.miniProject.core.model.bo;


import lombok.Data;
import org.anson.miniProject.core.model.po.Example;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

@Data
public class ExampleBO extends BaseBO {
    private String no;
    private String name;

    private static final BeanCopier toExampleCopier = BeanCopier.create(ExampleBO.class, Example.class, false);

    public Example toExample() throws InstantiationException, IllegalAccessException {
        Example po = BeanHelper.beanToBean(this, Example.class, toExampleCopier);

        return po;
    }

}
