package org.anson.miniProject.core.model.dmo.example;

import lombok.Data;
import org.anson.miniProject.core.model.po.Example;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

import java.util.List;

@Data
public class ExampleAddDMO {
    private String no;

    private static final BeanCopier doToPoCopier = BeanCopier.create(ExampleAddDMO.class, Example.class, false);

    public static Example toExamplePo(ExampleAddDMO dmo) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(dmo, Example.class, doToPoCopier);
    }

    public static List<Example> toExamplePoList(List<ExampleAddDMO> doList) throws IllegalAccessException, InstantiationException {
        return BeanHelper.beansToBeans(doList, Example.class, doToPoCopier);
    }
}
