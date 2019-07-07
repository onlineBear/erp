package org.anson.miniProject.core.model.bo.example;

import lombok.Data;
import org.anson.miniProject.core.model.dmo.example.ExampleAddDo;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

import java.util.List;

@Data
public class ExampleAddBo {
    private String no;

    private static final BeanCopier toBeginLoginDoCopier = BeanCopier.create(ExampleAddBo.class, ExampleAddDo.class, false);

    public static ExampleAddDo toExampleAddDo(ExampleAddBo bo) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(bo, ExampleAddDo.class, toBeginLoginDoCopier);
    }

    public List<ExampleAddDo> toExampleAddDo(List<ExampleAddBo> boList) throws IllegalAccessException, InstantiationException {
        return BeanHelper.beansToBeans(boList, ExampleAddDo.class, toBeginLoginDoCopier);
    }
}
