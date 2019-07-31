package org.anson.miniProject.domain.sys.dictType.impl;

import cn.hutool.core.collection.CollUtil;
import lombok.Data;
import lombok.ToString;
import org.anson.miniProject.domain.base.BaseEntity;
import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

import java.util.List;

@Data
@ToString(callSuper = true)
class DictTypeEntity extends BaseEntity {
    private String no;
    private String name;
    private String description;

    private List<DictEntity> dictList;

    private static final BeanCopier toPOCopier = BeanCopier.create(DictTypeEntity.class, DictType.class, false);

    public static DictType toDictType(DictTypeEntity entity) throws InstantiationException, IllegalAccessException {
        if (entity == null){
            return null;
        }

        DictType po = BeanHelper.beanToBean(entity, DictType.class, toPOCopier);

        return po;
    }

    public static List<Dict> toDictList(DictTypeEntity entity) throws IllegalAccessException, InstantiationException {
        if (entity == null || CollUtil.isEmpty(entity.getDictList())){
            return null;
        }

        return DictEntity.toDict(entity.getDictList());
    }
}
