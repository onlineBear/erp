package org.anson.miniProject.core.model.param.sys.base.dict;

import lombok.Data;
import org.anson.miniProject.core.model.bo.sys.base.DictBO;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

@Data
public class MdfDictParam {
    private String no;
    private String name;
    private String key;
    private String description;

    private static final BeanCopier toBOCopier = BeanCopier.create(MdfDictParam.class, DictBO.class, false);

    public DictBO toBO() throws InstantiationException, IllegalAccessException {
        DictBO bo = BeanHelper.beanToBean(this, DictBO.class, toBOCopier);

        return bo;
    }
}
