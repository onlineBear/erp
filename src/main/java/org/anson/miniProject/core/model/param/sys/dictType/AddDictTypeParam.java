package org.anson.miniProject.core.model.param.sys.dictType;

import lombok.Data;
import org.anson.miniProject.core.model.bo.sys.base.DictTypeBO;
import org.anson.miniProject.core.model.param.sys.base.dict.AddDictParam;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

import java.util.List;

@Data
public class AddDictTypeParam {
    private String no;
    private String name;
    private String description;

    private List<AddDictParam> addDictParamList;

    private static final BeanCopier toBOCopier = BeanCopier.create(AddDictTypeParam.class, DictTypeBO.class, false);

    public static DictTypeBO toBO(AddDictTypeParam param) throws InstantiationException, IllegalAccessException {
        return BeanHelper.beanToBean(param, DictTypeBO.class, toBOCopier);
    }
}