package org.anson.miniProject.core.model.dmo.example;

import lombok.Data;
import org.anson.miniProject.core.model.po.ExamplePo;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

import java.util.List;

@Data
public class ExampleAddDmo {
    private String no;

    private static final BeanCopier doToPoCopier = BeanCopier.create(ExampleAddDmo.class, ExamplePo.class, false);

    public static ExamplePo toExamplePo(ExampleAddDmo dmo) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(dmo, ExamplePo.class, doToPoCopier);
    }

    public static List<ExamplePo> toExamplePoList(List<ExampleAddDmo> doList) throws IllegalAccessException, InstantiationException {
        return BeanHelper.beansToBeans(doList, ExamplePo.class, doToPoCopier);
    }
}
