package org.anson.miniProject.core.model.bo.sys;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.anson.miniProject.core.model.bo.BaseBO;
import org.anson.miniProject.core.model.po.sys.Dict;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

@Data
public class DictBO extends BaseBO {
    private DictTypeBO dictTypeBO;

    private String no;
    private String name;
    private String dictKey;
    private String description;

    private static final BeanCopier toDictCopier = BeanCopier.create(DictBO.class, Dict.class, false);

    public static Dict toDict(DictBO param) throws InstantiationException, IllegalAccessException {
        Dict po = BeanHelper.beanToBean(param, Dict.class, toDictCopier);

        if (param.getDictTypeBO() != null){
            DictTypeBO dictTypeBO = param.getDictTypeBO();
            if (StrUtil.isNotEmpty(dictTypeBO.getId())){
                po.setDictTypeId(dictTypeBO.getId());
            }
        }

        return po;
    }
}
