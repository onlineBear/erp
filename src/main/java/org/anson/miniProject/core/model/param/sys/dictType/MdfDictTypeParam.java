package org.anson.miniProject.core.model.param.sys.dictType;

import lombok.Data;
import org.anson.miniProject.core.model.bo.sys.DictTypeBO;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

@Data
public class MdfDictTypeParam {
    private String id;
    private String no;
    private String name;
    private String description;

    private static final BeanCopier toBOCopier = BeanCopier.create(MdfDictTypeParam.class, DictTypeBO.class, false);

    public static DictTypeBO toBO(MdfDictTypeParam param) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(param, DictTypeBO.class, toBOCopier);
    }
}
