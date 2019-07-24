package org.anson.miniProject.domain.sys.dictType.impl;

import org.anson.miniProject.tool.helper.BeanHelper;
import org.springframework.cglib.beans.BeanCopier;

class Translator {
    private static final BeanCopier toDictPOCopier = BeanCopier.create(DictTypeEntity.class, Dict.class, false);
    private static final BeanCopier toDictTypePOCopier = BeanCopier.create(DictTypeEntity.class, DictType.class, false);

    public static Dict toDictPO(DictTypeEntity entity) throws InstantiationException, IllegalAccessException {
        if (entity == null){
            return null;
        }

        return BeanHelper.beanToBean(entity, Dict.class, toDictPOCopier);
    }

    public static DictType toDictTypePO(DictTypeEntity entity) throws InstantiationException, IllegalAccessException {
        if (entity == null){
            return null;
        }

        return BeanHelper.beanToBean(entity, DictType.class, toDictTypePOCopier);
    }
}
