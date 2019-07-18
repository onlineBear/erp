package org.anson.miniProject.core.model.param.sys.base.dict;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.anson.miniProject.core.model.bo.sys.base.DictBO;
import org.anson.miniProject.core.model.bo.sys.base.DictTypeBO;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

@Data
public class AddDictParam {
    private String dictTypeId;

    private String no;
    private String name;
    private String key;
    private String description;

    private static final BeanCopier toBOCopier = BeanCopier.create(AddDictParam.class, DictBO.class, false);

    public DictBO toBO() throws InstantiationException, IllegalAccessException {
        DictBO bo = BeanHelper.beanToBean(this, DictBO.class, toBOCopier);

        if (StrUtil.isNotEmpty(this.dictTypeId)){
            DictTypeBO dictTypeBO = new DictTypeBO();
            dictTypeBO.setId(this.dictTypeId);
            bo.setDictTypeBO(dictTypeBO);
        }

        return bo;
    }
}
