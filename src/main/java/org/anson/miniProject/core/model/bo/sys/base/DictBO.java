package org.anson.miniProject.core.model.bo.sys.base;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.anson.miniProject.core.model.bo.BaseBO;
import org.anson.miniProject.core.model.po.sys.base.Dict;
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

    public Dict toDict() throws InstantiationException, IllegalAccessException {
        Dict po = BeanHelper.beanToBean(this, Dict.class, toDictCopier);

        if (this.dictTypeBO != null){
            if (StrUtil.isNotEmpty(this.dictTypeBO.getId())){
                po.setDictTypeId(this.dictTypeBO.getId());
            }
        }

        return po;
    }
}
