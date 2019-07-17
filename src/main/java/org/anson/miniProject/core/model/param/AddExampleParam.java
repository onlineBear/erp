package org.anson.miniProject.core.model.param;

import lombok.Data;
import org.anson.miniProject.core.model.po.Example;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

@Data
public class AddExampleParam {
    private String no;

    private static final BeanCopier toParamCopier = BeanCopier.create(AddExampleParam.class, Example.class, false);

    public static Example toParam(AddExampleParam param) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(param, Example.class, toParamCopier);
    }
}
